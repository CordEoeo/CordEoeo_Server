package cord.eoeo.momentwo.album.application.service.profile;

import cord.eoeo.momentwo.album.application.aop.annotation.CheckAlbumAdmin;
import cord.eoeo.momentwo.album.application.port.in.profile.ProfileDeleteUseCase;
import cord.eoeo.momentwo.album.application.port.out.GetAlbumMemberPort;
import cord.eoeo.momentwo.album.application.port.out.profile.ProfileDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileDeleteService implements ProfileDeleteUseCase {
    private final ProfileDeletePort profileDeletePort;
    private final GetAlbumMemberPort getAlbumMemberPort;

    @Override
    @Transactional
    @CheckAlbumAdmin
    public void profileDelete(Long albumId) {
        profileDeletePort.profileDelete(getAlbumMemberPort.getMember(albumId));
    }
}
