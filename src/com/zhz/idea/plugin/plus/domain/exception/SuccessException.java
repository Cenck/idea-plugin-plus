package com.zhz.idea.plugin.plus.domain.exception;

import com.zhz.idea.plugin.plus.domain.enums.ErrorCodeEnums;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-30 09:31
 */
public class SuccessException extends IppException {

    public SuccessException(String msg){
        super(msg);
        this.code = ErrorCodeEnums.success.getCode();
    }

}
