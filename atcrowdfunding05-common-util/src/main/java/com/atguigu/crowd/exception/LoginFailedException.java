package com.atguigu.crowd.exception;

/**
 * 登录失败后抛出的异常
 * @author 杨鹏炜
 * @data 2021/4/2  22:37:40
 */
public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    protected LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final long serialVersionUID = 1L;
}
