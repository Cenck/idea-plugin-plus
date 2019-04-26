package com.zhz.idea.plugin.plus.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import com.zhz.idea.plugin.plus.facade.TestCreatorFacade;
import com.zhz.idea.plugin.plus.facade.impl.TestCreatorFacadeImpl;

import java.io.IOException;

public class TestClassCreatorAction extends AbstractAction {

    private TestCreatorFacade facade = new TestCreatorFacadeImpl();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        // 提取类信息
        ClassAgg classAgg = facade.analysisClassInfo(e);
        try {
            if (classAgg.isTestClassExists()) {
                // 已存在测试类，向原文件添加 新方法
                String classQualifiedName = classAgg.getPkg() + "." + classAgg.getTestName();
                PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classQualifiedName, GlobalSearchScope.projectScope(project));
                facade.appendTestMethodText(psiClass,classAgg);
                Messages.showMessageDialog(project, "Test method appended ", "Success", Messages.getInformationIcon());
            } else {
                // 全新生成一个测试类
                classAgg.toPrintModel().writeToNewFile();
                Messages.showMessageDialog(project, "new Test added", "Success", Messages.getInformationIcon());
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            Messages.showMessageDialog(project, ie.getMessage(), "IO Error", Messages.getInformationIcon());
        } catch (Exception ne) {
            ne.printStackTrace();
            Messages.showMessageDialog(project, ne.getMessage(), "Error", Messages.getInformationIcon());
        }


    }
}
