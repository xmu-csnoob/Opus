package cn.edu.xmu.wwf.opus.artworkservice.microservice;

import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image.PostImageRetVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value="ImageService")
public interface ImgService {
    @PostMapping(value = "/image/{category}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReturnObject<PostImageRetVo> PostImageToCos(@RequestPart("file") MultipartFile file, @PathVariable String category);
}
