package cord.eoeo.momentwo.album.adapter.in.profile;

import cord.eoeo.momentwo.album.adapter.dto.in.AlbumProfileRequestDto;
import cord.eoeo.momentwo.album.application.port.in.profile.AlbumSubTitleDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AlbumSubTitleDeleteController {
    private final AlbumSubTitleDeleteUseCase albumSubTitleDeleteUseCase;

    // 서브 타이틀 삭제
    @DeleteMapping("/albums/subtitle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void albumSubTitleDelete(@ModelAttribute @Valid AlbumProfileRequestDto albumProfileRequestDto) {
        albumSubTitleDeleteUseCase.albumSubTitleDelete(albumProfileRequestDto);
    }
}
