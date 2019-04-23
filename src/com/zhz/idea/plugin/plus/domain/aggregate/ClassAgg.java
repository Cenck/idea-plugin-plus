package com.zhz.idea.plugin.plus.domain.aggregate;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.zhz.idea.plugin.plus.domain.IClassInfoAggregate;
import com.zhz.idea.plugin.plus.util.ClassInfoIoUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 类的聚合
 *
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 16:07
 */
public class ClassAgg implements IClassInfoAggregate {


    /**
     * 方法名
     */
    private List<PsiMethod> methods;

    private String simpleName;

    private String pkg;

    private String path;

    /** 打印输出数据 */
    private String data;

    /**
     * 获取测试类名
     *
     * @return
     */
    @Override
    public String getTestName() {
        if (simpleName != null) {
            return simpleName.trim() + "Test";
        }
        return null;
    }

    /**
     * 获取测试类路径
     *
     * @return
     */
    @Override
    public String getTestPath() {
        if (path == null) {
            return null;
        }
        String testPath = path;
        if (path.contains("main")) {
            testPath = path.replace("main", "test");
        } else {
            testPath = path.replace("src", "test");
        }
        return testPath.substring(0, testPath.length() - 5) + "Test.java";
    }

    /**
     * 打印到控制台
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("类名为：").append(simpleName).append("\n")
                .append("全名为：").append(getClassName()).append("\n")
                .append("路径：").append(path).append("\n");
        ;
        if (methods != null && methods.size() > 0) {
            sb.append("方法：");
            for (PsiMethod method : methods) {
                sb.append(method.getName()).append(",\n");
                PsiModifierList list = method.getModifierList();
                sb.append("----:" + list.toString()).append("\n");

            }
        }
        return sb.toString();
    }

    @Override
    public boolean isTestClassExists() {
        String testPath = this.getTestPath();
        return new File(testPath).exists();
    }

    @Override
    public void writeToNewFile() throws IOException {
        ClassInfoIoUtil.writeTestFile(this);
    }

    public String getData() {
        if (data == null || "".equals(data.trim())){
            return ClassInfoIoUtil.readClassInfoData(this);
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<PsiMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<PsiMethod> methods) {
        this.methods = methods;
    }

    public String getClassName() {
        return pkg + "." + simpleName;
    }


    @Override
    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
}
