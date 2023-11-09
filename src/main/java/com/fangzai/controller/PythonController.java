package com.fangzai.controller;

import com.fangzai.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
public class PythonController {
    @PostMapping("/runPython")
    public Result runPythonScript(@RequestBody Map<String, Object> inputData) {
        Map<String, Object> form = (Map<String, Object>) inputData.get("form");
        String desc = (String) form.get("desc");
        System.out.println(desc);
        // 执行Python脚本并返回结果
        Result result = executePythonScript(inputData);
        return Result.success(result);
    }

    private Result executePythonScript(Map<String, Object> inputData) {
        String result = "";
        try {
            // 设置Python脚本路径和参数
            String pythonScriptPath = "D:\\Model模型\\KeyBERT\\test\\test1.py";
            String[] cmd = {"python", pythonScriptPath, String.valueOf(inputData)};

            // 创建进程并执行Python脚本
            Process process = Runtime.getRuntime().exec(cmd);

            // 读取Python脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }

            // 等待脚本执行完成
            int exitCode = process.waitFor();
            System.out.println("Python脚本执行完成，退出码: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(result);
    }
}
