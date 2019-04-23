package com.zhz.idea.plugin.plus.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import com.zhz.idea.plugin.plus.facade.TestCreatorFacade;
import com.zhz.idea.plugin.plus.facade.impl.TestCreatorFacadeImpl;
import com.zhz.idea.plugin.plus.util.ClassInfoIoUtil;

import java.io.IOException;
import java.util.List;

public class TestClassCreatorAction extends AbstractAction{

    private TestCreatorFacade facade = new TestCreatorFacadeImpl();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        ClassAgg classAgg = facade.analysisClassInfo(e);
        try {
            if (classAgg.isTestClassExists()){
                String classQualifiedName = classAgg.getSimpleName()+"Test";
                PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classQualifiedName, GlobalSearchScope.projectScope(project));
                if (psiClass instanceof PsiJavaFileImpl){
                   ClassAgg oldTestClass = facade.classInfoFromPsiFile((PsiJavaFileImpl) psiClass);
                    List<PsiMethod> list = oldTestClass.getMethods();
                    // 过滤掉已存在的方法
                    list = facade.filterExistsTestMethod(list,classAgg.getMethods());
                    StringBuilder sb =new StringBuilder();
                    for (PsiMethod method : list) {
                        ClassInfoIoUtil.readMethodInfo(method,sb);
                    }
                    String newInsertMethodString = sb.toString();
                    // 在代码的最后一个}前插入@Test方法
                    String data = oldTestClass.getData();
                    // 把类读出string 从最后一个}处删除 （后面会再加上）
                    data = data.substring(0,data.lastIndexOf("}"));
                    data = data + newInsertMethodString + "\n}";
                    oldTestClass.setData(data);
                    classAgg.writeToNewFile();

                }
            }else {
                classAgg.writeToNewFile();
            }
            Messages.showMessageDialog(project, classAgg.getClassName(), "Test Class Write Success1", Messages.getInformationIcon());
        } catch (IOException e1) {
            e1.printStackTrace();
            Messages.showMessageDialog(project, e1.getMessage(), "Error", Messages.getInformationIcon());
        }


    }
}
