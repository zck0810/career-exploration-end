package com.fangzai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fangzai.entity.Liepin;
import com.fangzai.service.ILiepinService;
import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
public class PythonController {

    @Resource
    ILiepinService liepinService;

    @PostMapping(value = "/runPython")
    public Result runPythonScript(@RequestParam(name = "inputValue") String desc) {
        // 执行Python脚本并返回结果
        Result result1 = executePythonScript(desc);
        String data = (String) result1.getData();
        //分割得到单个元素
        String[] split = data.split(",");
        LambdaQueryWrapper<Liepin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .and(wrapper -> {
                    wrapper.or().like(Liepin::getCity, split[0])
                            .or().like(Liepin::getTags, split[0])
                            .or().like(Liepin::getEducation, split[0])
                            .or().like(Liepin::getPosition, split[0])
                            .or().like(Liepin::getSalary, split[0])
                            .or().like(Liepin::getExperience, split[0]);
                })
                .and(wrapper -> {
                    wrapper.or().like(Liepin::getCity, split[1])
                            .or().like(Liepin::getTags, split[1])
                            .or().like(Liepin::getEducation, split[1])
                            .or().like(Liepin::getPosition, split[1])
                            .or().like(Liepin::getSalary, split[1])
                            .or().like(Liepin::getExperience, split[1]);
                })
                .or(wrapper -> {
                    wrapper.like(Liepin::getCity, split[2]).or().like(Liepin::getTags, split[2])
                            .or().like(Liepin::getEducation, split[2])
                            .or().like(Liepin::getPosition, split[2])
                            .or().like(Liepin::getSalary, split[2])
                            .or().like(Liepin::getExperience, split[2]);
                })
                .or(wrapper -> {
                    wrapper.like(Liepin::getCity, split[3]).or().like(Liepin::getTags, split[3])
                            .or().like(Liepin::getEducation, split[3])
                            .or().like(Liepin::getPosition, split[3])
                            .or().like(Liepin::getSalary, split[3])
                            .or().like(Liepin::getExperience, split[3]);
                })
                .or(wrapper -> {
                    wrapper.like(Liepin::getCity, split[4])
                            .or().like(Liepin::getTags, split[4])
                            .or().like(Liepin::getEducation, split[4])
                            .or().like(Liepin::getPosition, split[4])
                            .or().like(Liepin::getSalary, split[4])
                            .or().like(Liepin::getExperience, split[4]);
                })
                .last("LIMIT 10");
        List<Liepin> list = liepinService.list(queryWrapper);
        return Result.success(list);
    }

    private Result executePythonScript(String inputData) {
        String result = "";
        try {
            // 设置Python脚本路径和参数
            String pythonScriptPath = "E:\\tempProject\\career-exploration\\KeyBERT\\test\\test1.py";
            String[] cmd = {"python", pythonScriptPath, inputData};

            // 创建进程并执行Python脚本
            Process process = Runtime.getRuntime().exec(cmd);

            // 读取Python脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            // 等待脚本执行完成
            int exitCode = process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(result);
    }
}
