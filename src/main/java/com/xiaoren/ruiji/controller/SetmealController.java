package com.xiaoren.ruiji.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.SetmealDto;
import com.xiaoren.ruiji.service.SetmealDishService;
import com.xiaoren.ruiji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理
 */
@RequestMapping("/setmeal")
@RestController
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;


    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> insertSetmeal(@RequestBody SetmealDto setmealDto){
        return setmealService.insertSetmeal(setmealDto);
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> selectSetmeal(int page ,int pageSize,String name){
        return setmealService.selectSetmeal(page,pageSize,name);
    }


    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam List<Long> ids){
        return setmealService.deleteSetmeal(ids);
    }



}
