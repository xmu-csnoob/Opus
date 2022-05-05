package cn.edu.xmu.wwf.opus.artworkservice.microservice;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="CategoryService")
public interface CategoryService {
    @PostMapping("/category/{id}/artworks/{artworkId}")
    ReturnObject addArtworkIntoCategory(@PathVariable int id, @PathVariable int artworkId);
}
