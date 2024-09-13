package cc.doctor.stars.biz.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends Exception {

    public static final int INVALID_INPUT_CODE = 400;
    public static final int INVALID_TOKEN = 300;

    private int code;
    private String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
