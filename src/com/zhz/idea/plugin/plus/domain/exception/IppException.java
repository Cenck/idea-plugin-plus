package com.zhz.idea.plugin.plus.domain.exception;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 16:12
 */
public class IppException extends RuntimeException{

    public IppException(){
        super();
    }
    public IppException(String msg){
        super(msg);
    }

}
