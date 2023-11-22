package com.fangzai.service;

import com.fangzai.entity.QuestionAndAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵长开
 * @since 2023-11-20
 */
public interface IQuestionAndAnswerService extends IService<QuestionAndAnswer> {

    List<QuestionAndAnswer> getAnswer(String question);

    List<QuestionAndAnswer> getGuidedQuestion(Integer up_one_level_question_id);
}
