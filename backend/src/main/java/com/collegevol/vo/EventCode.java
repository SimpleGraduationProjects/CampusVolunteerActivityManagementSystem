package com.collegevol.vo;

public interface EventCode {

    //审核中/进行中/已结束
    String UNDER_REVIEW = "审核中";

    String IN_PROGRESS = "进行中";

    String ENDED = "已结束";

    String TIMEOUT = "已过期";

    String S00X = "已删除";
}
