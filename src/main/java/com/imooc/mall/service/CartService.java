package com.imooc.mall.service;

import com.imooc.mall.model.vo.CartVO;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 */
public interface CartService {

    List<CartVO> selectAllOrNot(Integer userId, Integer selected);

    List<CartVO> select(Integer userId, Integer productId, Integer selected);

    List<CartVO> delete(Integer userId, Integer productId);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId , Integer productId , Integer count);

}
