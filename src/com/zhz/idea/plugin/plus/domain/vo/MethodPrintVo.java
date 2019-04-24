package com.zhz.idea.plugin.plus.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-24 16:38
 */

public class MethodPrintVo implements Serializable {

    private boolean isStatic;
    private VariableVo returnType;
    private String methodName;
    private List<VariableVo> paramList = new ArrayList<>();

    public VariableVo getReturnType() {
        return returnType;
    }

    public void setReturnType(VariableVo returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<VariableVo> getParamList() {
        return paramList;
    }

    public void setParamList(List<VariableVo> paramList) {
        this.paramList = paramList;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodPrintVo){
            return ((MethodPrintVo) obj).methodName.endsWith(methodName);
        }
        return super.equals(obj);
    }
}
