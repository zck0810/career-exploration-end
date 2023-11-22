package com.fangzai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangzai.entity.CompanyInformation;
import com.fangzai.entity.QuestionAndAnswer;
import com.fangzai.service.IQuestionAndAnswerService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 赵长开
 * @since 2023-11-20
 */
@RestController
@RequestMapping("/question-and-answer")
public class QuestionAndAnswerController {

    @Resource
    IQuestionAndAnswerService questionAndAnswerService;

    @GetMapping("getAnswer")
    public Result getAnswer(@RequestParam(name = "question") String question) {
        List<QuestionAndAnswer> list = questionAndAnswerService.getAnswer(question);
        return Result.success(list);
    }

    @GetMapping("getGuidedQuestion")
    public Result getGuidedQuestion(@RequestParam(name = "up_one_level_question_id") Integer up_one_level_question_id) {
        List<QuestionAndAnswer> list = questionAndAnswerService.getGuidedQuestion(up_one_level_question_id);
        return Result.success(list);
    }

}
