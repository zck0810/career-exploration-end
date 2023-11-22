package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangzai.entity.QuestionAndAnswer;
import com.fangzai.mapper.QuestionAndAnswerMapper;
import com.fangzai.service.IQuestionAndAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 赵长开
 * @since 2023-11-20
 */
@Service
public class QuestionAndAnswerServiceImpl extends ServiceImpl<QuestionAndAnswerMapper, QuestionAndAnswer> implements IQuestionAndAnswerService {

    @Resource
    private QuestionAndAnswerMapper questionAndAnswerMapper;

    @Override
    public List<QuestionAndAnswer> getAnswer(String question) {
        QueryWrapper<QuestionAndAnswer> wrapper = new QueryWrapper<>();
        wrapper.eq("question",question);
        return questionAndAnswerMapper.selectList(wrapper);
    }

    @Override
    public List<QuestionAndAnswer> getGuidedQuestion(Integer up_one_level_question_id) {
        QueryWrapper<QuestionAndAnswer> wrapper = new QueryWrapper<>();
        wrapper.eq("up_one_level_question_id",up_one_level_question_id).orderByDesc("id");
        return questionAndAnswerMapper.selectList(wrapper);
    }


}
