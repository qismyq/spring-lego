package net.yunqihui.starter.frame.exception;

import net.yunqihui.starter.frame.entity.ReturnDatas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 全局异常捕获类
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/10/29 17:07
 **/
@RestControllerAdvice
public class ErrorMessageExceptionAdvice {

    Logger logger = LoggerFactory.getLogger(ErrorMessageExceptionAdvice.class);


    /**
     * @desc: 全局默认异常
     * @param request
     * @param e
     * @return: net.yunqihui.starter.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/10/29 17:15
     * @update:
     */
    @ExceptionHandler(value = Exception.class)
    public ReturnDatas defaultException(HttpServletRequest request, Exception e) {
        logger.error(e.getMessage(), e);
        ReturnDatas returnDatas = ReturnDatas.getErrorReturnDatas().setMessage("系统异常，请稍后再试");
        return returnDatas;
    }



    /**
     * @desc: 自定义异常捕获
     * @param request
     * @param e
     * @return: net.yunqihui.starter.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/10/29 17:15
     * @update:
     */
    @ExceptionHandler(value = ErrorMessageException.class)
    public ReturnDatas errorMessageException(HttpServletRequest request, Exception e) {
        ReturnDatas returnDatas = ReturnDatas.getErrorReturnDatas().setMessage(e.getMessage());
        return returnDatas;
    }

}
