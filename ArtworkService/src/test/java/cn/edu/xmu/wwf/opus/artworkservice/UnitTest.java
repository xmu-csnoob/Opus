package cn.edu.xmu.wwf.opus.artworkservice;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class  UnitTest {
    @Autowired
    MockMvc mvc;
    @Test
    public void onShelfArtwork01() throws Exception {
        String responseStr = mvc.perform(
                        put("/artwork/32/on").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==200;
    }
    @Test
    public void onShelfArtwork02() throws Exception {
        String responseStr = mvc.perform(
                        put("/artwork/29/on").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==600;
    }
    @Test
    public void onShelfArtwork03() throws Exception {
        String responseStr = mvc.perform(
                        put("/artwork/30/on").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==600;
    }
    @Test
    public void offShelfArtwork01() throws Exception {
        String responseStr = mvc.perform(
                        put("/artwork/32/off").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==200;
    }
    @Test
    public void offShelfArtwork02() throws Exception {
        String responseStr = mvc.perform(
                        put("/artwork/29/off").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==600;
    }
    @Test
    public void offShelfArtwork03() throws Exception {
        String responseStr = mvc.perform(
                        put("/artwork/31/off").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject=JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==600;
    }
    @Test
    public void getUserArtwork01() throws Exception {
        String responseStr = mvc.perform(
                        get("/artwork/users/0?page=1&pageSize=10").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"total\":4,\"list\":[{\"id\":29,\"name\":\"??\u009B\u009B??\u008D\u0081\",\"introduction\":\"very clever\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":4},{\"id\":30,\"name\":\"test\",\"introduction\":\"test\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":1},{\"id\":31,\"name\":\"test\",\"introduction\":\"test\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":2},{\"id\":32,\"name\":\"test\",\"introduction\":\"test\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":0}],\"pageNum\":1,\"pageSize\":10,\"size\":4,\"startRow\":1,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void getArtwork01()throws Exception{
        String responseStr = mvc.perform(
                        get("/artwork/29").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"name\":\"??????\",\"introduction\":\"very clever\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"url\":null},\"errMsg\":\"Success\"}",responseStr,false);
    }
    @Test
    public void obscureSearch()throws Exception{
        String responseStr = mvc.perform(
                        get("/artwork?word=test&page=1&pageSize=10").contentType("application/json;encoding=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"returnNo\":\"OK\",\"data\":{\"total\":3,\"list\":[{\"id\":30,\"name\":\"test\",\"introduction\":\"test\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":1},{\"id\":31,\"name\":\"test\",\"introduction\":\"test\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":2},{\"id\":32,\"name\":\"test\",\"introduction\":\"test\",\"userId\":0,\"imageId\":30,\"categoryId\":0,\"state\":0}],\"pageNum\":1,\"pageSize\":10,\"size\":3,\"startRow\":1,\"endRow\":3,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1},\"errMsg\":\"Success\"}",responseStr,false);
    }
}
