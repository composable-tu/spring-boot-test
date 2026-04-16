package personal.example.demo.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseResponse<T> {
  private int status;
  private String message;
  private T data;

  public BaseResponse(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public BaseResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public static <T> BaseResponse<T> success(T data) {
    return new BaseResponse<>(HttpStatus.OK.value(), "success", data);
  }

  public static <T> BaseResponse<T> success(String message, T data) {
    return new BaseResponse<>(HttpStatus.OK.value(), message, data);
  }

  public static <T> BaseResponse<T> error(int status, String message) {
    return new BaseResponse<>(status, message, null);
  }

  public static <T> BaseResponse<T> error(String message) {
    return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
  }

  public static <T> BaseResponse<T> unauthorized(String message) {
    return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), message, null);
  }

  public static <T> BaseResponse<T> badRequest(String message) {
    return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
  }

  public static <T> BaseResponse<T> badRequest(String message, T data) {
    return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), message, data);
  }

  public static <T> BaseResponse<T> notFound(String message) {
    return new BaseResponse<>(HttpStatus.NOT_FOUND.value(), message, null);
  }

  public static <T> BaseResponse<T> notFound(String message, T data) {
    return new BaseResponse<>(HttpStatus.NOT_FOUND.value(), message, data);
  }
}
