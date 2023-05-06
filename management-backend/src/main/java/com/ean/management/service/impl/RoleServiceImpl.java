package com.ean.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.management.commons.Result;
import com.ean.management.constants.ResCode;
import com.ean.management.mapper.RoleMapper;
import com.ean.management.mapper.RoleMenuMapper;
import com.ean.management.model.domain.Role;
import com.ean.management.model.domain.RoleMenu;
import com.ean.management.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Asphyxia
* @description 针对表【tb_role】的数据库操作Service实现
* @createDate 2023-05-03 11:19:05
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Result<Map<String, Object>> getRoleList(String roleName, Long pageNo, Long pageSize) {
        // 校验数据
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(org.springframework.util.StringUtils.hasLength(roleName),Role::getRoleName, roleName);
        // 构造分页
        Page page = new Page(pageNo,pageSize);
        this.page(page,queryWrapper);
        // 返回结果
        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @Override
    @Transactional
    public Result<Role> getRoleById(Integer id) {
        Role role = this.getById(id);
        if (role == null) {
            return Result.error(ResCode.ERROR,"角色不存在");
        }
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        role.setMenuIdList(menuIdList);
        return Result.success(role,"回显成功");
    }

    @Override
    @Transactional
    public Result updateRole(Role role) {
        String roleName = role.getRoleName();
        if (StringUtils.isBlank(roleName)) {
            return Result.error();
        }
        String roleDesc = role.getRoleDesc();
        if (StringUtils.isBlank(roleDesc)) {
            return Result.error();
        }
        // 更新操作
        // 操作的是role实体
        boolean success = this.updateById(role);
        if (!success) {
            return Result.error();
        }
        // 更新role应该清除role_menu的关系映射
        // 删除原有的权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getRoleId());
        int count = roleMenuMapper.delete(wrapper);
        if (count < 0) {
            Result.error("权限删除出现错误");
        }
        // 新增修改的权限
        if (null != role.getMenuIdList()) {
            for (Integer menuId : role.getMenuIdList()) {
                roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
            }
        }
        return Result.success(ResCode.SUCCESS,"更新成功");
    }

    @Override
    @Transactional
    public Result addRole(Role role) {
        String roleName = role.getRoleName();
        String roleDesc = role.getRoleDesc();
        if (StringUtils.isBlank(roleName) || StringUtils.isBlank(roleDesc)) {
            return Result.error("必填信息为空");
        }
        // 将role数据保存到role表中
        boolean res = this.save(role);
        if (!res) {
            return Result.error("添加失败");
        }
        // 将role写入role_menu关系表中
        if (null != role.getMenuIdList()) {
            for (Integer menuId : role.getMenuIdList()) {
                roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
            }
        }
        return Result.success(ResCode.SUCCESS,"添加成功");
    }

    @Override
    @Transactional
    public Result<Role> deleteRoleById(Integer id) {
        // 删除role实体
        boolean success = this.removeById(id);
        if (!success) {
            return Result.error("删除失败");
        }
        // 删除role_menu关系映射
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        int count = roleMenuMapper.delete(wrapper);
        if (count < 0) {
            return Result.error("映射关系删除出错");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result getAllRole() {
        List<Role> roleList = this.list();
        return Result.success(roleList);
    }
}




