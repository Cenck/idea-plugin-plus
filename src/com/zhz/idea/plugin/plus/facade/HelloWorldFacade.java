package com.zhz.idea.plugin.plus.facade;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.apache.http.util.TextUtils;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 14:43
 */
public class HelloWorldFacade {



    public String getSelectedString(AnActionEvent e){
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        SelectionModel model = editor.getSelectionModel();
        final String selectedText = model.getSelectedText();
        if (TextUtils.isEmpty(selectedText)) {
            return selectedText;
        }
        return null;
    }


}
