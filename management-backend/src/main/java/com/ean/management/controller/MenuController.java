package com.ean.management.controller;

import com.ean.management.commons.Result;
import com.ean.management.model.domain.Menu;
import com.ean.management.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:TODO
 * @author:Povlean
 */
@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    public Result<List<Menu>> getAllMenu() {
        return menuService.getAllMenu();
    }

}
