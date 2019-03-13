package com.example.demo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.ResponseHelper;
import com.example.demo.dto.ResponseModel;
import com.example.demo.entity.ExamAnswerSkillEntity;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "单表和多表操作示例")
@RestController
@Slf4j
@Validated
@RequestMapping("/testDemo")
public class ExamAnswerSkillController {

    /**
    * @Description: 新增
    *
    * @Author wangyanlin
    * @Date 15:40 2019/3/13
    * @Param
    * @return
    **/
    public ResponseModel addOrUpdate(){
        return ResponseHelper.success();
    }
    /**
    * @Description: 分页查询
    *
    * @Author wangyanlin
    * @Date 15:41 2019/3/13
    * @Param
    * @return
    **/
    public ResponseModel<Page<ExamAnswerSkillEntity>> selectList(){
        Page<ExamAnswerSkillEntity> page=new Page<>();
        return ResponseHelper.success(page);
    }
}
