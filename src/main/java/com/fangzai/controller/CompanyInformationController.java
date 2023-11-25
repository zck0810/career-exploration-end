package com.fangzai.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.CompanyInformation;
import com.fangzai.service.ICompanyInformationService;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司信息表 前端控制器
 * </p>
 *
 * @author smile
 * @since 2023-10-17
 */
@RestController
@RequestMapping("/company")
public class CompanyInformationController {
    @Autowired
    private ICompanyInformationService iCompanyInformationService;

    @GetMapping("/getCompanyTotal")
    public Result getCompanyTotal(){
        long count = iCompanyInformationService.count();
        return Result.success(count);
    }
    //公司分页查询
    @PostMapping("/getCompanyData")
    public Result getCompanyData(@RequestBody Map<String, Object> data) {
        String name = (String) data.get("name");
        Page<CompanyInformation> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));

        LambdaQueryWrapper<CompanyInformation> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(CompanyInformation::getCompanyName, name)
                    .or().like(CompanyInformation::getLegalRepresentative, name)
                    .or().like(CompanyInformation::getManageState, name)
                    .or().like(CompanyInformation::getProvince, name)
                    .or().like(CompanyInformation::getCity, name)
                    .or().like(CompanyInformation::getCounty, name)
                    .or().like(CompanyInformation::getEnterpriseType, name);
        }

        IPage result = iCompanyInformationService.page(page, queryWrapper);

        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }


    @GetMapping("getCompanyScaleData")
    public Result getCompanyScaleData(@RequestParam(name = "selectedCity") String selectedCity){
        List<Map<String, Object>> companyScaleData = iCompanyInformationService.getCompanyScaleData(selectedCity);

        return Result.success(companyScaleData);
    }


    //公司高级搜索
    @PostMapping("/getHighCompanyInformation")
    public Result getHighCompanyInformation(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        Map<String, Object> form = (Map<String, Object>) data.get("form");
        String key = (String) form.get("key");
        String region = (String) form.get("region");
        String year = (String) form.get("year");
        String pay = (String) form.get("pay");
        String number = (String) form.get("number");
        //注册资金
        Integer pay_min = null;
        Integer pay_max1 = null;
        if (pay.contains("-")) {
            pay_min = Integer.valueOf(year.split("-")[0]);
            String year_max = year.split("-")[1];
            pay_max1 = Integer.valueOf(year_max.substring(0, year_max.length() - 1));
        } else if (pay.contains("万以上")) {
            pay_min = Integer.valueOf(pay.substring(0, year.length() - 3));
            pay_max1 = 10000000;
        }
        //成立年限
        Integer year_min = null;
        Integer year_max1 = null;
        if (year.contains("-")) {
            year_min = Integer.valueOf(year.split("-")[0]);
            String year_max = year.split("-")[1];
            year_max1 = Integer.valueOf(year_max.substring(0, year_max.length() - 1));
        } else if (year.contains("年以上")) {
            year_min = Integer.valueOf(year.substring(0, year.length() - 3));
            year_max1 = 100;
        }

        //参保人数50-99人
        Integer number_min = null;
        Integer number_max1 = null;
        if (number.contains("-")) {
            number_min = Integer.valueOf(number.split("-")[0]);
            String number_max = number.split("-")[1];
            number_max1 = Integer.valueOf(number_max.substring(0, number_max.length() - 1));
        } else if (number.contains("人以上")) {
            number_min = Integer.valueOf(number.substring(0, number.length() - 3));
            number_max1 = 10000000;
        }
        //更多筛选
        LambdaQueryWrapper<CompanyInformation> queryWrapper = new LambdaQueryWrapper<>();
        ArrayList<String> conditions = (ArrayList<String>) form.get("condition");
        for (String condition : conditions) {
            if (condition.equals("联系电话")) {
                queryWrapper.ne(CompanyInformation::getTelephone, "-");
            }
            if (condition.equals("联系邮箱")) {
                queryWrapper.ne(CompanyInformation::getMailbox, "-");
            }
            if (condition.equals("网址信息")) {
                queryWrapper.ne(CompanyInformation::getWebsite, "-");
            }
        }
        Page<CompanyInformation> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));

        Integer finalNumber_min = number_min;
        Integer finalNumber_max = number_max1;
        Integer finalYear_min = year_min;
        Integer finalYear_max = year_max1;
        Integer finalPay_min = pay_min;
        Integer finalPay_max = pay_max1;

        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like(CompanyInformation::getCompanyName, key)
                    .and(wrapper -> wrapper.like(CompanyInformation::getProvince, region))
                    .and(wrapper -> wrapper.between(CompanyInformation::getTempInsuredNumber, finalNumber_min, finalNumber_max))
                    .and(wrapper -> wrapper.between(CompanyInformation::getTimeDifference, finalYear_min, finalYear_max))
                    .and(wrapper -> wrapper.between(CompanyInformation::getTempCapital, finalPay_min, finalPay_max));

        } else {
            queryWrapper.like(CompanyInformation::getProvince, region)
                    .and(wrapper -> wrapper.between(CompanyInformation::getTempInsuredNumber, finalNumber_min, finalNumber_max))
                    .and(wrapper -> wrapper.between(CompanyInformation::getTimeDifference, finalYear_min, finalYear_max))
                    .and(wrapper -> wrapper.between(CompanyInformation::getTempCapital, finalPay_min, finalPay_max));
        }
        IPage result1 = iCompanyInformationService.page(page, queryWrapper);
        System.out.println("total===" + result1.getTotal());
        return Result.success(result1.getRecords(), result1.getTotal());

    }
}
