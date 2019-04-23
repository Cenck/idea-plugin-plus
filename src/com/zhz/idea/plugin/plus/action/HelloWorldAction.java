package com.zhz.idea.plugin.plus.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zhz.idea.plugin.plus.facade.HelloWorldFacade;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-19 11:23
 */
public class HelloWorldAction extends AbstractAction {

    private HelloWorldFacade helloWorldFacade = new HelloWorldFacade();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println(111);
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        VirtualFile virtualFile = psiFile.getVirtualFile();

        StringBuilder sb = new StringBuilder();
        if (psiFile instanceof PsiJavaFileImpl) {
            PsiJavaFileImpl javaFile = (PsiJavaFileImpl) psiFile;
            sb.append("javafile name：").append(javaFile.getName()).append("\n")
                    .append("javafile pkgName：").append(javaFile.getPackageName()).append("\n");
            for (PsiClass aClass : javaFile.getClasses()) {
                sb.append("java-psi:").append(aClass.getName()).append("\n");
            }
        }
        sb.append("psi class name：").append(psiFile.getClass().getName()).append("\n")
                .append("vf class name：").append(virtualFile.getClass().getName()).append("\n")
                .append("vf path：").append(virtualFile.getPath()).append("\n")
                .append("vf getExtension：").append(virtualFile.getExtension()).append("\n")
                .append("vf getCanonicalPath：").append(virtualFile.getCanonicalPath()).append("\n")
                .append("vf name：").append(virtualFile.getName()).append("\n");
        Messages.showMessageDialog(project, sb.toString(), "Warning", Messages.getInformationIcon());

        /*
        String txt = Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
        String s = anActionEvent.getPresentation().getText();
        Messages.showMessageDialog(project, s, "Information", Messages.getInformationIcon());

         */

    }


}
