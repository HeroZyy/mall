package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.fillter.UserFilter;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.impl.CartServiceImpl;
import com.imooc.mall.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 * 描述：购物车Controller
 */

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartServiceImpl cartService;
    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    //优雅的用户过滤器 不用HttpSession
    public ApiRestResponse add(@RequestParam Integer productId , @RequestParam Integer count){
        //userId 来自于过滤器
        List<CartVO> cartVOList = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list(){
        //内部获取用户ID，防止横向越权
        List<CartVO> cartVOList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/update")
    @ApiOperation("添加商品到购物车")
    //优雅的用户过滤器 不用HttpSession
    public ApiRestResponse update(@RequestParam Integer productId , @RequestParam Integer count){
        //userId 来自于过滤器
        List<CartVO> cartVOList = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }
    @PostMapping("/delete")
    @ApiOperation("删除购物车")
    //优雅的用户过滤器 不用HttpSession
    public ApiRestResponse delete(@RequestParam Integer productId){
        //userId 来自于过滤器
        //不能传入userId cartId也不能传入 否则可以删除别人的购物车
        List<CartVO> cartVOList = cartService.delete(UserFilter.currentUser.getId(), productId);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    @ApiOperation("选择/不选择购物车的某商品")
    //优雅的用户过滤器 不用HttpSession
    public ApiRestResponse select (@RequestParam Integer productId , @RequestParam Integer selected){
        //userId 来自于过滤器
        List<CartVO> cartVOList = cartService.select(UserFilter.currentUser.getId(), productId , selected);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/selectAll")
    @ApiOperation("全选择/全不选择购物车的某商品")
    //优雅的用户过滤器 不用HttpSession
    public ApiRestResponse selectAll (@RequestParam Integer selected){
        //userId 来自于过滤器
        List<CartVO> cartVOList = cartService.selectAllOrNot(UserFilter.currentUser.getId(),selected);
        return ApiRestResponse.success(cartVOList);
    }
}

