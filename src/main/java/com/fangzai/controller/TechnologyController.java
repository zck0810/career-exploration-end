package com.fangzai.controller;


import com.fangzai.entity.Technology;
import com.fangzai.service.ITechnologyService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Technology> list = technologyService.list();
        List<Technology> top40List = list.stream().limit(40).collect(Collectors.toList());
        return Result.success(top40List);
    }

}
