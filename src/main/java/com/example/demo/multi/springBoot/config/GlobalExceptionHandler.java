package com.example.demo.multi.springBoot.config;

import com.example.demo.multi.springBoot.constant.ResponseCodeEnum;
import com.example.demo.multi.springBoot.vo.CommonResponseVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.multi.springBoot.constant.StringConstants.SYS_EXCEPTION_WITH_COLON;

/**
 * 全局异常处理配置
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    @Autowired
    private CustomizedMessageSource messageSource;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResponseVo handleException(Exception e){
        CommonResponseVo responseVo = new CommonResponseVo();
        responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
        responseVo.setMsg(messageSource.getMessage("system.exception"));

        LOGGER.error(SYS_EXCEPTION_WITH_COLON + e.getMessage());
        return responseVo;
    }
}
