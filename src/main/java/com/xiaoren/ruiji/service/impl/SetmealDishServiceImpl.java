package com.xiaoren.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoren.ruiji.entity.SetmealDish;
import com.xiaoren.ruiji.mapper.SetmealDIshMapper;
import com.xiaoren.ruiji.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service("SetmealDishService")
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDIshMapper, SetmealDish> implements SetmealDishService {
}
