package com.ean.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.management.model.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author Asphyxia
* @description 针对表【tb_menu】的数据库操作Mapper
* @createDate 2023-05-04 11:02:07
* @Entity generator.domain.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenuListByUserId(@Param("userId") Integer userId,@Param("pid") Integer pid);
}




