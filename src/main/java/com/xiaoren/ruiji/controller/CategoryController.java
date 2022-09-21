package com.xiaoren.ruiji.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.entity.Category;
import com.xiaoren.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @GetMapping("/page")
    public R<Page> pageCategory(int page,int pageSize){
        return categoryService.pageCategory(page,pageSize);
    }

    /**
     * 根据id来删除菜品分类
     * @param ids
     * @return
     */

    @DeleteMapping
    public R<String> deleteCategory(Long ids){
        return categoryService.deleteCategory(ids);
    }


    /**
     * 根据id修改菜品信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    /**
     * 根据条件来查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> selectList(Category category){
        return categoryService.selectList(category);
    }
}
