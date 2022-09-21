package com.xiaoren.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoren.ruiji.common.CustomException;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.entity.Category;
import com.xiaoren.ruiji.entity.Dish;
import com.xiaoren.ruiji.entity.Setmeal;
import com.xiaoren.ruiji.mapper.CategoryMapper;
import com.xiaoren.ruiji.mapper.DishMapper;
import com.xiaoren.ruiji.mapper.SetmealMapper;
import com.xiaoren.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CategoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category > implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public R<String> saveCategory(Category category) {
        categoryMapper.insert(category);
        return R.success("新增分类成功");
    }

    @Override
    public R<Page> pageCategory(int page, int pageSize) {
        Page<Category> pageInfo= new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> queryWrapper =new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Category::getSort);

        pageInfo=  categoryMapper.selectPage(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> deleteCategory(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper =new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        Integer count1 = dishMapper.selectCount(dishLambdaQueryWrapper);
        if (count1>0){
            throw  new CustomException("当前分类项关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        Integer count2 = setmealMapper.selectCount(setmealLambdaQueryWrapper);
        if (count2>0){
            throw  new CustomException("当前分类关联了套餐，不能删除");
        }
        categoryMapper.deleteById(ids);
        return R.success("删除菜品成功！");
    }

    @Override
    public R<String> updateCategory(Category category) {
        categoryMapper.updateById(category);
        return R.success("修改菜品信息成功");
    }

    @Override
    public R<List<Category>> selectList(Category category) {
//        条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
//        添加排序条件
        queryWrapper.orderByDesc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryMapper.selectList(queryWrapper);
        return R.success(list);
    }
}
