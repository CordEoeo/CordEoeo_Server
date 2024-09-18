package cord.eoeo.momentwo.elasticsearch.application.service;

import cord.eoeo.momentwo.elasticsearch.adpater.dto.in.UserSearchRequestDto;
import cord.eoeo.momentwo.elasticsearch.adpater.dto.out.UserSearchListResponseDto;
import cord.eoeo.momentwo.elasticsearch.adpater.out.UserElasticSearchManager;
import cord.eoeo.momentwo.elasticsearch.application.port.in.UserSearchUseCase;
import cord.eoeo.momentwo.user.advice.exception.NotFoundUserException;
import cord.eoeo.momentwo.user.application.port.out.GetAuthentication;
import cord.eoeo.momentwo.user.application.port.out.UserRepository;
import cord.eoeo.momentwo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService implements UserSearchUseCase {
    private final UserElasticSearchManager userElasticSearchManager;
    private final UserRepository userRepository;
    private final GetAuthentication getAuthentication;

    @Override
    public UserSearchListResponseDto getUserElasticSearch(UserSearchRequestDto userSearchRequestDto) {
        User user = userRepository.findByNickname(getAuthentication.getAuthentication().getName())
                        .orElseThrow(NotFoundUserException::new);

        return new UserSearchListResponseDto()
                .toDo(userElasticSearchManager.getMembers(userSearchRequestDto.getNickname(), user));
    }
}
