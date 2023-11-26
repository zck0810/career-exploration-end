package com.fangzai.mapper;

import com.fangzai.entity.Liepin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 赵长开
 * @since 2023-11-26
 */
public interface LiepinMapper extends BaseMapper<Liepin> {

    List<Map<String, Object>> getHotPosition(String selectedCity);

    List<Map<String, Object>> getPositionRecommendationData(Object positionsObject, Object citiesObject, Object salariesObject, String education, String experience, Object technologiesObject);
}
