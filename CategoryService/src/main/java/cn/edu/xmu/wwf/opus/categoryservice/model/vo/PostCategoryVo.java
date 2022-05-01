package cn.edu.xmu.wwf.opus.categoryservice.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostCategoryVo {
    @NotBlank
    String name;
}
