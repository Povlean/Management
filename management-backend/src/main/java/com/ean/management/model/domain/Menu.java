package com.ean.management.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @TableName tb_menu
 */
@TableName(value ="tb_menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    /**
     * 
     */
    private String component;

    /**
     * 
     */
    private String path;

    /**
     * 
     */
    private String redirect;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String icon;

    /**
     * 
     */
    private Integer parentId;

    /**
     * 
     */
    private String isLeaf;

    /**
     * 
     */
    private Integer hidden;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children;

    @TableField(exist = false)
    private Map<String,Object> meta;
    private Map<String,Object> getMeta() {
        meta = new HashMap<>();
        meta.put("title",title);
        meta.put("icon",icon);
        return meta;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}