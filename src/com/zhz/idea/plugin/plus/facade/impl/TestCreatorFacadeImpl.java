package com.zhz.idea.plugin.plus.facade.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import com.zhz.idea.plugin.plus.domain.dto.PsiMethodOutDto;
import com.zhz.idea.plugin.plus.domain.exception.IppException;
import com.zhz.idea.plugin.plus.domain.exception.SuccessException;
import com.zhz.idea.plugin.plus.domain.vo.MethodPrintVo;
import com.zhz.idea.plugin.plus.domain.vo.VariableVo;
import com.zhz.idea.plugin.plus.facade.TestCreatorFacade;
import com.zhz.idea.plugin.plus.util.Assert;
import com.zhz.idea.plugin.plus.util.PrintIOUtil;
import com.zhz.idea.plugin.plus.util.PrintTextUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 16:10
 */
public class TestCreatorFacadeImpl implements TestCreatorFacade {

    @Override
    public ClassAgg analysisClassInfo(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Assert.notNull(psiFile, "当前未选中文档");
        if (psiFile instanceof PsiJavaFileImpl) {
            return this.classInfoFromPsiFile((PsiJavaFileImpl) psiFile);
        } else {
            throw new IppException("本操作仅支持java类型文档");
        }
    }

    @Override
    public ClassAgg classInfoFromPsiFile(PsiJavaFileImpl javaFile) {
        ClassAgg classAgg = new ClassAgg();
        String simplename = javaFile.getName().trim().substring(0, javaFile.getName().length() - 5);
        PsiClass[] psiArr = javaFile.getClasses();
        List<PsiMethod> methods = new ArrayList<>();

        if (psiArr.length > 0) {
            for (PsiClass psiClass : psiArr) {
                PsiMethod[] methodArr = psiClass.getMethods();
                for (PsiMethod psiMethod : methodArr) {
                    if (isMethodVisible(psiMethod)) {
                        methods.add(psiMethod);
                    }
                }
            }
        }
        String path = javaFile.getVirtualFile().getCanonicalPath();
        classAgg.setSimpleName(simplename);
        classAgg.setMethods(methods);
        classAgg.setPath(path);
        classAgg.setPkg(javaFile.getPackageName());
        return classAgg;
    }

    @Override
    public boolean isMethodVisible(PsiMethod method) {
        if (method.isConstructor()) {
            // 构造函数不展示
            return false;
        } else if (!method.getModifierList().hasModifierProperty("public")) {
            // 非公方法不展示
            return false;
        }
        return true;
    }

    @Override
    public List<PsiMethod> filterExistsTestMethod(List<PsiMethod> target, List<PsiMethod> existsList) {
        // 过滤 只需要方法可见，并且不在旧test文件中
        return target.stream().filter(method -> (isMethodVisible(method) && !isMethodInList(method, existsList))).collect(Collectors.toList());
    }

    @Override
    public void appendTestMethodText(PsiClass psiTestClass, ClassAgg classAgg) throws IOException {
        if (psiTestClass instanceof PsiClassImpl) {
            PsiClassImpl psiTest = (PsiClassImpl) psiTestClass;
            // 测试类已生成的方法集
            List<PsiMethod> existsList = Arrays.asList(psiTest.getMethods());
            // 选中类方法集合
            List<PsiMethod> list = classAgg.getMethods();
            // 过滤掉已存在的方法
            list = this.filterExistsTestMethod(list, existsList);
            if (list == null || list.size() == 0) {
                throw new SuccessException("无更新");
            }
            // 读取新方法
            PsiMethodOutDto dto = PrintTextUtil.appendMethodsToPrintAgg(list);
            StringBuilder newMethodListSb = new StringBuilder("    /*===============插入新方法开始================*/\n");
            for (PsiMethod method : list) {
                newMethodListSb.append("    //  ").append(method.getName()).append("\n");
            }

            // 在代码的最后一个}前插入新添加的Test方法
            String data = PrintIOUtil.readDataFromAbPath(classAgg.getTestPath());
            String newImportString = this.getNewImportString(dto.getNewImportList());
            if (newImportString.trim().length() > 0) {
                data = data.replaceFirst("import", newImportString + "\nimport ");
            }

            // 把类读出string 从最后一个}处删除 （后面会再加上）
            data = data.substring(0, data.lastIndexOf("}"));
            String newInsertMethodString = this.getNewTestMethodsPrintString(newMethodListSb, dto.getAppendMethodList(), classAgg.toPrintModel().getDefaultService());
            // 添加并补上}
            if (newInsertMethodString.trim().length() > 0) {
                data = data + newInsertMethodString + "\n}";
            }
            PrintIOUtil.writeTestFile(classAgg.getTestPath(), data);
        }
    }

    private String getNewImportString(Set<String> importPkgs) {
        StringBuilder sb = new StringBuilder();
        if (importPkgs != null) {
            importPkgs.forEach(s -> sb.append("import ").append(s).append(";\n"));
        }
        return sb.toString();
    }


    /**
     * 将需要新导入的方法 读成string
     *
     * @param methodList
     * @param defaultService
     * @return
     */
    private String getNewTestMethodsPrintString(StringBuilder sb, List<MethodPrintVo> methodList, VariableVo defaultService) {
        if (methodList != null && methodList.size() > 0) {
            methodList.forEach(m -> PrintTextUtil.appendMethod(m, sb, defaultService));
        }
        return sb.toString();
    }

    /**
     * 如果方法在旧Test文件中则过滤
     * 比较方法相同时，方法名和参数列表相同
     */
    private boolean isMethodInList(PsiMethod method, List<PsiMethod> methodList) {
        for (PsiMethod psiMethod : methodList) {
            if (psiMethod.getName().equals(method.getName())) {
                // 方法名相同，再比较参数列表
                return true;
                /*
                测试方法没有参数列表，所有没有办法比较参数列表：也就是说，无法为重载的方法 新增测试方法
                 */
//                if (this.paramListToString(psiMethod).equals(this.paramListToString(method))) {
//                    return true;
//                }
            }
        }
        return false;
    }

    private String paramListToString(PsiMethod psiMethod) {
        StringBuilder sb = new StringBuilder();
        for (Object parameter : psiMethod.getParameterList().getParameters()) {
            sb.append(parameter.getClass().getName());
        }
        return sb.toString();
    }

}
