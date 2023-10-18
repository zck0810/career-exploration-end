package com.fangzai.service.impl;

import com.fangzai.entity.CompanyInformation;
import com.fangzai.mapper.CompanyInformationMapper;
import com.fangzai.service.ICompanyInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
