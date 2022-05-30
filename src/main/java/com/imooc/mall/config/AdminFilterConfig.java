package com.imooc.mall.config;

import com.imooc.mall.fillter.AdminFilter;
import com.imooc.mall.fillter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 */
@Configuration
public class AdminFilterConfig {
    @Bean
    public AdminFilter adminFilter(){
        return new AdminFilter();
    }
    @Bean(name="adminFilterConf")
    public FilterRegistrationBean adminFilterConfig(){
        FilterRegistrationBean filterFilterRegistrationBean = new FilterRegistrationBean();
        filterFilterRegistrationBean.setFilter(adminFilter());
        filterFilterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterFilterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterFilterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterFilterRegistrationBean.setName("adminFilterConf");
        return filterFilterRegistrationBean;
    }
}
