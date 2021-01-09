package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkItemId") Integer checkItemId);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdByCheckGroup(Integer id);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    void deleteById(int id);

    int findSetmealCountByCheckGroupId(int id);
}
