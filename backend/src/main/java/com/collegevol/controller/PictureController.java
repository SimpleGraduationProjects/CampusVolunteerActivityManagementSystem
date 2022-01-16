package com.collegevol.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.collegevol.entity.EventPictureSlider;
import com.collegevol.resolver.MultiRequestBody;
import com.collegevol.service.impl.EventPictureSliderServiceImpl;
import com.collegevol.vo.ReturnData;
import com.collegevol.vo.StatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/picture")
public class PictureController {
    @Resource
    private EventPictureSliderServiceImpl eventPictureSliderService;
    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${image.url}")
    private String imageUrl;

    /**
     * 查询首页轮播展示的图片，按时间和展示优先级显示
     *
     * @param size
     * @return
     */
    @RequestMapping("/getPictureTopSize/{size}")
    public IPage<EventPictureSlider> getPictureTopSize(@PathVariable("size") int size) {
        Page page = new Page();
        page.setSize(size);
        page.setCurrent(1);
        return eventPictureSliderService.qryPictureSliderForPage(page);
    }

    @RequestMapping("/add")
    public ReturnData add(@MultiRequestBody EventPictureSlider eventPictureSlider, @MultiRequestBody MultipartFile file) throws Exception {
        File upload = new File(uploadDir + file.getOriginalFilename());
        file.transferTo(upload);
        if(eventPictureSlider.getShowPriority()==null){
            eventPictureSlider.setShowPriority(0);
        }
        eventPictureSlider.setImgUrl(imageUrl + file.getOriginalFilename());
        eventPictureSlider.setCreatedTime(new Date());
        eventPictureSliderService.insert(eventPictureSlider);
        return new ReturnData(StatusCode.SUCCESS, "添加图片成功", "");
    }

    /**
     * 根据epsid修改行记录
     *
     * @param eventPictureSlider
     * @param file
     * @return
     */
    @RequestMapping("/update")
    public ReturnData update(@MultiRequestBody EventPictureSlider eventPictureSlider, @MultiRequestBody MultipartFile file) throws IOException {
        if (eventPictureSlider.getEpsId() == null) {
            return new ReturnData(StatusCode.FAIL, "图片记录的eps_id不能为空", "");
        }
        UpdateWrapper<EventPictureSlider> updateWrapper = new UpdateWrapper<>();
        if (file != null && !file.isEmpty()) {
            File upload = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(upload);
            updateWrapper.set("img_url", eventPictureSlider.getImgUrl());
            eventPictureSlider.setImgUrl(imageUrl + file.getOriginalFilename());
        }
        updateWrapper.set("show_priority", eventPictureSlider.getShowPriority());
        updateWrapper.eq("eps_id", eventPictureSlider.getEpsId());
        eventPictureSliderService.update(updateWrapper);
        return new ReturnData(StatusCode.SUCCESS, "修改成功", "");
    }


    @RequestMapping("/qryPictureForPageAdmin")
    public IPage qryPictureForPageAdmin(@MultiRequestBody Page page) {
        return eventPictureSliderService.page(page);
    }


}
