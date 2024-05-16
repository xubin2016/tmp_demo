package sl.ebay.demo.test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sl.ebay.demo.pojo.AccessResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoWebJunitTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * request header 测试数据
     * {
     * "userId":1,
     * "accountName": "ebayxx",
     * "role": "admin"
     * }
     * base64: ewoidXNlcklkIjoxLAoiYWNjb3VudE5hbWUiOiAiZWJheXh4IiwKInJvbGUiOiAiYWRtaW4iCn0=
     * @throws IOException
     */
    @Test
    public void testAddUserResourcesMapping() throws Exception {
        List<AccessResource> accessResourceList = new ArrayList<>();
        accessResourceList.add(new AccessResource(12L, "resourceA"));
        accessResourceList.add(new AccessResource(12L, "resourceB"));
        accessResourceList.add(new AccessResource(312L, "resourceC"));
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/demo/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authentication_info",
                        "ewoidXNlcklkIjoxLAoiYWNjb3VudE5hbWUiOiAiZWJheXh4IiwKInJvbGUiOiAiYWRtaW4iCn0=")
                .content(new JsonMapper().writeValueAsString(accessResourceList));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    /**
     * request header 测试数据  testOwnerJudge依赖于testAddUserResourcesMapping方法跑完的结果数据
     * {
     * "userId":312L,
     * "accountName": "ebayxx",
     * "role": "admin"
     * }
     * base64: ewoidXNlcklkIjozMTIsCiJhY2NvdW50TmFtZSI6ICJlYmF5eHgiLAoicm9sZSI6ICJhZG1pbiIKfQ==
     * @throws IOException
     */
    @Test
    public void testOwnerJudge() throws Exception {
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/demo/user/resourceC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authentication_info",
                                "ewoidXNlcklkIjozMTIsCiJhY2NvdW50TmFtZSI6ICJlYmF5eHgiLAoicm9sZSI6ICJhZG1pbiIKfQ==");

        // 执行请求并验证响应状态
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
