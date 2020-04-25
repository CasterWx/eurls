package com.antzuhl.mall.service.impl;

import com.antzuhl.mall.common.HResult;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.dao.CategoryMapper;
import com.antzuhl.mall.pojo.Category;
import com.antzuhl.mall.service.CategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServiceResponse<String> addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServiceResponse.createErrorResponse("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        category.setCreateTime(new Date());

        int rowCount = categoryMapper.insert(category);
        if (rowCount == 0) {
            return ServiceResponse.createErrorResponse("品类创建错误");
        }
        return ServiceResponse.createSuccessResponse("品类添加成功");
    }

    @Override
    public ServiceResponse<List<Category>> getChildrenCategory(Integer categoryId) {
        if (categoryId == null) {
            return ServiceResponse.createErrorResponse("参数错误");
        }
        List<Category> result = categoryMapper.selectCategoryChildren(categoryId);
        if (CollectionUtils.isEmpty(result)) {
            return ServiceResponse.createErrorResponse("查询为空");
        }
        return ServiceResponse.createSuccessResponse(HResult.R_OK.getMsg(), result);
    }


    @Override
    public ServiceResponse<List<Category>> getDeepChildrenCategory(Integer parentId) {
        if (parentId == null) {
            return ServiceResponse.createErrorResponse("参数错误");
        }
        Set<Category> categories = Sets.newHashSet();
        findCategory(categories, parentId);
        List<Category> result = Lists.newArrayList();
        for (Category category : categories) {
            result.add(category);
        }
        return ServiceResponse.createSuccessResponse(HResult.R_OK.getMsg(), result);
    }

    public Set<Category> findCategory(Set<Category> categories, Integer parentId) {
        Category category = categoryMapper.selectByPrimaryKey(parentId);
        if (category != null) {
            categories.add(category);
        }
        List<Category> result = categoryMapper.selectCategoryChildren(parentId);
        for (Category cate : result) {
            findCategory(categories, cate.getId());
        }
        return categories;
    }
}
