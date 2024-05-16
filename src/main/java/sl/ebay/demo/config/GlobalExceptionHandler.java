package sl.ebay.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sl.ebay.demo.response.DemoResponse;

/**
 * 全局异常兜底
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public DemoResponse handleAllExceptions(Throwable ex) {
        // 记录日志，处理异常信息
        ex.printStackTrace();
        return DemoResponse.error();
    }
}
