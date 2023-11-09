package com.fangzai.service;

import com.fangzai.entity.Liepin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-16
 */
public interface ILiepinService extends IService<Liepin> {

    List<Map<String, Object>> getCityPositionCount();

    List<Map<String, Object>> getHotPosition(String selectedCity);

    List<Map<String, Object>> getEducationCount();
}
