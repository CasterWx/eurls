package com.antzuhl.mall.common;

public enum HResult {
    R_OK(1, "success"),
    R_ERROR(-1, "error");

    private int code;
    private String msg;

    HResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
