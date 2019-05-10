package com.zhz.idea.plugin.plus.domain.exception;


import com.zhz.idea.plugin.plus.domain.enums.ErrorCodeEnums;
import org.apache.log4j.spi.ErrorCode;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 16:12
 */
public class IppException extends RuntimeException{

    String code;

    Object data;

    public IppException(){
        super();
    }
    public IppException(String msg){
        super(msg);
    }
    public IppException(ErrorCodeEnums errorCode){
        super(errorCode.getDesc());
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public ErrorCodeEnums getError(){
        return ErrorCodeEnums.getByCode(this.getCode());
    }
}
