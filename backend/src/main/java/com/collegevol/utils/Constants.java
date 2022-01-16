package com.collegevol.utils;

public interface Constants {

    //通用分隔符:,
    String SPLIT_COMMON = ",";

    //分隔符:-
    String SPLIT_STRIKE = "-";

    //分隔符::
    String SPLIT_COLON= ":";

    //分隔符: _
    String SPLIT_UNDERLINE = "_";

    /** long类型的NULL表示. */
    long NULL_LONG = -9999999999999998L; // 前台JAVASCRIPT没有这么大的值

    /** String类型的NULL标识 */
    public static final String NULL_STRING = "-nullnull-";

    //缓存模式：本地缓存
    String CACHE_MODEl_LOCAL="LOCAL";

    //缓存模式：REDIS缓存
    String CACHE_MODEl_REDIS="REDIS";

    //缓存模式：混合缓存，一部分在redis，一部分在local
    String CACHE_MODEl_MIX="MIX";

    /** sql语句中?替换标识 */
    String SQL_REPLACE_KEY = "-QUESTION-MARK-";

    //utf8编码
    String ENCODING_UTF8 = "UTF-8";


    //http请求参数类型：url-form
    String HTTP_CONTENT_TYPE_FORM= "application/x-www-form-urlencoded";

    //http请求参数类型：json
    String HTTP_CONTENT_TYPE_JSON= "application/json";

    //http请求类型：GET
    String HTTP_METHOD_GET = "get";

    //http请求类型：POST
    String HTTP_METHOD_POST = "post";

    //成功
    String SUCCESS = "success";
    //失败
    String FAILURE = "failure";


}
