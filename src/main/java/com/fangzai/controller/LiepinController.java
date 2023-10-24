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
    public Result getPositionData(@RequestBody Map<String, Object> data) {
        String name = (String) data.get("name");
        System.out.println(name);
        Page<Liepin> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));

        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Liepin::getPosition, name);
        }
        IPage result = liepinService.page(page, queryWrapper);
        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }
    //职位高级搜索
    @PostMapping("/getJobInformation")
    public Result getJobInformation(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        String select1 = (String) data.get("select1");
        String select2 = (String) data.get("select2");
        String select3 = (String) data.get("select3");
        String select4 = (String) data.get("select4");
        Page<Liepin> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));
        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        Integer num_min;
        Integer num_max;
        if(select3.contains("薪资面议")){
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.like(Liepin::getSalary, select3))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4));
        }
        if (select3.contains("-")){
            num_min = Integer.valueOf(select3.split("-")[0]);
            String number_max = select3.split("-")[1];
            num_max = Integer.valueOf(number_max.substring(0, number_max.length() - 1));
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.between(Liepin::getSalaryAverage,num_min,num_max))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4));
        }else if (select3.contains("K以上")){
            num_min = Integer.valueOf(select3.substring(0,select3.length() - 3));
            num_max = 100;
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.between(Liepin::getSalaryAverage,num_min,num_max))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4));
        }
        IPage result = liepinService.page(page, queryWrapper);
        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("getEducationCount")
    public Result getEducationCount(){
        return Result.success(liepinService.getEducationCount());
    }


}
