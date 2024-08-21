package cord.eoeo.momentwo.image.application.port.out;

import cord.eoeo.momentwo.image.adapter.dto.ImageDownLoadResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface ImageManager {
    CompletableFuture<String> imageUpload(MultipartFile image, String path);
    void imageDelete(String Path, String imageUrl);
    CompletableFuture<ImageDownLoadResponseDto> imageDownload(Path path);
    CompletableFuture<Resource> profileFileSearch(String filename);
    CompletableFuture<Resource> imageFileSearch(String filename);
}