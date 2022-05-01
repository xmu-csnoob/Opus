package cn.edu.xmu.wwf.opus.categoryservice.microservice;

import cn.edu.xmu.wwf.opus.categoryservice.microservice.model.GetArtWorkRetVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="ArtworkService")
public interface ArtworkService {
    @GetMapping(value="/artwork/{id}",produces = "application/json;charset=UTF-8")
    ReturnObject<GetArtWorkRetVo> getArtwork(@PathVariable int id);
}
