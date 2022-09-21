package com.xiaoren.ruiji.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.entity.Category;

import java.util.List;


public interface CategoryService {
    R<String> saveCategory(Category category);

    R<Page> pageCategory(int page, int pageSize);

    R<String> deleteCategory(Long ids);

    R<String> updateCategory(Category category);

    R<List<Category>> selectList(Category category);
}
