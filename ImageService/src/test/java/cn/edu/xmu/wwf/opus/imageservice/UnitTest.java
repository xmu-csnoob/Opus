package cn.edu.xmu.wwf.opus.imageservice;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class UnitTest {
    @Autowired
    MockMvc mvc;
    @Test
    public void postImage01() throws Exception {
        ClassPathResource classPathResource=new ClassPathResource("/files/1.png");
        File file=classPathResource.getFile();
        InputStream inputStream=new FileInputStream(file);
        MockMultipartFile file1 = new MockMultipartFile("file", file.getName(),
                "multipart/form-data", inputStream);
        String responseStr=mvc.perform(
                MockMvcRequestBuilders
                        .multipart("/image/test")
                        .file(file1))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject= JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==200;
    }
    @Test
    public void postImage02() throws Exception {
        ClassPathResource classPathResource=new ClassPathResource("/files/1.txt");
        File file=classPathResource.getFile();
        InputStream inputStream=new FileInputStream(file);
        MockMultipartFile file1 = new MockMultipartFile("file", file.getName(),
                "text/plain", inputStream);
        String responseStr=mvc.perform(
                        MockMvcRequestBuilders
                                .multipart("/image/test")
                                .file(file1))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject= JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==307;
    }
    @Test
    public void getUrlVo1() throws Exception {
        String responseStr=mvc.perform(
                get("/image/30"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals(responseStr,"{\"returnNo\":\"OK\",\"data\":{\"url\":\"http://rau38waqa.bkt.clouddn.com/IT/e506f4c0fb6342e59e4049db788fcf5b.png\"},\"errMsg\":\"Success\"}",false);
    }
    @Test
    public void getUrlVo2() throws Exception {
        String responseStr=mvc.perform(
                        get("/image/0"))
                .andReturn().getResponse().getContentAsString();
        ReturnObject returnObject= JSON.parseObject(responseStr,ReturnObject.class);
        assert returnObject.returnNo.getValue()==404;
    }
}
