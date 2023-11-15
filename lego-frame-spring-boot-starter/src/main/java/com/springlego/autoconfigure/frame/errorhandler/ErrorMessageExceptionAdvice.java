package com.springlego.autoconfigure.frame.errorhandler;

import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 全局异常捕获类
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/10/29 17:07
 **/
@RestControllerAdvice
public class ErrorMessageExceptionAdvice {

    Logger logger = LoggerFactory.getLogger(ErrorMessageExceptionAdvice.class);


    /**
     * @desc: 全局默认异常
     * @param request
     * @param e
     * @return: com.springlego.starter.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
     * @date:   2019/10/29 17:15
     * @update:
     */
    @ExceptionHandler(value = Exception.class)
    public ReturnDatas defaultException(HttpServletRequest request, Exception e) {
        logger.error(e.getMessage(), e);
        ReturnDatas returnDatas = ReturnDatas.getErrorReturnDatas(FrameCodeEnum.E_UNKNOWN_ERROR);
        return returnDatas;
    }



    /**
     * @desc: 自定义异常捕获
     * @param request
     * @param e
     * @return: com.springlego.starter.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
     * @date:   2019/10/29 17:15
     * @update:
     */
    @ExceptionHandler(value = ErrorMessageException.class)
    public ReturnDatas errorMessageException(HttpServletRequest request, ErrorMessageException e) {
        ReturnDatas returnDatas = new ReturnDatas(e.getErrorCode(),e.getMessage());
        return returnDatas;
    }

}
