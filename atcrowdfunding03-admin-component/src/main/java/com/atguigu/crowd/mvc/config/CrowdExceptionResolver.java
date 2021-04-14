package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.utils.CrowdUtil;
import com.atguigu.crowd.utils.ResultEntity;
import com.google.gson.Gson;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 33246
 * @ControllerAdvice表示当前类是一个基于注解的异常处理器类
 */
@ControllerAdvice
public class CrowdExceptionResolver {
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-edit";
        System.out.println("===========resolveLoginAcctAlreadyInUseForUpdateException==========");
        return commonResolve(viewName, exception, request, response);
    }
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveDuplicateKeyException(LoginAcctAlreadyInUseException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        System.out.println("===========resolveDuplicateKeyException==========");
        return commonResolve(viewName, exception, request, response);
    }
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin_login";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin_login";
        return commonResolve(viewName, exception, request, response);
    }
    /**
     * @ExceptionHandler将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveArithmeticException(ArithmeticException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        if (judgeResult) {
            // ajax请求，创建ResultEntity对象
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            // 创建json对象
            Gson gson = new Gson();
            String json = gson.toJson(failed);
            // 将JSON字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            // 由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        // 如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        // 将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        // 设置对应的视图名称
        modelAndView.setViewName(viewName);

        return modelAndView;
    }
}
