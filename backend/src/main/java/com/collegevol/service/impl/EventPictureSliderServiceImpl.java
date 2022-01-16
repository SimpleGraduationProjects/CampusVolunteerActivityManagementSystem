package com.collegevol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.collegevol.entity.EventPictureSlider;
import com.collegevol.dao.EventPictureSliderMapper;
import com.collegevol.service.EventPictureSliderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 图片轮播图 首页展示的图片，展示优先级 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class EventPictureSliderServiceImpl extends ServiceImpl<EventPictureSliderMapper, EventPictureSlider> implements EventPictureSliderService {

    @Resource
    private EventPictureSliderMapper eventPictureSliderMapper;


    /**
     * 新增图片
     * @param eventPictureSlider
     */
    public void insert(EventPictureSlider eventPictureSlider){
        eventPictureSliderMapper.insert(eventPictureSlider);
    }

    public IPage<EventPictureSlider> qryPictureSliderForPage(Page page){
        QueryWrapper<EventPictureSlider> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("show_priority");
        queryWrapper.orderByDesc("created_time");
        return eventPictureSliderMapper.selectPage(page,queryWrapper);
    }


}
