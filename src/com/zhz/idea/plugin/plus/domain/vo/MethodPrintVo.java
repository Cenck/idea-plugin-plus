package com.zhz.idea.plugin.plus.domain.vo;

import org.apache.http.util.TextUtils;

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
    /**
     * 测试方法名 可能和实际调用方法名不同
     */
    private String testMethodName;
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

    public String getTestMethodName() {
        if (TextUtils.isBlank(this.testMethodName)) {
            return this.getMethodName();
        }
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof MethodPrintVo) {
            // 方法名相同，参数列表相等，才被认为是同一个方法
            boolean isNameEqual = ((MethodPrintVo) obj).methodName != null && ((MethodPrintVo) obj).methodName.equals(methodName);
            return isNameEqual && this.paramList.equals(((MethodPrintVo) obj).getParamList());
        }
        return super.equals(obj);
    }
}
