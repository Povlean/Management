package com.ean.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.management.commons.Result;
import com.ean.management.model.domain.Menu;

import java.util.List;

/**
* @author Asphyxia
* @description 针对表【tb_menu】的数据库操作Service
* @createDate 2023-05-04 11:02:07
*/
public interface MenuService extends IService<Menu> {
    Result<List<Menu>> getAllMenu();
}
