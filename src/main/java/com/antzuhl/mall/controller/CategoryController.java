package com.antzuhl.mall.controller;

import com.antzuhl.mall.common.Const;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.Category;
import com.antzuhl.mall.pojo.User;
import com.antzuhl.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/manage/category")
@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/addCategory")
    @ResponseBody
    public ServiceResponse<String> addCategory(HttpSession session,
                  String categoryName,
                  @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        if (user.getRole() != Const.ROLE_ADMIN) {
            return ServiceResponse.createErrorResponse("权限不足");
        }
        return categoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping("/getChildrenCategory")
    @ResponseBody
    public ServiceResponse<List<Category>> getChildrenCategory(HttpSession session,
                                                     @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        if (user.getRole() != Const.ROLE_ADMIN) {
            return ServiceResponse.createErrorResponse("权限不足");
        }
        return categoryService.getChildrenCategory(categoryId);
    }

    @RequestMapping("/getDeepChildrenCategory")
    @ResponseBody
    public ServiceResponse<List<Category>> getDeepChildrenCategory(HttpSession session,
                                                                   @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        if (user.getRole() != Const.ROLE_ADMIN) {
            return ServiceResponse.createErrorResponse("权限不足");
        }
        return categoryService.getDeepChildrenCategory(categoryId);
    }
}