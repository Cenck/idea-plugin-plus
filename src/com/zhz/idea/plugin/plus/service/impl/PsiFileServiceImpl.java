package com.zhz.idea.plugin.plus.service.impl;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.PsiShortNamesCache;
import com.zhz.idea.plugin.plus.service.PsiFileService;
import org.apache.http.util.TextUtils;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-28 19:43
 */
public class PsiFileServiceImpl implements PsiFileService {

    /**
     * <a href='https://my.oschina.net/u/2526698/blog/1548929></a>
     *
     * @param e
     * @param psiFilePath
     */
    @Override
    public void gotoPsiFile(Project project, String psiFilePath) {
        if (TextUtils.isBlank(psiFilePath)) {
            return;
        }
        //查找名称为mapperName的文件
        PsiFile[] files = PsiShortNamesCache.getInstance(project).getFilesByName(psiFilePath);
        if (files.length >= 1) {
            PsiFile file = files[0];
            OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(project, file.getVirtualFile());
            Editor editor = FileEditorManager.getInstance(project).openTextEditor(openFileDescriptor, true);
            // 跳转到对应 文件 的第3行
            CaretModel caretModel = editor.getCaretModel();
            LogicalPosition logicalPosition = caretModel.getLogicalPosition();
            logicalPosition.leanForward(true);
            LogicalPosition logical = new LogicalPosition(3, logicalPosition.column);
            caretModel.moveToLogicalPosition(logical);
            SelectionModel selectionModel = editor.getSelectionModel();
            selectionModel.selectLineAtCaret();
        }
    }
}
