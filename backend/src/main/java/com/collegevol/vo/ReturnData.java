package com.collegevol.vo;

import lombok.Data;

@Data
public class ReturnData {
    int statusCode;
    String msg;
    Object data;

    public ReturnData(int statusCode, String msg, Object data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public ReturnData(){

    }

}
