package cn.edu.xmu.wwf.opus.categoryservice.microservice;

import cn.edu.xmu.wwf.opus.categoryservice.microservice.model.GetImageUrlRetVo;
import cn.edu.xmu.wwf.opus.categoryservice.microservice.model.PostImageRetVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value="ImageService")
public interface ImageService {
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ReturnObject<PostImageRetVo> uploadImage(@RequestPart("file") MultipartFile file, @RequestParam String region);

    @GetMapping("/image/{id}")
    ReturnObject<GetImageUrlRetVo> getImageInfo(@PathVariable int id);
}
