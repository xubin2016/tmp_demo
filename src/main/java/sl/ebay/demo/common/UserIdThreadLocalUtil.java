package sl.ebay.demo.common;

import sl.ebay.demo.pojo.AuthenticationInfo;

public class UserIdThreadLocalUtil {

    public static ThreadLocal<AuthenticationInfo> USER_ID_THREAD_LOCAL = new ThreadLocal<>();

}
