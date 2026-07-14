package cn.xh_net.studio.result;

import lombok.Data;

import static cn.xh_net.studio.constant.StatusConstant.SERVER_ERROR;
import static cn.xh_net.studio.constant.StatusConstant.SUCCESS;

/**
 * 统一响应结果
 * @param <T>
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 成功响应
     * @param <T>
     * @return 成功响应结果
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = SUCCESS;
        result.message = "success";
        return result;
    }

    /**
     * 成功响应
     * @param <T>
     * @param data 响应数据
     * @return 成功响应结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = SUCCESS;
        result.message = "success";
        result.data = data;
        return result;
    }

    /**
     * 错误响应
     * @param msg 错误消息
     * @return 错误响应结果
     */
    public static <T> Result<T> error(String msg) {
        return error(SERVER_ERROR, msg);
    }

    /**
     * 错误响应
     * @param <T>
     * @param code 错误码
     * @param msg 错误消息
     * @return 错误响应结果
     */
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = msg;
        return result;
    }
}
