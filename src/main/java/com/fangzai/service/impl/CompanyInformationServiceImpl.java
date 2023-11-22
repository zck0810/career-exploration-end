package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fangzai.entity.CompanyInformation;
import com.fangzai.entity.Liepin;
import com.fangzai.mapper.CompanyInformationMapper;
import com.fangzai.service.ICompanyInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司信息表 服务实现类
 * </p>
 *
 * @author smile
 * @since 2023-10-17
 */
@Service
public class CompanyInformationServiceImpl extends ServiceImpl<CompanyInformationMapper, CompanyInformation> implements ICompanyInformationService {

    @Resource
    private CompanyInformationMapper companyInformationMapper;
    @Override
    public List<Map<String, Object>> getCompanyScaleData(String selectedCity) {
        QueryWrapper<CompanyInformation> wrapper = new QueryWrapper<>();

        if(selectedCity.equals("全国")){
            wrapper.select("SUM(CASE WHEN temp_Insured_number < 50 THEN 1 ELSE 0 END) as less_than_fifty",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 50 AND 100 THEN 1 ELSE 0 END) as fifty_to_hundred",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 100 AND 500 THEN 1 ELSE 0 END) as hundred_to_five_hundred",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 500 AND 1000 THEN 1 ELSE 0 END) as five_hundred_to_thousand",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 1000 AND 5000 THEN 1 ELSE 0 END) as thousand_to_five_thousand",
                    "SUM(CASE WHEN temp_Insured_number > 5000 THEN 1 ELSE 0 END) as greater_than_five_thousand"
            ).gt("temp_Insured_number",5);
        }else {
            wrapper.select("SUM(CASE WHEN temp_Insured_number < 50 THEN 1 ELSE 0 END) as less_than_fifty",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 50 AND 100 THEN 1 ELSE 0 END) as fifty_to_hundred",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 100 AND 500 THEN 1 ELSE 0 END) as hundred_to_five_hundred",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 500 AND 1000 THEN 1 ELSE 0 END) as five_hundred_to_thousand",
                    "SUM(CASE WHEN temp_Insured_number BETWEEN 1000 AND 5000 THEN 1 ELSE 0 END) as thousand_to_five_thousand",
                    "SUM(CASE WHEN temp_Insured_number > 5000 THEN 1 ELSE 0 END) as greater_than_five_thousand"
            ).like("province",selectedCity).or().like("city",selectedCity).gt("temp_Insured_number",5);
        }


        return companyInformationMapper.selectMaps(wrapper);


    }

}
