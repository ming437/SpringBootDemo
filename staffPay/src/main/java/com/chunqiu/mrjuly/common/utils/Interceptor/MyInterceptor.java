package com.chunqiu.mrjuly.common.utils.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.modules.system.model.User;

@Component
public class MyInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(MyInterceptor.class);
    /**
     * 该方法实在执行执行servlet的 service方法之前执行的
     * 即在进入controller之前调用
     * @return 如果返回true表示继续执行下一个拦截器的PreHandle方法；如果没有拦截器了,则执行controller
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String url = request.getRequestURI();
        StringBuilder info = new StringBuilder("Request Controller >>> ");
        try {
        	HandlerMethod currentHandler = (HandlerMethod) handler;
        	info.append(currentHandler.getBeanType().getName()).append("，  Method >>> ").append(currentHandler.getMethod().getName());
        	log.info(info.toString());
        } catch (Exception e) {
        }
        info = null;
        User user = UserUtils.getUser();
        if (user != null){
            //登录平台是广告商和商家时
            if (user.getRoleType() == 3 || user.getRoleType() == 4){
                if (user.getBusinessId() == null){
                    //广告商平台
                    if (user.getRoleType() == 4){
                        request.getRequestDispatcher("/admin/advertiserinfo/advertiserInfo/myAdvertiserFinanceIndex").forward(request, response);
                        return false;
                        //商家平台
                    }else if (user.getRoleType() == 3){
                        request.getRequestDispatcher("/admin/shopinfo/ShopCreate/index").forward(request, response);
                        return false;
                    }

                }
            }
        }else {
            //返回登录页面
            request.getRequestDispatcher("/admin/system/setup/tokenUser").forward(request, response);
            return false;
        }

        return true;

    }

    /**
     *在执行完controller之后，返回视图之前执行，我们可以对controller返回的结果做处理
     * 执行顺序：先执行最后一个拦截器的postHandle方法，一次向前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception{

//        System.out.println("解析视图之前.....");
    }

    /**
     * 整个请求结束之后，即返回视图之后执行
     *该方法需要同一拦截器的preHandle返回true时执行，
     * 如果该拦截器preHandle返回false，则该拦截器的afterCompletion不执行
     * 执行顺序：先执行最后一个返回true的拦截器的afterCompletion，在依次向前
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) throws Exception{

//        System.out.println("视图解析完成...");
    }


}
