package com.fangzai.controller;


import com.fangzai.service.IFreshGraduateSalaryService;
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
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/fresh-graduate-salary")
public class FreshGraduateSalaryController {

    @Resource
    private IFreshGraduateSalaryService freshGraduateSalaryService;

    @GetMapping("getFreshGraduateSalary")
    public Result getFreshGraduateSalary(){
        return Result.success(freshGraduateSalaryService.list());
    }

}
