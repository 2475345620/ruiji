package com.xiaoren.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.DishDto;
import com.xiaoren.ruiji.entity.DishFlavor;
import com.xiaoren.ruiji.mapper.DishFlavorMapper;
import com.xiaoren.ruiji.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DishflavorService")
@Slf4j
public class DishflavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}
