package com.codex.ecam.result;

import com.codex.ecam.constants.ResultStatus;

public class RestResult<T> {

    private T data;
    private String msg;
    private ResultStatus status = ResultStatus.SUCCESS;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }


}
