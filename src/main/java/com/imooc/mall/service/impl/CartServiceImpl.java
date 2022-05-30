package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;


    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected) {
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }
    @Override
    public List<CartVO> select(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
        else{
            cartMapper.selectOrNot(userId , productId ,selected);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        else{
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return  this.list(userId);
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        validProduct(productId ,count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId , productId);
        if(cart == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
        else{

            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }


    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (CartVO cartVO : cartVOS) {
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId , Integer productId , Integer count){
        //判断商品是否存在 数量是否足够
        validProduct(productId , count);
        //这个商品之前不在购物车里，需要新增一个记录
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            cart= new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        }
        else{
            //这个商品已经再购物车里了，则数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }


    private void validProduct(Integer productId ,Integer count){
        Product product = productMapper.selectByPrimaryKey(productId);
        System.out.println(product);
        //判断商品是否存在，商品是否上架
        if(product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SELL)){
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }

        //判断商品库存
        if(count > product.getStock()){
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }

    }


}
