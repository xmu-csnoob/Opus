package cn.edu.xmu.wwf.opus.common.utils.ret;

public enum ReturnNo {
    /**
     * 状态码：
     * 2XX：访问服务成功
     * 3XX: 传参有误
     * 4XX: 访问服务成功，但对资源的访问不成功
     * 5XX：服务器内部出现错误
     * 6XX: 访问成功，但是当前服务器状态不允许进行这样的操作
     */
    OK(200),
    DATA_FORMAT_INVALID(300),
    FILE_NOT_VALID(307),
    RESOURCE_NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    STATE_NOT_ALLOWED(600);
    private final int value;
    private ReturnNo(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
