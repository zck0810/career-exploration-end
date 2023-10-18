package com.fangzai.controller;


import com.fangzai.service.IProvincePositionCountService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/province-position-count")
public class ProvincePositionCountController {

    @Resource
    private IProvincePositionCountService iProvincePositionCountService;

    @GetMapping("getProvincePositionCount")
    public Result getProvincePositionCount(){
        return Result.success(iProvincePositionCountService.list());
    }


}
