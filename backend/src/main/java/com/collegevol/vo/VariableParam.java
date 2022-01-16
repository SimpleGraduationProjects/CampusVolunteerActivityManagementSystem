package com.collegevol.vo;

public interface VariableParam {

    String CONTENT_TYPE = "application/json; charset=utf-8";

    String REDIS_PREFIX = "userId:";

    String SESSION_USERID = "userId";

    String LOGIN_HASH = "userLogin";

    Integer MIN_SCORE = 60;

    Integer BASE_SCORE = 100;
}
