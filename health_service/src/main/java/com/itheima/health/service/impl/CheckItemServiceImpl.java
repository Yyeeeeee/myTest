package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
//使用alibaba的包，发布服务 interfaceClass指定实现的接口
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //模糊查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //查询
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public CheckItem findById(int id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public void deleteById(int id)throws HealthException{
        //获取查询到的条数
        int flag = checkItemDao.findCountByCheckItemId(id);
        //判断如果有则抛出异常
        if (flag>0){
            throw new HealthException(MessageConstant.DELETE_CHECKITEM_FAIL);
        }else {
            checkItemDao.deleteById(id);
        }
    }
}
