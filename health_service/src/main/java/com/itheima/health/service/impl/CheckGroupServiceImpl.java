package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(CheckGroup checkGroup, Integer[] ids) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取添加后的ID
        Integer checkGroupId = checkGroup.getId();
        //遍历检查项ID
        if (ids!=null){
            for (Integer checkItemId : ids) {
                //循环添加
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkItemId);
            }
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //调用持久层查询
        Page<CheckGroup> checkGroups = checkGroupDao.findPage(queryPageBean.getQueryString());
        //封装返回结果
        PageResult<CheckGroup> result = new PageResult<>(checkGroups.getTotal(),checkGroups.getResult());
        return result;
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdByCheckGroup(Integer id) {
        return checkGroupDao.findCheckItemIdByCheckGroup(id);
    }

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组
        checkGroupDao.update(checkGroup);
        //清除原有
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //更新现有ID
        if (null!=checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        //判断检查组是否被套餐使用
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count>0){
            //抛出自定义错误
            throw new HealthException("检查组被套餐使用中，不能进行删除！");
        }
        //多表删除需要先删除外键！！！
        //删除检查组与检查项关联
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //删除检查组
        checkGroupDao.deleteById(id);
    }
}
