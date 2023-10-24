package com.fangzai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 赵长开
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fresh_graduate_salary")
public class FreshGraduateSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 大专不分区
     */
    private Integer collegeDegree;

    /**
     * 本科不分区
     */
    private Integer undergraduateUnzoned;

    /**
     * 普通本科院校
     */
    private Integer generalUndergraduate;

    /**
     * 重点本科院校
     */
    private Integer keyUndergraduate;

    /**
     * 硕士不分区
     */
    private Integer masterUnzoned;

    /**
     * 普通硕士院校
     */
    private Integer generalMaster;

    /**
     * 重点硕士院校
     */
    private Integer keyMaster;

    /**
     * 博士不分区
     */
    private Integer doctorUnzoned;

    /**
     * 普通博士院校
     */
    private Integer generalDoctor;

    /**
     * 重点博士院校
     */
    private Integer keyDoctor;

    /**
     * 时间
     */
    private String time;


}
