package com.fangzai.controller;


import com.fangzai.service.ITechnologyService;
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
 * @since 2023-10-05
 */
@RestController
@RequestMapping("/technology")
public class TechnologyController {

    @Resource
    private ITechnologyService technologyService;
    @GetMapping("getTechnicalFrequencyStatistics")
    public Result getTechnicalFrequencyStatistics(){
        return Result.success(technologyService.list());
    }

}
