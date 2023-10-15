package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangzai.entity.PositionInformation;
import com.fangzai.mapper.PositionInformationMapper;
import com.fangzai.service.IPositionInformationService;
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
 * @since 2023-10-04
 */
@Service
public class PositionInformationServiceImpl extends ServiceImpl<PositionInformationMapper, PositionInformation> implements IPositionInformationService {

    @Resource
    private PositionInformationMapper positionInformationMapper;

    @Override
    public List<Map<String, Object>> getCityPositionCount() {
        QueryWrapper<PositionInformation> wrapper = new QueryWrapper<>();
        wrapper.select("city", "COUNT(city) as count")
                .groupBy("city")
                .orderByDesc("count")
                .orderByAsc("city")
                .last("LIMIT 20");
        return positionInformationMapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getHotPosition() {
        QueryWrapper<PositionInformation> wrapper = new QueryWrapper<>();
        wrapper.select("job", "COUNT(*) as count")
                .groupBy("job")
                .orderByDesc("count")
                .last("LIMIT 30");
        return positionInformationMapper.selectMaps(wrapper);
    }
}
