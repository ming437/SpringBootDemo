package com.chunqiu.mrjuly.common.utils.Interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor interceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns指定拦截器要拦截的路径
        //excludePathPatterns指定拦截器不拦截的路径
        registry.addInterceptor(interceptor).addPathPatterns("/admin/**").
                excludePathPatterns("/admin/login","/admin/index","/admin/login/doLogin",
                                    "/admin/login/logout","/admin/welcome","/admin/hotelinfo/hotelInfo",
                                    "/admin/advertiserinfo/advertiserInfo/myAdvertiserFinanceIndex",
                                    "/admin/advertiserinfo/advertiserInfo/updateMyAdvertiserFinance","/admin/shopinfo/ShopCreate/index",
                                    "/admin/shopinfo/ShopCreate/map","/admin/shopinfo/ShopCreate/returnMap","/admin/shopinfo/ShopCreate/select",
                                    "/admin/shopinfo/ShopCreate/save","/admin/upload/uploadImg","/admin/upload/uploadFile","/admin/hotelinfo/hotelInfo/getNewTime",
                                    "/admin/system/user/userList","/admin/system/setup/tokenUser","/admin/receptionInfo/receptionInfo",
                                    "/admin/receptionInfo/videoInfo","/admin/receptionInfo/selAddress","/admin/receptionInfo/websitePositionList","/admin/api/upload/springVideoUpload",
                                    "/admin/api/upload/ueditor","/admin/shopinfo/ShopCreate/saveOrUpdate","/admin/upload/ossUploadImg",
                                    "/admin/alipay/notifyResult","/admin/alipay/returnResult");
    }
}
