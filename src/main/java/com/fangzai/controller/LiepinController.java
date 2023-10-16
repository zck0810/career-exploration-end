package com.fangzai.controller;


import com.fangzai.service.ILiepinService;
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
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/liepin")
public class LiepinController {

    @Resource
    ILiepinService liepinService;

    @GetMapping("getCityPositionCount")
    public Result getCityPositionCount(){
        List<Map<String, Object>> cityPositionCount = liepinService.getCityPositionCount();
        return Result.success(cityPositionCount);
    }

    @GetMapping("getHotPosition")
    public Result getHotPosition(){
        List<Map<String, Object>> hotPosition = liepinService.getHotPosition();
        return Result.success(hotPosition);
    }
}
