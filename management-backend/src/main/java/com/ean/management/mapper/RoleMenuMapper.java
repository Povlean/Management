package com.ean.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.management.model.domain.RoleMenu;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
* @author Asphyxia
* @description 针对表【tb_role_menu】的数据库操作Mapper
* @createDate 2023-05-04 20:46:12
* @Entity generator.domain.RoleMenu
*/
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Integer> getMenuIdListByRoleId(Integer roleId);
}




