package com.fangzai.service;

import com.fangzai.entity.CompanyInformation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司信息表 服务类
 * </p>
 *
 * @author smile
 * @since 2023-10-17
 */
public interface ICompanyInformationService extends IService<CompanyInformation> {

    List<Map<String, Object>> getCompanyScaleData(String selectedCity);
}
