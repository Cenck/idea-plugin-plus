package com.zhz.idea.plugin.plus.service;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-28 19:43
 */
public interface PsiFileService {


    /**
     * 跳动file中去
     * @param psiFile
     */
    void gotoPsiFile(Project project, String  psiFilePath);

}
