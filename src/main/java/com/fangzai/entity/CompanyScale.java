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
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("company_scale")
public class CompanyScale implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal lessThanFifty;

    private BigDecimal fiftyToHundred;

    private BigDecimal hundredToFiveHundred;

    private BigDecimal fiveHundredToThousand;

    private BigDecimal thousandToFiveThousand;

    private BigDecimal greaterThanFiveThousand;


}
