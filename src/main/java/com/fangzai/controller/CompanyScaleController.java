package com.fangzai.controller;


import com.fangzai.service.ICompanyScaleService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/company-scale")
public class CompanyScaleController {

    @Resource
    private ICompanyScaleService companyScaleService;


    @GetMapping("getCompanyScaleData")
    public Result getCompanyScaleData(@RequestParam(name = "selectedCity") String selectedCity){
        return Result.success(companyScaleService.list());
    }

}
