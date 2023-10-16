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
    public List<Map<String, Object>> getCityPositionCount() {
        QueryWrapper<Liepin> wrapper = new QueryWrapper<>();
        wrapper.select("LEFT(city, 2) as city_prefix", "COUNT(LEFT(city, 2)) as count")
                .groupBy("city_prefix")
                .orderByDesc("count")
                .orderByAsc("city_prefix")
                .last("LIMIT 20");
        return liepinMapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getHotPosition() {
        QueryWrapper<Liepin> wrapper = new QueryWrapper<>();
        wrapper.select("position", "COUNT(*) as count")
                .groupBy("position")
                .orderByDesc("count")
                .last("LIMIT 30");
        return liepinMapper.selectMaps(wrapper);
    }

}
