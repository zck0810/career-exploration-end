package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fangzai.entity.CompanyInformation;
import com.fangzai.mapper.CompanyInformationMapper;
import com.fangzai.service.ICompanyInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

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

//    @Autowired
//    private CompanyInformationMapper companyInformationMapper;
//
//    public List<Data> getDataByLambdaQueryWrapper(LambdaQueryWrapper<Data> queryWrapper) {
//        return CompanyInformationMapper.selectList(queryWrapper);
//
}
