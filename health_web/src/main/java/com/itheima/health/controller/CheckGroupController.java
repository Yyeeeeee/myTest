package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        //调用业务层添加
        checkGroupService.add(checkGroup,checkitemIds);
        //响应结果
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        //返回一个CheckGroup对象
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 根据检查组ID查询检查项ID
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemIdByCheckGroup")
    public Result findCheckItemIdByCheckGroup(Integer id){
        //返回数组
        List<Integer> ids = checkGroupService.findCheckItemIdByCheckGroup(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
    }

    /**
     * 修改检查组属性
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }


}
