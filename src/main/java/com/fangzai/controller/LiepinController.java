package com.fangzai.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.Liepin;
import com.fangzai.service.ILiepinService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
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
    public Result getCityPositionCount(@RequestParam(name = "selectedCity") String selectedCity) {
        List<Map<String, Object>> cityPositionCount = liepinService.getCityPositionCount(selectedCity);
        return Result.success(cityPositionCount);
    }
    //岗位总数
    @GetMapping("/getPositionTotal")
    public Result getPositionTotal(){
        long count = liepinService.count();
        return Result.success(count);
    }
    //万元以上岗位
    @GetMapping("/getHighSalaryTotal")
    public Result getHighSalaryTotal(){
        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Liepin::getSalaryAverage,10);
        long count = liepinService.count(queryWrapper);
        return Result.success(count);
    }
    //万元以下岗位
    @GetMapping("/getLowSalaryTotal")
    public Result getLowSalaryTotal(){
        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Liepin::getSalaryAverage,6,8);
        long count = liepinService.count(queryWrapper);
        return Result.success(count);
    }

    @GetMapping("getHotPosition")
    public Result getHotPosition(@RequestParam(name = "selectedCity") String selectedCity) {
        List<Map<String, Object>> hotPosition = liepinService.getHotPosition(selectedCity);
        return Result.success(hotPosition);
    }
    //职位分页查询
    @PostMapping("/getPositionData")
    public Result getPositionData(@RequestBody Map<String, Object> data) {
        String name = (String) data.get("name");
        System.out.println(name);
        Page<Liepin> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));

        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Liepin::getPosition, name)
                    .or().like(Liepin::getEducation, name)
                    .or().like(Liepin::getCity, name)
                    .or().like(Liepin::getTags, name)
                    .or().like(Liepin::getCompanyName, name)
                    .or().like(Liepin::getSalary, name)
                    .or().like(Liepin::getExperience, name)
                    .or().like(Liepin::getCompanyScale, name);
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
        String select5 = (String) data.get("select5");
        Page<Liepin> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));
        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        Integer num_min;
        Integer num_max;
        if (select3.contains("薪资面议")) {
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.like(Liepin::getSalary, select3))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4))
                    .and(wrapper -> wrapper.like(Liepin::getCity, select5));
        }
        if (select3.contains("-")) {
            num_min = Integer.valueOf(select3.split("-")[0]);
            String number_max = select3.split("-")[1];
            num_max = Integer.valueOf(number_max.substring(0, number_max.length() - 1));
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.between(Liepin::getSalaryAverage, num_min, num_max))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4))
                    .and(wrapper -> wrapper.like(Liepin::getCity, select5));
        } else if (select3.contains("K以上")) {
            num_min = Integer.valueOf(select3.substring(0, select3.length() - 3));
            num_max = 100;
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.between(Liepin::getSalaryAverage, num_min, num_max))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4))
                    .and(wrapper -> wrapper.like(Liepin::getCity, select5));
        }else{
            queryWrapper.like(Liepin::getPosition, select1)
                    .and(wrapper -> wrapper.like(Liepin::getExperience, select2))
                    .and(wrapper -> wrapper.like(Liepin::getEducation, select4))
                    .and(wrapper -> wrapper.like(Liepin::getCity, select5));
        }
        IPage result = liepinService.page(page, queryWrapper);
        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("/getEducationCount")
    public Result getEducationCount() {
        return Result.success(liepinService.getEducationCount());
    }

    @PostMapping("getPositionRecommendationData")
    public Result getPositionRecommendationData(@RequestBody Map<String, Object> data){
        Object positionsObject = data.get("positions");
        Object citiesObject = data.get("cities");
        Object salariesObject = data.get("salaries");
        String education = (String) data.get("education");
        String experience  = (String) data.get("experience");
        Object technologiesObject = data.get("technologies");

        List<Map<String, Object>> positionListData = liepinService.getPositionRecommendationData(positionsObject,citiesObject,salariesObject,education,experience,technologiesObject);
        return Result.success(positionListData);
    }

}
