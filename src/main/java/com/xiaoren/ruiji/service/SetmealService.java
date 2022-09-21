package com.xiaoren.ruiji.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.SetmealDto;
import com.xiaoren.ruiji.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    R<String> insertSetmeal(SetmealDto setmealDto);

    R<Page> selectSetmeal(int page, int pageSize, String name);

    R<String> deleteSetmeal(List<Long> ids);
}
