package com.wt.adminvue.exception;


import com.wt.adminvue.util.ResultCode;
import lombok.Data;


/**
 * @author helen
 * @since 2020/4/15
 */
@Data
public class GuliException extends RuntimeException {

    private Integer code;

    public GuliException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GuliException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
