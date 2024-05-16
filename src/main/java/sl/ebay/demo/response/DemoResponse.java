package sl.ebay.demo.response;

import sl.ebay.demo.common.Constants;

public class DemoResponse<M> {

    private M m;
    int responseCode;
    private String msg;

    public static <M>DemoResponse success(M m) {
        return new DemoResponse()
                .setM(m)
                .setResponseCode(Constants.RESPONSE_CODE_SUCCESS)
                .setMsg(Constants.SUCCESS_MSG);
    }

    public static DemoResponse error(String msg) {
        return new DemoResponse()
                .setResponseCode(Constants.RESPONSE_CODE_ERROR)
                .setMsg(msg);
    }

    public static DemoResponse error() {
        return new DemoResponse()
                .setResponseCode(Constants.RESPONSE_CODE_ERROR)
                .setMsg(Constants.ERROR_MSG);
    }

    public M getM() {
        return m;
    }

    public DemoResponse setM(M m) {
        this.m = m;
        return this;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public DemoResponse setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public DemoResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
