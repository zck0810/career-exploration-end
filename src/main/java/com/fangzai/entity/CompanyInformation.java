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
 * 公司信息表
 * </p>
 *
 * @author smile
 * @since 2023-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("company_information")
public class CompanyInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String companyName;

    private String manageState;

    private String legalRepresentative;

    private String registeredCapital;

    private String contributedCapital;

    private String establishmentDate;

    private String province;

    private String city;

    private String county;

    private String telephone;

    private String mailbox;

    private String insuredNumber;

    private String enterpriseType;

    private String registeredAddress;

    private String latestAnnualReportAddress;

    private String website;


}
