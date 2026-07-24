package cn.xh_net.studio.result;

import cn.xh_net.studio.constant.StatusConstant;
import lombok.Data;

import static cn.xh_net.studio.constant.StatusConstant.SERVER_ERROR;
import static cn.xh_net.studio.constant.StatusConstant.SUCCESS;

/**
 * 统一响应结果
 * @author XingHai
 * @date 2026/7/12
 * @description 统一响应结果
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 成功响应
     * @param <T> 响应数据类型
     * @return 成功响应结果
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = SUCCESS;
        result.message = StatusConstant.SUCCESS_MESSAGE;
        return result;
    }

    /**
     * 成功响应
     * @param <T> 响应数据类型
     * @param data 响应数据
     * @return 成功响应结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = SUCCESS;
        result.message = StatusConstant.SUCCESS_MESSAGE;
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
     * @param <T> 响应数据类型
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
