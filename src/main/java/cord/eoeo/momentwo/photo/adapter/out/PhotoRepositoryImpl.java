package cord.eoeo.momentwo.photo.adapter.out;

import cord.eoeo.momentwo.photo.application.port.out.PhotoJpaRepository;
import cord.eoeo.momentwo.photo.application.port.out.PhotoRepository;
import cord.eoeo.momentwo.photo.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {
    private final PhotoJpaRepository photoJpaRepository;

    @Override
    public void save(Photo photo) {
        photoJpaRepository.save(photo);
    }

    @Override
    public List<Photo> findImageByAlbumId(long albumId) {
        return photoJpaRepository.findImageByAlbumId(albumId);
    }

    @Override
    public void deleteAllByAlbumIdAndPhotoId(long albumId, List<Long> imageIds) {
        photoJpaRepository.deleteAllByAlbumIdAndPhotoId(albumId, imageIds);
    }
}
