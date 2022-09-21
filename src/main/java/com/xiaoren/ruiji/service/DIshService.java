package com.xiaoren.ruiji.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.dto.DishDto;
import com.xiaoren.ruiji.entity.Dish;

import java.util.List;

public interface DIshService extends IService<Dish> {
  public   void saveDishflavor(DishDto dishDto);

    R<Page> pageDish(int page, int pageSize, String name);

    R<DishDto> selectDish(Long id);

    R<String> updateDish(DishDto dishDto);

  R<List<Dish>> selectlist(Dish dish);
}
