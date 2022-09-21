package com.xiaoren.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.DishDto;
import com.xiaoren.ruiji.entity.Category;
import com.xiaoren.ruiji.entity.Dish;
import com.xiaoren.ruiji.entity.DishFlavor;
import com.xiaoren.ruiji.mapper.CategoryMapper;
import com.xiaoren.ruiji.mapper.DishFlavorMapper;
import com.xiaoren.ruiji.mapper.DishMapper;
import com.xiaoren.ruiji.service.DIshService;
import com.xiaoren.ruiji.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("DishService")
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DIshService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public void saveDishflavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishDtoId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDtoId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public R<Page> pageDish(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Dish::getName, name);

        queryWrapper.orderByDesc(Dish::getUpdateTime);
        pageInfo = dishMapper.selectPage(pageInfo, queryWrapper);
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {

            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
//            根据id来查询分类的对象
            Category category = categoryMapper.selectById(categoryId);

            if (category!=null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;


        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @Override
    public R<DishDto> selectDish(Long id) {
//        查询基本的菜品信息，在dish表中
        Dish byId = this.getById(id);

        DishDto dishDto =new DishDto();
        BeanUtils.copyProperties(byId,dishDto);

//        查询菜品对应的口味信息，在dish_flavor表中
        LambdaQueryWrapper<DishFlavor> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,byId.getId());
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(queryWrapper);
        dishDto.setFlavors(dishFlavors);


        return R.success(dishDto);
    }

    @Override
    @Transactional
    public R<String> updateDish(DishDto dishDto) {

//        更新dish表基本信息
        this.updateById(dishDto);

//        先删除当前菜品对应的口味，dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorMapper.delete(queryWrapper);

//        添加当前提交过来的口味，dish_flavor表的insert操作
        List<DishFlavor> flavors =dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);




        return R.success("修改成功");
    }

    @Override
    public R<List<Dish>> selectlist(Dish dish) {

        LambdaQueryWrapper<Dish> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        状态为1是在售状态
        queryWrapper.eq(Dish::getStatus,1);

        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishes = dishMapper.selectList(queryWrapper);

        return R.success(dishes);
    }


//    public


}
