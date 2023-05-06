package com.ean.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.management.commons.Result;
import com.ean.management.model.domain.Role;

import java.util.Map;


/**
* @author Asphyxia
* @description 针对表【tb_role】的数据库操作Service
* @createDate 2023-05-03 11:19:05
*/
public interface RoleService extends IService<Role> {



    Result<Map<String, Object>> getRoleList(String roleName, Long pageNo, Long pageSize);

    Result getRoleById(Integer id);

    Result updateRole(Role role);

    Result addRole(Role role);

    Result deleteRoleById(Integer id);

    Result getAllRole();
}
