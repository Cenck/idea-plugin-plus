package com.zhz.idea.plugin.plus.util;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiType;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 17:17
 */
public class ClassInfoIoUtil {

    private ClassInfoIoUtil() {
    }

    /**
     * 将类信息数据读出 到string
     *
     * @param classAgg
     * @return
     */
    @Deprecated
    public static String readClassInfoData(ClassAgg classAgg) {
        StringBuilder sb = new StringBuilder();
        sb.append("package  ").append(classAgg.getPkg()).append(";\n");
        {
            sb.append("import org.junit.Test;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        }
        sb.append("public class " + classAgg.getTestName() + "  { \n\n");
        {
            sb.append("    @Autowired\n");
            sb.append("    private " + classAgg.getSimpleName() + " service; \n\n");
        }
        for (PsiMethod method : classAgg.getMethods()) {
            readMethodInfo(method, sb);
        }
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public static void readMethodInfo(PsiMethod method, StringBuilder sb) {
        sb.append("    @Test\n");
        sb.append("    public void " + method.getName() + "(){\n");
        Set<String> newImportSet = new HashSet<>();
        {
            PsiParameterList parameterList = method.getParameterList();
            if (!parameterList.isEmpty()) {
                for (int i = 0; i < parameterList.getParameters().length; i++) {
                    PsiParameter parameter = parameterList.getParameters()[i];
                    PsiType type = parameter.getType();
                    addImport(type.getCanonicalText(), newImportSet);
                    String pname = paramname(parameter.getType().getPresentableText()) + i;
                    sb.append("        ").append(parameter.getType().getPresentableText()).append(" ").append(pname).append(" = null; \n");
                }
            }
            PsiType returnType = method.getReturnType();
            addImport(returnType.getCanonicalText(), newImportSet);
            // 生成service对应的该方法调用
            sb.append("        ").append(returnType.getPresentableText()).append(" result = ").append(" service.").append(method.getName()).append("(");
            if (!parameterList.isEmpty()) {
                for (int i = 0; i < parameterList.getParameters().length; i++) {
                    PsiParameter parameter = parameterList.getParameters()[i];
                    String pname = paramname(parameter.getType().getPresentableText()) + i;
                    sb.append("       ").append(pname).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            //方法调用结束
            sb.append(");\n\n");
        }
        sb.append("\n    }\n");
        {
            // 添加新的需要的导入
            int index = sb.indexOf(";") + 1;
            newImportSet.forEach(s -> {
                sb.insert(index, "\nimport " + s + ";");
            });
        }

    }

    private static void addImport(String canonicalText, Set<String> newImportSet) {
        if (canonicalText.contains("<")) {

        } else {
            newImportSet.add(canonicalText);
        }
    }

    private static String paramname(String presentableText) {
        if (presentableText.contains("<")) {
            return presentableText.substring(0, presentableText.indexOf("<"));
        }
        return presentableText;
    }

    /**
     * 将类写入到文件
     *
     * @param filepath
     * @param data
     * @throws IOException
     */
    public static void writeTestFile(String filepath, String data) throws IOException {
        File file = new File(filepath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(fos);
        BufferedWriter bf = new BufferedWriter(pw);
        bf.write(data);
        bf.flush();
        fos.close();
        pw.close();
        bf.close();
    }

    /**
     * 从file绝对路径读出string
     *
     * @param filepath
     */
    public static String readDataFromAbPath(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IOException("待读出文件不存在");
        }
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null && s.length() != 0) {
            sb.append(s).append("\n");
        }
        fis.close();
        isr.close();
        br.close();
        return sb.toString();
    }


}
