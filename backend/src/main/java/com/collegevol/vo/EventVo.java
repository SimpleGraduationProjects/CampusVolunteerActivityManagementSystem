package com.collegevol.vo;

import com.collegevol.entity.Event;
import com.collegevol.entity.EventPicture;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class EventVo extends Event implements Comparator<EventVo>,Comparable<EventVo> {
    private int num;

    @Override
    public int compareTo(EventVo o) {
        if(this.getStartTime().getTime()>o.getStartTime().getTime()){
            return -1;
        }else if(this.getStartTime().getTime()==o.getStartTime().getTime()){
            return 0;
        }else return 1;
    }

    @Override
    public int compare(EventVo o1, EventVo o2) {
        if(o1.getStartTime().getTime()>o2.getStartTime().getTime()){
            return 1;
        }else if(o1.getStartTime().getTime()==o2.getStartTime().getTime()){
            return 0;
        }else return -1;
    }

    /**
     * 喜欢的人数
     */
    private int likeNum;

    private int commentNum;

    private List<EventLikeUserInfoVo> likes;

    private List<EventCommentUserInfoVo> comments;

    private List<EventPicture> resultPictures;

    private String firstImgUrl;

    private boolean islike;

    private boolean isAdmin;
}
