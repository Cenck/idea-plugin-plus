package com.zhz.idea.plugin.plus.facade;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 16:05
 */
public interface TestCreatorFacade {


    /**
     * 解析出必要类信息
     *
     * @param e
     * @return
     */
    ClassAgg analysisClassInfo(AnActionEvent e);


    /**
     * psiJavaFile解析
     *
     * @param javaFile
     * @return
     */
    ClassAgg classInfoFromPsiFile(PsiJavaFileImpl javaFile);

    /**
     * 字段是否可见
     *
     * @param psiMethod
     * @return
     */
    boolean isMethodVisible(PsiMethod psiMethod);

    /**
     * 过滤方法
     *
     * @param target
     * @param existsList
     */
    List<PsiMethod> filterExistsTestMethod(List<PsiMethod> target, List<PsiMethod> existsList);

    /**
     * 向已存在的test文件中添加方法
     *
     * @param psiTestClass
     * @param source   原来已生成的测试类
     */
    void appendTestMethodText(PsiClass psiTestClass, ClassAgg source) throws IOException;


}
