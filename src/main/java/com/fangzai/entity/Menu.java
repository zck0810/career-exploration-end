package com.fangzai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单实体
 */
@Data
@TableName("sys_menu")
public class Menu {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 父菜单ID
     */
    private Long parentId;
    
    /**
     * 菜单名称
     */
    private String menuName;
    
    /**
     * 菜单路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 菜单类型 1:目录 2:菜单 3:按钮
     */
    private Integer menuType;
    
    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 是否显示 1:显示 0:隐藏
     */
    private Integer visible;
    
    /**
     * 状态 1:启用 0:禁用
     */
    private Integer status;
    
    /**
     * 系统标识 用于区分不同的子系统
     */
    private String systemCode;
    
    /**
     * 外部链接地址 用于跳转到其他子系统
     */
    private String externalUrl;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    private Long createBy;
    
    /**
     * 更新人
     */
    private Long updateBy;
    
    /**
     * 逻辑删除 1:删除 0:未删除
     */
    private Integer deleted;
    
    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;
}