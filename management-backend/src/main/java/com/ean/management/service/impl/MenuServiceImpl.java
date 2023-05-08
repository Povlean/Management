package com.ean.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.management.commons.Result;
import com.ean.management.mapper.MenuMapper;
import com.ean.management.model.domain.Menu;
import com.ean.management.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Asphyxia
* @description 针对表【tb_menu】的数据库操作Service实现
* @createDate 2023-05-04 11:02:07
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Result<List<Menu>> getAllMenu() {
        // 1.一级菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,0);
        List<Menu> menuList = this.list(queryWrapper);
        setMenuChildren(menuList);
        return Result.success(menuList,"回显成功");
    }

    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {
        // 获取一级菜单
        List<Menu> menuList = menuMapper.getMenuListByUserId(userId, 0);
        // 子菜单
        setMenuChildrenByUserId(userId,menuList);
        return menuList;
    }

    private void setMenuChildrenByUserId(Integer userId, List<Menu> menuList) {
        if (null != menuList) {
            for (Menu menu : menuList) {
                // 获取二级
                List<Menu> subMenuList = menuMapper.getMenuListByUserId(userId, menu.getMenuId());
                menu.setChildren(subMenuList);
                // 递归
                setMenuChildrenByUserId(userId,subMenuList);
            }
        }
    }

    public void setMenuChildren(List<Menu> menuList) {
        if (menuList != null || menuList.size() > 0) {
            for (Menu menu : menuList) {
                LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper();
                wrapper.eq(Menu::getParentId,menu.getMenuId());
                List<Menu> subMenuList = this.list(wrapper);
                menu.setChildren(subMenuList);
                // 递归
                setMenuChildren(subMenuList);
            }
        }
    }
}




