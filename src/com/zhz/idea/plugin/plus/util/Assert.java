package com.zhz.idea.plugin.plus.util;

import com.zhz.idea.plugin.plus.domain.exception.IppException;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 16:15
 */
public class Assert {
    private Assert(){}

    public static void notNull(Object o,String msg){
        if (o == null){
            throw new IppException(msg);
        }
    }
}
