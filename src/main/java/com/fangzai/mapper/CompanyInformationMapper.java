package com.fangzai.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fangzai.entity.CompanyInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * <p>
 * 公司信息表 Mapper 接口
 * </p>
 *
 * @author smile
 * @since 2023-10-17
 */
@Mapper
public interface CompanyInformationMapper extends BaseMapper<CompanyInformation> {

}
