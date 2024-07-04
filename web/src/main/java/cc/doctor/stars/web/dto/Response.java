package cc.doctor.stars.web.dto;

import lombok.Data;

@Data
public class Response<T> {

    private static final int SUCCESS_CODE = 200;

    private int code;

    private String msg;

    private boolean success;

    private T data;

    public static Response<?> success() {
        Response<?> response = new Response<>();
        response.setCode(SUCCESS_CODE);
        response.setSuccess(true);
        return response;
    }

    public static <D> Response<D> success(D data) {
        Response<D> response = new Response<>();
        response.setCode(SUCCESS_CODE);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static Response<?> fail(int code, String message) {
        Response<?> response = new Response<>();
        response.setCode(code);
        response.setMsg(message);
        return response;
    }
}
