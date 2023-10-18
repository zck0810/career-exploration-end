package com.fangzai.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.CompanyInformation;
import com.fangzai.entity.Liepin;
import com.fangzai.service.ILiepinService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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
    public Result getCityPositionCount() {
        List<Map<String, Object>> cityPositionCount = liepinService.getCityPositionCount();
        return Result.success(cityPositionCount);
    }

    @GetMapping("getHotPosition")
    public Result getHotPosition() {
        List<Map<String, Object>> hotPosition = liepinService.getHotPosition();
        return Result.success(hotPosition);
    }

    @PostMapping("/getPositionData")
    public Result getPositionData(@RequestBody Map<String,Object> data) {
        String name = (String) data.get("name");
        System.out.println(name);
        Page<Liepin> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));

        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Liepin::getPosition,name);
        }
        IPage result = liepinService.page(page,queryWrapper);
        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(),result.getTotal());
    }


    }
