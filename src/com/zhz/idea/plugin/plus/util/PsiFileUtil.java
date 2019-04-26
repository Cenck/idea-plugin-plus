package com.zhz.idea.plugin.plus.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * <a href='https://blog.csdn.net/ExcellentYuXiao/article/details/80273448'>IntelliJ IDEA插件开发指南(三)</a>
 *
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-19 15:05
 */
@Deprecated
public class PsiFileUtil {

    private PsiFileUtil() {
    }

    public static Object getObj(AnActionEvent e) {
        // 获取当前编辑的文件, 通过PsiFile可获得PsiClass, PsiField等对象
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        System.out.println();
        // 获取当前的project对象
        Project project = e.getProject();

        // 获取数据上下文
        DataContext dataContext = e.getDataContext();

        String classQualifiedName = "";
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classQualifiedName, GlobalSearchScope.projectScope(project));
        StringBuilder sb = new StringBuilder();
        for (PsiMethod allMethod : psiClass.getAllMethods()) {
            if(allMethod.isConstructor()){
                //不接收构造函数
                continue;
            }
            sb.append(allMethod.getName()).append("\n");
        }
        return sb.toString();
    }


}
