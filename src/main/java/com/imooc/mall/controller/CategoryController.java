package com.imooc.mall.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 */
@Controller
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    /**
     * 后台添加目录
     *
     * @param session
     * @param addCategoryReq
     * @return
     */
    @ApiOperation("后台添加目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        System.out.println(addCategoryReq.toString());

        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        boolean adminRole = userService.checkAdminRole(currentUser);
        //校验是否是管理员
        if (!adminRole) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        } else {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }
    }

    @ApiOperation("后台更新目录")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq) {
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
//        boolean adminRole = userService.cheackAminRole(currentUser);
//        //校验是否是管理员
//        if(!adminRole){
//            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
//        }
//        else{
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryReq , category);
        categoryService.update(category);
        return ApiRestResponse.success();
//        }
    }

    @ApiOperation("后台删除目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台目录列表")
    @GetMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("前台目录列表")
    @GetMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOS);
    }

}
