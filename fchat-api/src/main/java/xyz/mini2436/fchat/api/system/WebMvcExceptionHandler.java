package xyz.mini2436.fchat.api.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import xyz.mini2436.fchat.enums.BusinessEnum;
import xyz.mini2436.fchat.exceptions.DatabaseException;
import xyz.mini2436.fchat.exceptions.MessageException;
import xyz.mini2436.fchat.exceptions.ParameterException;
import xyz.mini2436.fchat.exceptions.ServiceException;
import xyz.mini2436.fchat.model.vo.BasicApiVo;
import xyz.mini2436.fchat.model.vo.ResultVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局自定义异常拦截
 *
 * @author mini2436
 * @date 2021-07-07 13:32
 **/

@RestControllerAdvice
@Slf4j
public class WebMvcExceptionHandler extends BasicApiVo {

    @ExceptionHandler(Exception.class)
    public ResultVo handleCustomException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        if (e instanceof RuntimeException) {
            if (e instanceof MessageException) {
                return error(BusinessEnum.MESSAGE_ERROR,e);
            } else if (e instanceof DatabaseException) {
                return error(BusinessEnum.DATABASE_ERROR,e);
            } else if (e instanceof ParameterException) {
                return error(BusinessEnum.PARAMETER_ERROR,e);
            } else if (e instanceof ServiceException) {
                return error(BusinessEnum.SERVICE_ERROR,e);
            } else if (e instanceof WebExchangeBindException bindException){
                List<String> collect = bindException.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
                return error(BusinessEnum.PARAMETER_ERROR,e,collect);
            } else {
                return error(BusinessEnum.SYSTEM_ERROR,e);
            }
        } else {
            return fail(e.getMessage());
        }
    }
}
