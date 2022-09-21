package com.xiaoren.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoren.ruiji.common.CustomException;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.SetmealDto;
import com.xiaoren.ruiji.entity.Category;
import com.xiaoren.ruiji.entity.Setmeal;
import com.xiaoren.ruiji.entity.SetmealDish;
import com.xiaoren.ruiji.mapper.CategoryMapper;
import com.xiaoren.ruiji.mapper.SetmealDIshMapper;
import com.xiaoren.ruiji.mapper.SetmealMapper;
import com.xiaoren.ruiji.service.SetmealDishService;
import com.xiaoren.ruiji.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("SetmealService")
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDIshMapper setmealDIshMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public R<String> insertSetmeal(SetmealDto setmealDto) {
//        保存套餐的基本信息，操作setmeal，执行insert
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

//        保存套餐和菜品关联的信息，操作seetmeal_dish，执行insert
        setmealDishService.saveBatch(setmealDishes);

        return R.success("新增套餐成功");
    }

    @Override
    public R<Page> selectSetmeal(int page, int pageSize, String name) {

        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();


        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
//        根据更新时间降序排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealMapper.selectPage(pageInfo, queryWrapper);
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto dto = new SetmealDto();
            BeanUtils.copyProperties(item, dto);

            Long categoryId = item.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dto.setCategoryName(categoryName);

            }
            return dto;
        }).collect(Collectors.toList());


        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @Override
    @Transactional
    public R<String> deleteSetmeal(List<Long> ids) {
//        查询套餐状态，确认是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);
        //        不可以删除，抛出异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖，不能删除");
        }

//        可以删除先删除套餐表中的数据.setmeal表
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        //        删除关系表中的数据, setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
        return R.success("套餐删除成功");
    }
}
