package com.zhz.idea.plugin.plus.facade.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import com.zhz.idea.plugin.plus.domain.exception.IppException;
import com.zhz.idea.plugin.plus.facade.TestCreatorFacade;
import com.zhz.idea.plugin.plus.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
      return  target.stream().filter(method -> isMethodInList(method,existsList)).collect(Collectors.toList());
    }

    /** 如果方法在list中则过滤 */
    private boolean isMethodInList(PsiMethod method,List<PsiMethod> methodList){
        for (PsiMethod psiMethod : methodList) {
            if (psiMethod.getName().equals(method.getName())){
                return true;
            }
        }
        return false;
    }

}
