package cn.edu.xmu.wwf.opus.common.utils.ret;

public class ReturnObject<T> {
    public ReturnNo returnNo;
    public T data;
    public String errMsg;
    public ReturnObject(T data){
        this.data=data;
        this.errMsg="Success";
        this.returnNo=ReturnNo.OK;
    }
    public ReturnObject(ReturnNo returnNo,String errMsg){
        this.data=null;
        this.returnNo=returnNo;
        this.errMsg=errMsg;
    }
    public ReturnObject(){}
}
