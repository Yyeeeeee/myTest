package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkItem, Integer[] ids);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdByCheckGroup(Integer id);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(int id);
}
