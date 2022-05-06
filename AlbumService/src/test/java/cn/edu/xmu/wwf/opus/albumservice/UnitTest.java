package cn.edu.xmu.wwf.opus.albumservice;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UnitTest {
    @Autowired
    MockMvc mvc;
    @Test
    public void postAlbum01() throws Exception {
        String requestStr = "{\n" +
                "    \"name\":\"123\",\n" +
                "    \"introduction\":\"abc\"\n" +
                "}";
        String responseStr = mvc.perform(
                        post("/album").contentType(MediaType.APPLICATION_JSON)
                                .content(requestStr))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 200;
    }
    @Test
    public void postAlbum02() throws Exception {
        String requestStr = "{\"name\":\"\",\"introduction\":\"\"}";
        String responseStr = mvc.perform(
                        post("/album").contentType(MediaType.APPLICATION_JSON)
                                .content(requestStr))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 300;
    }
    @Test
    public void getAlbum01() throws Exception {
        String responseStr = mvc.perform(
                        get("/album/3").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"name\":\"testAlterAlbum\",\"userId\":0,\"introduction\":\"alterSucUtils\",\"artworks\":[{\"name\":\"å\u009B\u009Bå\u008D\u0081\",\"introduction\":\"very clever\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"url\":null}]},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void getAlbum02() throws Exception {
        String responseStr = mvc.perform(
                        get("/album/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 404;
    }
    @Test
    @Transactional
    public void putAlbum01() throws Exception {
        String requestStr="{\"name\":\"abc\",\"introduction\":\"here\"}";
        String responseStr = mvc.perform(
                        put("/album/3").contentType("application/json;encoding=UTF-8").content(requestStr))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"id\":3,\"name\":\"abc\",\"userId\":0,\"introduction\":\"here\",\"deleted\":false},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void putAlbum02() throws Exception {
        String requestStr = "{\"name\":\"\",\"introduction\":\"\"}";
        String responseStr = mvc.perform(
                        put("/album/3").contentType("application/json;encoding=UTF-8").content(requestStr))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 300;
    }
    @Test
    public void putAlbum03() throws Exception {
        String requestStr="{\"name\":\"abc\",\"introduction\":\"here\"}";
        String responseStr = mvc.perform(
                        put("/album/-1").contentType("application/json;encoding=UTF-8").content(requestStr))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 404;
    }
    @Test
    public void delAlbum01() throws Exception {
        String responseStr = mvc.perform(
                        delete("/album/3").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"id\":3,\"name\":null,\"userId\":0,\"introduction\":null,\"deleted\":true},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void delAlbum02() throws Exception {
        String responseStr = mvc.perform(
                        delete("/album/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 404;
    }
    @Test
    public void postArtworkToAlbum01() throws Exception {
        String responseStr = mvc.perform(
                        post("/album/4/artwork/29").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 200;
    }
    @Test
    public void postArtworkToAlbum02() throws Exception {
        String responseStr = mvc.perform(
                        post("/album/4/artwork/-1").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 404;
    }
    @Test
    public void postArtworkToAlbum03() throws Exception {
        String responseStr = mvc.perform(
                        post("/album/-1/artwork/29").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 404;
    }
    @Test
    public void delArtworkToAlbum01() throws Exception {
        String responseStr = mvc.perform(
                        delete("/album/3/artwork/29").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 200;
    }
    @Test
    public void delArtworkToAlbum02() throws Exception {
        String responseStr = mvc.perform(
                        delete("/album/4/artwork/29").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject = JSON.parseObject(responseStr, ReturnObject.class);
        assert returnObject.returnNo.getValue() == 600;
    }
}
