package cn.edu.xmu.wwf.opus.artworkservice.microservice;

import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image.GetImageUrlRetVo;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image.PostImageRetVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value="ImageService")
public interface ImgService {
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReturnObject<PostImageRetVo> uploadImage(@RequestPart("file") MultipartFile file);
    @GetMapping("/image/{id}")
    ReturnObject<GetImageUrlRetVo> getImageInfo(@PathVariable int id);
}
