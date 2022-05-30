package com.imooc.mall.common;

import com.google.common.collect.Sets;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 描述 ： 常量值
 */

@Component
public class Constant {

    public static final String IMOOC_MALL_USER = "imooc_mall_user";
    public static final String SALT = "asdsads,.das321ds";
    public static String FILE_UPLOAD_DIR;

    //静态变量无法注入
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc" ,"price asc");
    }

    public interface SaleStatus{
        int NOT_SELL = 0;//商品下架
        int SALE = 1;//商品上架
    }

    public interface Cart{
        int UN_CHECKED = 0;//购物车未选中状态
        int CHECKED = 1;//购物车选中状态
    }
    public enum OrderStatusEnum{
        //使用的就是构造方法OrderStatusEnum(int code ,String value)
        CANCELED(0,"用户已取消"),
        NOT_PAID( 10, "未付款"),
        PAID( 20 , "已付款"),
        DELIVERED( 30, "已发货"),
        FINISHED( 40, "交易完成");

        private int Code;
        private String Value;

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()) {
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);
        }
        OrderStatusEnum(int code ,String value) {
            Value = value;
            Code = code;
        }

        public int getCode() {
            return Code;
        }

        public void setCode(int code) {
            Code = code;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }
}
