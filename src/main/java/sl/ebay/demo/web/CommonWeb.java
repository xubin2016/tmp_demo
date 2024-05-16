package sl.ebay.demo.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sl.ebay.demo.common.Constants;
import sl.ebay.demo.common.RoleEnum;
import sl.ebay.demo.common.UserIdThreadLocalUtil;
import sl.ebay.demo.persist.FilePersistTool;
import sl.ebay.demo.pojo.AccessResource;
import sl.ebay.demo.pojo.AuthenticationInfo;
import sl.ebay.demo.response.DemoResponse;

import java.io.IOException;
import java.util.List;

@Api("demoController")
@RestController
@RequestMapping("demo")
public class CommonWeb {

    @Autowired
    private FilePersistTool filePersistTool;

    /**
     * Add resource permissions to users
     * @param resourceList 因为是一个demo所以这里直接使用了键值对数组来接收参数，但实际上这样会让参数体占用大小膨胀，userId冗余
     * @return
     * @throws IOException
     */
    @ApiOperation("Add resource permissions to users")
    @PostMapping("/admin/addUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.REQUEST_HEADER_AUTHENTICATION_INFO,
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    public DemoResponse<String> addUserResourcesMapping(@RequestBody List<AccessResource> resourceList) throws IOException {
        AuthenticationInfo authenticationInfo = UserIdThreadLocalUtil.USER_ID_THREAD_LOCAL.get();
        // 校验身份为admin
        if (authenticationInfo != null
                && RoleEnum.ADMIN.getRoleName().equals(authenticationInfo.getRole())) {
            filePersistTool.write(resourceList);
            return DemoResponse.success("User permissions updated successfully!");
        }
        return DemoResponse.error("Verification failed");
    }

    /**
     * Determine if you have permission
     * @param resource
     * @return
     */
    @ApiOperation("Determine if you have permission")
    @GetMapping("/user/{resource}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.REQUEST_HEADER_AUTHENTICATION_INFO,
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    public DemoResponse<List<AccessResource>> ownerJudge(@PathVariable("resource") String resource) {
        AuthenticationInfo authenticationInfo = UserIdThreadLocalUtil.USER_ID_THREAD_LOCAL.get();
        Long userId = authenticationInfo == null ? null : authenticationInfo.getUserId();
        if (userId == 1) {
            int t = 1 / 0;
        }
        String judgeMsg = filePersistTool.read()
                .contains(new AccessResource(userId, resource)) ? "Verification passed" : "Verification failed";
        return DemoResponse.error(judgeMsg);
    }

}
