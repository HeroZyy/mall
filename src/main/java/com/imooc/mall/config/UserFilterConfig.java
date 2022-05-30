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
public class UserFilterConfig {
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }

    @Bean(name="userFilterConf")
    public FilterRegistrationBean adminFilterConfig(){
        FilterRegistrationBean filterFilterRegistrationBean = new FilterRegistrationBean();
        filterFilterRegistrationBean.setFilter(userFilter());
        filterFilterRegistrationBean.addUrlPatterns("/cart/*");
        filterFilterRegistrationBean.addUrlPatterns("/order/*");
        filterFilterRegistrationBean.setName("userFilterConf");
        return filterFilterRegistrationBean;
    }
}
