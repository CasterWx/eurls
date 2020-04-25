package com.antzuhl.mall.service;

import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.Category;

import java.util.List;

public interface CategoryService {

    ServiceResponse<String> addCategory(String categoryName, Integer parentId);

    ServiceResponse<List<Category>> getChildrenCategory(Integer categoryId);

    ServiceResponse<List<Category>> getDeepChildrenCategory(Integer categoryId);
}
