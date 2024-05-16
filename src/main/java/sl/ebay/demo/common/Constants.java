package sl.ebay.demo.common;

public class Constants {

    // ------------------ 返回结果常量汇总
    public static final int RESPONSE_CODE_SUCCESS = 200;

    public static final int RESPONSE_CODE_ERROR = 500;

    public static final String SUCCESS_MSG = "ok";

    public static final String ERROR_MSG = "system error";

    // ------------------ 持久化相关常量汇总
    // 持久化文件存储的目录常量键名
    public static final String PERSIST_DIRECTORY_PATH_SYSTEM_PROPERTY_KEY = "user.dir";
    // 持久化文件名
    public static final String PERSIST_DIRECTORY_FILENAME = "user_resources_mapping";
    // 持久化单行资源分割识别符号
    public static final String SPLIT_SIGN = " ";
    // 换行符常量键名
    public static final String PERSIST_FILE_LINE_SEPARATOR_SYSTEM_PROPERTY_KEY = "line.separator";


    // ----------------- 请求信息携带常量字段
    public static final String REQUEST_HEADER_AUTHENTICATION_INFO = "authentication_info";

}
