package com.zhz.idea.plugin.plus.util;

import com.intellij.psi.PsiMethod;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;

import java.io.*;

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
     * @param classAgg
     * @return
     */
    public static String readClassInfoData(ClassAgg classAgg) {
        StringBuilder sb = new StringBuilder();
        sb.append("package  ").append(classAgg.getPkg()).append(";\n");
        {
            sb.append("import org.junit.Test;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        }
        sb.append("public class " + classAgg.getTestName() + "  { \n\n");
        {
            sb.append("     @Autowired\n");
            sb.append("     private "+classAgg.getSimpleName()+" service; \n");
        }
        for (PsiMethod method : classAgg.getMethods()) {
           readMethodInfo(method,sb);
        }
        sb.append("}");
        return sb.toString();
    }

    public static void readMethodInfo(PsiMethod method,StringBuilder sb){
        sb.append("     @Test\n");
        sb.append("     public void "+method.getName()+"(){\n");

        sb.append("\n   }\n");
    }

    /**
     * 将类写入到文件
     * @param classAgg
     * @throws IOException
     */
    public static void writeTestFile(ClassAgg classAgg) throws IOException {
        File file  = new File(classAgg.getTestPath());
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(fos);
        BufferedWriter bf = new BufferedWriter(pw);
        bf.write(classAgg.getData());
        bf.flush();
        fos.close();
        pw.close();
        bf.close();
    }

}
