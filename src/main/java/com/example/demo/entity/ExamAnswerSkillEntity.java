package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 答题技巧表
 * </p>
 *
 * @author wyl
 * @since 2019-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exam_answer_skill")
@ApiModel(value="ExamAnswerSkillEntity对象", description="答题技巧表")
public class ExamAnswerSkillEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "科目(科一、科四)")
    private Integer subject;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态（未发布、已发布、已撤回）")
    private Integer status;

    @ApiModelProperty(value = "点击量")
    private Integer clickCount;

    @ApiModelProperty(value = "逻辑删除标识（0.未删除 1.已删除）")
    @TableLogic
    private Boolean deleteFlag;

    @ApiModelProperty(value = "创建人id")
    private String createdUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "最后更新人id")
    private String updatedUserId;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime lastUpdatedTime;

    @ApiModelProperty(value = "发布人")
    private String publishUserId;

    @ApiModelProperty(value = "撤回人")
    private String revokeUserId;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "撤回时间")
    private LocalDateTime revokeTime;


}
