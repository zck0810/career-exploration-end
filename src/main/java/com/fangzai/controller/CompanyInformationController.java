package com.fangzai.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.CompanyInformation;
import com.fangzai.service.ICompanyInformationService;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    //公司分页查询
    @PostMapping("/getCompanyData")
    public Result getCompanyData(@RequestBody Map<String,Object> data ){
        String name = (String) data.get("name");
        System.out.println(name);
        Page<CompanyInformation> page = new Page<>();
        page.setCurrent((Integer) data.get("pageNum"));
        page.setSize((Integer) data.get("pageSize"));

        LambdaQueryWrapper<CompanyInformation> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(CompanyInformation::getCompanyName,name);
        }

        IPage result = iCompanyInformationService.page(page, queryWrapper);

        System.out.println("total===" + result.getTotal());
        return Result.success(result.getRecords(),result.getTotal());
    }

}
