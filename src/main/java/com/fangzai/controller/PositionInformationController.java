package com.fangzai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangzai.entity.PositionInformation;
import com.fangzai.service.IPositionInformationService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-04
 */
@RestController
@RequestMapping("/positionInformation")
public class PositionInformationController {

    @Resource
    private IPositionInformationService positionInformationService;

    @GetMapping("getAllData")
    public Result getAllData(){
        return Result.success(positionInformationService.list());
    }

    @GetMapping("getCityPositionCount")
    public Result getCityPositionCount(){
        List<Map<String, Object>> cityPositionCount = positionInformationService.getCityPositionCount();
        return Result.success(cityPositionCount);
    }

    @GetMapping("getHotPosition")
    public Result getHotPosition(){
        List<Map<String, Object>> hotPosition = positionInformationService.getHotPosition();
        return Result.success(hotPosition);
    }
}
