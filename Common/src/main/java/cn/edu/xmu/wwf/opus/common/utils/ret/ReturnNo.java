package cn.edu.xmu.wwf.opus.common.utils.ret;

public enum ReturnNo {
    OK(200),
    INTERNAL_SERVER_ERROR(500);
    private final int value;
    private ReturnNo(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
