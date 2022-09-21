package com.xiaoren.ruiji.dto;

import com.xiaoren.ruiji.entity.Setmeal;
import com.xiaoren.ruiji.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
