package cord.eoeo.momentwo.user.application.service;

import cord.eoeo.momentwo.config.security.jwt.TokenProvider;
import cord.eoeo.momentwo.config.security.jwt.adapter.out.TokenResponseDto;
import cord.eoeo.momentwo.member.advice.exception.AdminAlbumOutException;
import cord.eoeo.momentwo.member.application.port.out.GetAlbumInfo;
import cord.eoeo.momentwo.member.domain.MemberAlbumGrade;
import cord.eoeo.momentwo.user.adapter.dto.in.SignOutRequestDto;
import cord.eoeo.momentwo.user.adapter.dto.in.UserLoginRequestDto;
import cord.eoeo.momentwo.user.advice.exception.NotFoundUserException;
import cord.eoeo.momentwo.user.advice.exception.PasswordMisMatchException;
import cord.eoeo.momentwo.user.application.port.in.UserStatusUseCase;
import cord.eoeo.momentwo.user.application.port.out.AuthenticationManager;
import cord.eoeo.momentwo.config.security.jwt.port.out.JWTBlackList;
import cord.eoeo.momentwo.user.application.port.out.GetAuthentication;
import cord.eoeo.momentwo.user.application.port.out.PasswordEncoder;
import cord.eoeo.momentwo.user.application.port.out.UserRepository;
import cord.eoeo.momentwo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@RequiredArgsConstructor
public class UserStatusService implements UserStatusUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final JWTBlackList jwtBlackList;
    private final GetAuthentication getAuthentication;
    private final GetAlbumInfo getAlbumInfo;

    @Transactional(readOnly = true)
    @Override
    @Async
    public CompletableFuture<TokenResponseDto> signIn(UserLoginRequestDto userLoginRequestDto) {
        return CompletableFuture.supplyAsync(() -> {
            // 아이디 확인
            User user = userRepository.findByUsername(userLoginRequestDto.getUsername()).orElseThrow(() -> {
                throw new CompletionException(new NotFoundUserException());
            });
            // 비밀번호 확인
            if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
                throw new CompletionException(new PasswordMisMatchException());
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userLoginRequestDto.getUsername(),
                    userLoginRequestDto.getPassword()
            );
            Authentication authentication = authenticationManager.getAuthentication(authenticationToken);

            return tokenProvider.createToken(authentication);
        });
    }

    @Override
    public void blackListToken(String token) {
        jwtBlackList.blackListToken(token);
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public void signOut(SignOutRequestDto signOutRequestDto) {
        User user = userRepository.findByNickname(getAuthentication.getAuthentication().getName())
                .orElseThrow(NotFoundUserException::new);
        // 비밀번호 확인
        if(!passwordEncoder.matches(signOutRequestDto.getPassword(), user.getPassword())) {
            throw new PasswordMisMatchException();
        }

        // 회원 탈퇴 시 앨범 관리자이면서 멤버를 보유한 경우 회원탈퇴 불가
        // 관리자 권한을 넘기면 탈퇴 가능
        getAlbumInfo.getAlbumIdByAdminUser(user).forEach(albumId -> {
            if(getAlbumInfo.getAlbumMemberInfo(albumId,
                    user.getId()).getRules().equals(MemberAlbumGrade.ROLE_ALBUM_ADMIN) &&
                    !getAlbumInfo.getAlbumMemberList(albumId).isEmpty()) {
                throw new AdminAlbumOutException();
            }
        });

        userRepository.delete(user);
    }
}
