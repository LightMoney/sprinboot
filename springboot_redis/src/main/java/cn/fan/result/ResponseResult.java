package cn.fan.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
//@NoArgsConstructor
@ApiModel("api通用返回接口")
public class ResponseResult<T> implements Response, Serializable {

    /**
     * 操作是否成功
     */
    @ApiModelProperty("操作是否成功")
    boolean success = SUCCESS;

    /**
     * 操作代码FAILURE
     */
    @ApiModelProperty("返回代码，200为成功")
    int code;

    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    String message;

    /**
     * 返回数据
     */
    @ApiModelProperty("返回数据")
    T data;

    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult FAIL() {
        return new ResponseResult(CommonCode.FAIL);
    }

    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    // =====================================  add ~ ====================================================================

    public ResponseResult(ResultCode resultCode, T  data) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(boolean success, T data) {
        this.success = success;
        this.data = data;
        if (success) {
            this.code = CommonCode.SUCCESS.code;
            this.message = "操作成功";
        } else {
            this.code = CommonCode.FAIL.code;
            this.message = "操作失败";
        }
    }

    public ResponseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        //由于api 的调用问题 此处不严谨 修改
        if (success) {
            this.code = CommonCode.SUCCESS.code;
        } else {
            this.code = CommonCode.FAIL.code;
        }
    }

    public ResponseResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        if (success) {
            this.code = CommonCode.SUCCESS.code;
        } else {
            this.code = CommonCode.FAIL.code;
        }
    }
}