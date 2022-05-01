package cn.edu.xmu.wwf.opus.albumservice.model.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class AlbumPutVo {
    @Min(0)
    int id;
    @NotBlank
    String name;
    @NotBlank
    String introduction;
    boolean isDeleted;
}
