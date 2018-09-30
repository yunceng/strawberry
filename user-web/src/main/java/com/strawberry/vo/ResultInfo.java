package com.strawberry.vo;

public class ResultInfo {

    private CodeType code;

    private String msg;

    public CodeType getCode() {
        return code;
    }

    public void setCode(CodeType code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public enum CodeType{

        SUCCESS(0), UN_AUTHORIZED(1);

        private int code;

        private CodeType(int code){
            this.code = code;
        }

        @Override
        public String toString() {
            return code+"";
        }
    }

}
