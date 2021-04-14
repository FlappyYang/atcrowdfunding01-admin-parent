package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;

import java.util.List;

/**
 * @author 杨鹏炜
 * @data 2021/4/4  23:39:28
 */
public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
