package cc.doctor.stars.web.filter;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理返回结果异常
     */
    @ExceptionHandler(BusinessException.class)
    public Response<?> returnResultExceptionHandler(final Exception e, HttpServletRequest request, HttpServletResponse response) {
        printException(e);
        response.setStatus(HttpStatus.OK.value());
        return Response.fail(((BusinessException) e).getCode(), e.getMessage());
    }

    /**
     * 处理参数异常
     * RequestParam格式
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> constraintViolationExceptionHandler(final Exception e, HttpServletRequest request, HttpServletResponse response) {
        printException(e);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 处理参数异常
     * json格式
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> methodArgumentNotValidExceptionHandler(final Exception e, HttpServletRequest request, HttpServletResponse response) {
        printException(e);
        MethodArgumentNotValidException se = (MethodArgumentNotValidException) e;
        BindingResult bindingResult = se.getBindingResult();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        FieldError fieldError = bindingResult.getFieldError();
        return Response.fail(HttpStatus.BAD_REQUEST.value(), fieldError == null ? "" : fieldError.getDefaultMessage());
    }

    /**
     * 处理参数异常
     * 实体类格式
     */
    @ExceptionHandler(BindException.class)
    public Response<?> except(final Exception e, HttpServletRequest request, HttpServletResponse response) {
        printException(e);
        BindException bindException = (BindException) e;
        BindingResult bindingResult = bindException.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Response.fail(HttpStatus.BAD_REQUEST.value(), fieldError == null ? "" : fieldError.getDefaultMessage());
    }

    /**
     * 处理RuntimeException异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Response<?> runtimeExceptionHandler(final Exception e, HttpServletRequest request, HttpServletResponse response) {
        printException(e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常");
    }

    /**
     * 打印异常信息
     */
    private void printException(Exception e) {
        logger.error("[全局异常处理] 异常信息: ", e);
    }
}
