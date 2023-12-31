package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangzai.entity.Liepin;
import com.fangzai.mapper.LiepinMapper;
import com.fangzai.service.ILiepinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-16
 */
@Service
public class LiepinServiceImpl extends ServiceImpl<LiepinMapper, Liepin> implements ILiepinService {

    @Resource
    private LiepinMapper liepinMapper;

    @Override
    public List<Map<String, Object>> getCityPositionCount(String selectedCity) {
        QueryWrapper<Liepin> wrapper = new QueryWrapper<>();
        if(selectedCity.equals("全国")){
            wrapper.select("LEFT(city, 2) as city_prefix", "COUNT(LEFT(city, 2)) as count")
                    .groupBy("city_prefix")
                    .orderByDesc("count")
                    .orderByAsc("city_prefix")
                    .last("LIMIT 20");
        }else {
            wrapper.select("SUBSTRING_INDEX(city, '-', -1) as city_prefix", "COUNT(*) as count")
                    .like("city",selectedCity)
                    .like("city","-")
                    .groupBy("city_prefix")
                    .orderByDesc("count")
                    .orderByDesc("city_prefix")
                    .last("LIMIT 20");
        }
        return liepinMapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getHotPosition(String selectedCity) {
        return liepinMapper.getHotPosition(selectedCity);
    }

    @Override
    public List<Map<String, Object>> getEducationCount() {
        QueryWrapper<Liepin> wrapper = new QueryWrapper<>();
        wrapper.select("education as name","count(education) as value")
                .groupBy("education").orderByDesc("value")
                .last("LIMIT 6");
        return liepinMapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getPositionRecommendationData(Object positionsObject, Object citiesObject, Object salariesObject, String education, String experience, Object technologiesObject) {

        return liepinMapper.getPositionRecommendationData(positionsObject,citiesObject,salariesObject,education,experience,technologiesObject);
    }

}
