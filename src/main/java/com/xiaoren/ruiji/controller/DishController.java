package com.xiaoren.ruiji.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.DishDto;
import com.xiaoren.ruiji.entity.Dish;
import com.xiaoren.ruiji.service.CategoryService;
import com.xiaoren.ruiji.service.DIshService;
import com.xiaoren.ruiji.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品口味
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DIshService dIshService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> saveDishflavor(@RequestBody DishDto dishDto){
        dIshService.saveDishflavor(dishDto);

        return R.success("新增菜品成功");
    }

    /**
     * 菜品分类分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R<Page> pageDish(int page ,int pageSize,String name){
        return dIshService.pageDish(page,pageSize,name);
    }


    /**
     * 根据id来查询菜品信息和口味
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> selectDish(@PathVariable Long id){
        return dIshService.selectDish(id);
    }


    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        return dIshService.updateDish(dishDto);
    }


    /**
     * 根据条件来查询菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> selectlist(Dish dish){
        return dIshService.selectlist(dish);
    }

}
