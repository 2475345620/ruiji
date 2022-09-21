package com.xiaoren.ruiji.dto;

import com.xiaoren.ruiji.entity.Dish;
import com.xiaoren.ruiji.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
