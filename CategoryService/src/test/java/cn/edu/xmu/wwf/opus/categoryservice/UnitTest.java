package cn.edu.xmu.wwf.opus.categoryservice;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UnitTest {
    @Autowired
    MockMvc mvc;
    @Test
    public void postCategory01() throws Exception {
        String requestStr = "{\"name\":\"test\"}";
        String responseStr = mvc.perform(
                        post("/category/")
                                .contentType("application/json")
                                .content(requestStr))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"id\":0,\"name\":\"test\"},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void postCategory02() throws Exception {
        String requestStr = "{\"name\":\"\"}";
        String responseStr = mvc.perform(
                        post("/category/")
                                .contentType("application/json")
                                .content(requestStr))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==300;
    }
    @Test
    public void delCategory01() throws Exception {
        String responseStr = mvc.perform(
                        delete("/category/1")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":true,\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void delCategory02() throws Exception {
        String responseStr = mvc.perform(
                        delete("/category/-1")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==404;
    }
    @Test
    public void postCategoryArtwork01() throws Exception {
        String responseStr = mvc.perform(
                        post("/category/1/artworks/29")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert. assertEquals("{\"returnNo\":\"OK\",\"data\":{\"artworkId\":29,\"categoryId\":1},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void postCategoryArtwork02() throws Exception {
        String responseStr = mvc.perform(
                        post("/category/1/artworks/-1")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==404;
    }
    @Test
    public void postCategoryArtwork03() throws Exception {
        String responseStr = mvc.perform(
                        post("/category/-1/artworks/29")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==404;
    }
    @Test
    public void delCategoryArtwork01() throws Exception {
        String responseStr = mvc.perform(
                        delete("/category/1/artworks/29")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert. assertEquals("{\"returnNo\":\"OK\",\"data\":true,\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void delCategoryArtwork02() throws Exception {
        String responseStr = mvc.perform(
                        delete("/category/1/artworks/-1")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==404;
    }
    @Test
    public void delCategoryArtwork03() throws Exception {
        String responseStr = mvc.perform(
                        delete("/category/-1/artworks/29")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==404;
    }
    @Test
    public void delCategoryArtwork04() throws Exception {
        String responseStr = mvc.perform(
                        delete("/category/1/artworks/30")
                                .contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==600;
    }
}
