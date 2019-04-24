package com.zhz.idea.plugin.plus.domain.vo;

import com.zhz.idea.plugin.plus.util.StringFormatUtil;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-24 16:53
 */
public class VariableVo implements Serializable {

    private String name;
    private String type;


    public String getName() {
        if (name == null || name.trim().length() == 0){
            return (name = StringFormatUtil.getVarName(type));
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
