package cn.edu.xmu.wwf.opus.albumservice.model.vo;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AlbumPostVo {
    @NotBlank
    String name;
    @NotBlank
    String introduction;
}
