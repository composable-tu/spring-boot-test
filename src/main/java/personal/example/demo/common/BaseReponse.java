package personal.example.demo.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseReponse<T> {
    private int status;
    private String message;
    private T data;

    public BaseReponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BaseReponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> BaseReponse<T> success(T data) {
        return new BaseReponse<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> BaseReponse<T> success(String message, T data) {
        return new BaseReponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> BaseReponse<T> error(int status, String message) {
        return new BaseReponse<>(status, message, null);
    }

    public static <T> BaseReponse<T> error(String message) {
        return new BaseReponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    private static <T> BaseReponse<T> badRequest(String message) {
        return new BaseReponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static <T> BaseReponse<T> badRequest(String message, T data) {
        return new BaseReponse<>(HttpStatus.BAD_REQUEST.value(), message, data);
    }

    public static <T> BaseReponse<T> notFound(String message) {
        return new BaseReponse<>(HttpStatus.NOT_FOUND.value(), message, null);
    }

    public static <T> BaseReponse<T> notFound(String message, T data) {
        return new BaseReponse<>(HttpStatus.NOT_FOUND.value(), message, data);
    }

}
