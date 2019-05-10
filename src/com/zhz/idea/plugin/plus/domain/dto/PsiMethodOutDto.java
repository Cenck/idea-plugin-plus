package com.zhz.idea.plugin.plus.domain.dto;

import com.zhz.idea.plugin.plus.domain.vo.MethodPrintVo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * psiMethod解析输出内容
 *
 * 更新时，保存 类更新 的所有内容
 *
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-26 17:10
 */
public class PsiMethodOutDto implements Serializable {

    /** 新增的类方法 */
    private List<MethodPrintVo> appendMethodList;
    /** 新增导入 */
    private Set<String> newImportList;


    public List<MethodPrintVo> getAppendMethodList() {
        return appendMethodList;
    }

    public void setAppendMethodList(List<MethodPrintVo> appendMethodList) {
        this.appendMethodList = appendMethodList;
    }

    public Set<String> getNewImportList() {
        return newImportList;
    }

    public void setNewImportList(Set<String> newImportList) {
        this.newImportList = newImportList;
    }
}
