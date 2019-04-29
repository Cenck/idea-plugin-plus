package com.zhz.idea.plugin.plus.domain.aggregate;

import com.zhz.idea.plugin.plus.domain.IPrintText;
import com.zhz.idea.plugin.plus.domain.vo.MethodPrintVo;
import com.zhz.idea.plugin.plus.domain.vo.VariableVo;
import com.zhz.idea.plugin.plus.util.PrintIOUtil;
import com.zhz.idea.plugin.plus.util.PrintTextUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 专门用于打印的实体
 *
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-23 22:40
 */

public class PrintTextAgg implements Serializable, IPrintText {

    private String pkg;
    private Set<String> importList = new HashSet<>();
    private String className;
    //输出路径
    private String outPath;
    private String superClass;
    /**
     * 默认服务
     */
    private VariableVo defaultService;
    /**
     * 全局变量
     */
    private List<VariableVo> vars = new ArrayList<>();
    private Set<MethodPrintVo> methods = new HashSet<>();


    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void setImportList(Set<String> importList) {
        this.importList = importList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public List<VariableVo> getVars() {
        return vars;
    }

    public void setVars(List<VariableVo> vars) {
        this.vars = vars;
    }

    public Set<MethodPrintVo> getMethods() {
        this.renderMethods();
        return methods;
    }

    public void setMethods(Set<MethodPrintVo> methods) {
        this.methods = methods;
    }

    public VariableVo getDefaultService() {
        return defaultService;
    }

    public void setDefaultService(VariableVo defaultService) {
        this.defaultService = defaultService;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    @Override
    public String toData() {
        return PrintTextUtil.printToData(this);
    }

    @Override
    public void writeToNewFile() throws IOException {
        PrintIOUtil.writeTestFile(this.getOutPath(), this.toData());
    }

    @Override
    public void renderMethods() {
        Set<String> set = new HashSet<>(methods.size());
        for (MethodPrintVo method : methods) {
            String testMethodName = method.getMethodName();
            if (set.contains(testMethodName)) {
                // 集合有值，重命名
                testMethodName = method.getMethodName() + "_1";
                method.setTestMethodName(testMethodName);
            }
            set.add(testMethodName);
        }
    }
}
