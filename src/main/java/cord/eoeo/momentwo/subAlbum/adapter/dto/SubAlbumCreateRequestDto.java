package cord.eoeo.momentwo.subAlbum.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubAlbumCreateRequestDto {
    private long albumId;
    private String title;
}
