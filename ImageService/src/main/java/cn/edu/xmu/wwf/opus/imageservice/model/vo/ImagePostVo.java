package cn.edu.xmu.wwf.opus.imageservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ImagePostVo {
    MultipartFile multipartFile;
}
