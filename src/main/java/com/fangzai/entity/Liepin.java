package com.fangzai.entity;

import java.math.BigDecimal;
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
 * @since 2023-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("liepin")
public class Liepin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职位
     */
    private String position;

    /**
     * 城市
     */
    private String city;

    /**
     * 薪资
     */
    private String salary;

    /**
     * 经验
     */
    private String experience;

    /**
     * 学历
     */
    private String education;

    /**
     * 标签
     */
    private String tags;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 公司规模
     */
    private String companyScale;

    /**
     * 职位详情
     */
    private String href;

    /**
     * 最小薪资
     */
    private BigDecimal salaryMin;

    /**
     * 最大薪资
     */
    private BigDecimal salaryMax;

    /**
     * 平均薪资
     */
    private BigDecimal salaryAverage;


}
