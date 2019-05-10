package com.zhz.idea.plugin.plus.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.zhz.idea.plugin.plus.facade.HelloWorldFacade;
import org.apache.http.util.TextUtils;
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
        Project project = e.getData(PlatformDataKeys.PROJECT);
        StringBuilder sb = new StringBuilder();
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if (psiFile!=null){
            VirtualFile virtualFile = psiFile.getVirtualFile();
//            sb.append("virtualFile: ").append(virtualFile.getCanonicalPath()).append("\n");
        }
        PsiElement psiEle = e.getData(LangDataKeys.PSI_ELEMENT);
        if (psiEle!=null){
//            sb.append("当前在编辑元素: ").append(psiEle.getText()).append("\n");
//            if (psiEle.getReference()!=null){
//                sb.append("当前在编辑元素: ").append(psiEle.getReference().getCanonicalText()).append("\n");
//            }
        }

        Editor hostEditor = e.getData(LangDataKeys.HOST_EDITOR);
        if (hostEditor != null) {
            SelectionModel model = hostEditor.getSelectionModel();
            sb.append("host选中文本：类名：").append(hostEditor.getClass().getName()).append("\n");
            final String selectedText = model.getSelectedText();
            if (!TextUtils.isBlank(selectedText)){
                sb.append("host选中文本：").append(selectedText).append("\n");
            }
        }

        Object selectObj = e.getData(PlatformDataKeys.SELECTED_ITEM);
        if(selectObj!=null){
            sb.append("选中对象： ").append(selectObj.getClass().getName()).append("\n");
        }

        FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (fileEditor!=null){
            sb.append("FileEditor: ").append(fileEditor.getName()).append("\n");
        }


        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor != null) {
            SelectionModel model = editor.getSelectionModel();
            final String selectedText = model.getSelectedText();
            if (!TextUtils.isBlank(selectedText)){
                sb.append("选中文本：").append(selectedText).append("\n");
            }
        }


        String debug = sb.toString();
        Messages.showMessageDialog(project,"Hello,Ipp!", "Warning", Messages.getInformationIcon());


    }


}
