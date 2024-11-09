package cord.eoeo.momentwo.image.application.port.in;

import cord.eoeo.momentwo.image.adapter.dto.PresignedRequestDto;
import cord.eoeo.momentwo.image.adapter.dto.PresignedResponseDto;

public interface PhotoPresignedUrlUseCase {
    /**
     * 사진 프리사인드 URL 요청
     * @param presignedRequestDto
     * albumId : 앨범 id
     * extension : 확장자
     * @return : 프리사인드 URL
     */
    PresignedResponseDto photoPresignedUrl(PresignedRequestDto presignedRequestDto);
}
