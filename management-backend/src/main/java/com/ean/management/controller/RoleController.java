package com.ean.management.controller;

import com.ean.management.commons.Result;
import com.ean.management.constants.ResCode;
import com.ean.management.model.domain.Role;
import com.ean.management.service.RoleService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description:TODO
 * @author:Povlean
 */
@RestController
// 可以用，但是这里使用的是拦截器处理跨域问题
// @CrossOrigin(value = {"http://localhost:8888/","http://192.168.31.88:8888/"})
@Slf4j
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
    * @description:TODO
    * @author:Povlean
    * @date:2023/4/22 21:27
    * @param:* @param null
    * @return:
    */
    @GetMapping("/list")
    public Result<Map<String,Object>> getRoleList(@RequestParam(value = "roleName",required = false) String roleName,
                                                  @RequestParam("pageNo") Long pageNo,
                                                  @RequestParam("pageSize") Long pageSize) {
        return roleService.getRoleList(roleName,pageNo,pageSize);
    }

    @PostMapping("/add")
    public Result addRole(@RequestBody Role role) {
        if (ObjectUtil.isNull(role)) {
            return Result.error("添加信息为空");
        }
        return roleService.addRole(role);
    }

    /**
    * @description:数据回显
    * @author:Povlean
    * @date:2023/4/30 9:48
    * @param:* @param id
    * @return:* @return Result
    */
    @GetMapping("/get/{id}")
    public Result getRoleById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return Result.error(ResCode.ERROR);
        }
        return roleService.getRoleById(id);
    }

    @PutMapping("/update")
    public Result updateRole(@RequestBody Role role) {
        if (ObjectUtil.isNull(role)) {
            return Result.error("更新信息为空");
        }
        return roleService.updateRole(role);
    }

    @DeleteMapping("/{id}")
    public Result deleteRoleById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return Result.error("删除id有误");
        }
        return roleService.deleteRoleById(id);
    }

    @GetMapping("/all")
    public Result getAllRole() {
        return roleService.getAllRole();
    }

}