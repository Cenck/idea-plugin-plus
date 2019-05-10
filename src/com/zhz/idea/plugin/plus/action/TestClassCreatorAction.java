package com.zhz.idea.plugin.plus.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import com.zhz.idea.plugin.plus.domain.enums.ErrorCodeEnums;
import com.zhz.idea.plugin.plus.domain.exception.IppException;
import com.zhz.idea.plugin.plus.domain.exception.SuccessException;
import com.zhz.idea.plugin.plus.facade.TestCreatorFacade;
import com.zhz.idea.plugin.plus.facade.impl.TestCreatorFacadeImpl;
import com.zhz.idea.plugin.plus.service.PsiFileService;
import com.zhz.idea.plugin.plus.service.impl.PsiFileServiceImpl;
import org.apache.http.util.TextUtils;

import java.io.IOException;

public class TestClassCreatorAction extends AbstractAction {

    private TestCreatorFacade facade = new TestCreatorFacadeImpl();
    private PsiFileService psiFileService = new PsiFileServiceImpl();

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
                facade.appendTestMethodText(psiClass, classAgg);
                throw new SuccessException("更新成功");
            } else {
                // 全新生成一个测试类
                classAgg.toPrintModel().writeToNewFile();
                throw new SuccessException("新增成功");
            }

        } catch (IppException ippe) {
            ippe.printStackTrace();
            String title = ErrorCodeEnums.success.getCode().equals(ippe.getCode()) ? "Success" : "IPP Error";
            Messages.showMessageDialog(project, ippe.getMessage(), title, Messages.getInformationIcon());
        } catch (IOException ie) {
            ie.printStackTrace();
            Messages.showMessageDialog(project, ie.getMessage(), "IO Error", Messages.getInformationIcon());
        } catch (Exception ne) {
            ne.printStackTrace();
            Messages.showMessageDialog(project, ne.getMessage(), "Error", Messages.getInformationIcon());
        }

        if (classAgg != null && !TextUtils.isBlank(classAgg.getTestPath())) {
            psiFileService.gotoPsiFile(project, classAgg.getPkg() + "." + classAgg.getTestName());
        }


    }
}
