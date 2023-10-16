package com.fangzai.controller;


import com.fangzai.entity.Visit;
import com.fangzai.service.IVisitService;
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
@RequestMapping("/visit")
public class VisitController {

    @Resource
    private IVisitService visitService;


    @GetMapping("/getVisitCount")
    public Result getVisitCount(){
        Visit byId = visitService.getById(1);
        byId.setVisit(byId.getVisit()+1);
        visitService.updateById(byId);
        return Result.success(visitService.getById("1"));
    }

}
