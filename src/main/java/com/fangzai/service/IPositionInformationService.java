package com.fangzai.service;

import com.fangzai.entity.PositionInformation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-04
 */
public interface IPositionInformationService extends IService<PositionInformation> {

    List<Map<String, Object>> getCityPositionCount();

    List<Map<String, Object>> getHotPosition();
}
