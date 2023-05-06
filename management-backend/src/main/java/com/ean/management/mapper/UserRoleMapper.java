package com.ean.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.management.model.domain.UserRole;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
* @author Asphyxia
* @description 针对表【tb_user_role】的数据库操作Mapper
* @createDate 2023-05-06 15:18:32
* @Entity generator.domain.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




