package com.zhz.idea.plugin.plus.util;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiType;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import com.zhz.idea.plugin.plus.domain.aggregate.PrintTextAgg;
import com.zhz.idea.plugin.plus.domain.dto.PsiMethodOutDto;
import com.zhz.idea.plugin.plus.domain.vo.MethodPrintVo;
import com.zhz.idea.plugin.plus.domain.vo.VariableVo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-24 15:31
 */
public class PrintTextUtil {

    private PrintTextUtil() {
    }


    /**
     * 从类信息中转化出 打印对象实体
     * @param classInfo
     * @return
     */
    public static PrintTextAgg printTextFromClassInfo(ClassAgg classInfo) {
        PrintTextAgg agg = new PrintTextAgg();
        agg.setClassName(classInfo.getTestName());
        agg.setPkg(classInfo.getPkg());
        agg.setOutPath(classInfo.getTestPath());
        agg.setSuperClass(null);

        // 当前服务
        VariableVo ds = new VariableVo();
        ds.setType(classInfo.getSimpleName());
        ds.setName("service");
        agg.setDefaultService(ds);
        //import
        agg.getImportList().add("org.junit.Test");
        agg.getImportList().add("org.springframework.beans.factory.annotation.Autowired");
        //方法
        PsiMethodOutDto methodOutDto = appendMethodsToPrintAgg(classInfo.getMethods());
        if (methodOutDto.getAppendMethodList() != null) {
            agg.getMethods().addAll(methodOutDto.getAppendMethodList());
        }
        if (methodOutDto.getNewImportList() != null) {
            agg.getImportList().addAll(methodOutDto.getNewImportList());
        }
        return agg;
    }

    /**
     * 向打印实体中添加方法
     * @param methods
     * @return
     */
    public static PsiMethodOutDto appendMethodsToPrintAgg(List<PsiMethod> methods) {
        PsiMethodOutDto dto = new PsiMethodOutDto();
        if (methods != null) {
            Set<MethodPrintVo> voList = new HashSet<>(methods.size());
            Set<String> importSet = new HashSet<>(methods.size());
            methods.forEach(method -> {
                MethodPrintVo methodVo = new MethodPrintVo();
                methodVo.setMethodName(method.getName());
                VariableVo returnType = new VariableVo();
                returnType.setType(method.getReturnType().getPresentableText());
                methodVo.setReturnType(returnType);
                importSet.addAll(StringFormatUtil.getClassFromType(method.getReturnType().getCanonicalText()));
                // 是否静态方法
                methodVo.setStatic(method.getModifierList().hasModifierProperty("static"));

                PsiParameterList parameterList = method.getParameterList();
                if (!parameterList.isEmpty()) {
                    for (int i = 0; i < parameterList.getParameters().length; i++) {
                        PsiParameter parameter = parameterList.getParameters()[i];
                        PsiType type = parameter.getType();
                        VariableVo vo = new VariableVo();
                        vo.setType(type.getPresentableText());
                        vo.setName(parameter.getName());
                        methodVo.getParamList().add(vo);

                        List<String> clazzes = StringFormatUtil.getClassFromType(type.getCanonicalText());
                        importSet.addAll(clazzes);
                    }
                }
                voList.add(methodVo);
            });
            dto.setAppendMethodList(voList);
            dto.setNewImportList(importSet);
        }
        return dto;
    }


    public static String printToData(PrintTextAgg agg) {
        StringBuilder sb = new StringBuilder();
        // 包名
        sb.append("package  ").append(agg.getPkg()).append(";\n");
        //  import list
        if (agg.getImportList() != null && agg.getImportList().size() != 0) {
            for (String s : agg.getImportList()) {
                sb.append("import ").append(s).append(";\n");
            }
            sb.append("\n");
        }

        // 类名
        sb.append("public class ").append(agg.getClassName());
        // 继承
        if (agg.getSuperClass() != null && agg.getSuperClass().trim().length() != 0) {
            sb.append(" extend ").append(agg.getSuperClass());
        }
        sb.append("  { \n\n");

        // 全局变量
        // 生成该测试类的服务
        VariableVo ds = agg.getDefaultService();
        sb.append("    @Autowired\n");
        sb.append("    private ").append(ds.getType()).append(" ").append(ds.getName()).append("; \n\n");
        // 其他服务
        if (agg.getVars() != null && agg.getVars().size() != 0) {
            for (int i = 0; i < agg.getVars().size(); i++) {
                VariableVo var = agg.getVars().get(i);
                sb.append("    @Autowired\n");
                sb.append("    private ").append(var.getType()).append(" ").append(var.getName()).append("; \n\n");
            }
        }

        //方法
        if (agg.getMethods() != null && agg.getMethods().size() > 0) {
            for (MethodPrintVo m : agg.getMethods()) {
                appendMethod(m, sb, agg.getDefaultService());
            }
        }

        sb.append("}");
        return sb.toString();
    }


    public static void appendMethod(MethodPrintVo m, StringBuilder sb, VariableVo defaultService) {
        sb.append("    @Test\n");
        sb.append("    public void ").append(m.getTestMethodName()).append("(){ \n");
        // 参数生成
        if (m.getParamList() != null && m.getParamList().size() > 0) {
            for (int i = 0; i < m.getParamList().size(); i++) {
                VariableVo var = m.getParamList().get(i);
                String varname = var.getName();
                String defaultVal = getDefaultVal(var.getType());
                sb.append("        ").append(var.getType()).append(" ").append(varname)
                        .append(" = ").append(defaultVal).append(" ;\n");
            }

            // 生成对该方法的调用
            sb.append("        ");
            if (!"void".equals(m.getReturnType().getType())) {
                sb.append(m.getReturnType().getType()).append(" ").append(getResultName(m.getReturnType().getName())).append(" = ");
            }
            if (m.isStatic()) {
                sb.append(defaultService.getType());
            } else {
                sb.append(defaultService.getName());
            }
            sb.append(".").append(m.getMethodName()).append("(");
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < m.getParamList().size(); i++) {
                VariableVo var = m.getParamList().get(i);
                String varname = var.getName();
                params.append(varname).append(",");
            }
            params.deleteCharAt(params.length() - 1);
            sb.append(params).append(");\n\n");
        }

        sb.append("    }\n\n");

    }

    private static String getDefaultVal(String type) {
        switch (type.trim()) {
            case "int":
            case "short":
            case "byte":
                return "0";
            case "long":
                return "0L";
            case "char":
                return "'0'";
            case "boolean":
                return "false";
            case "float":
            case "double":
                return "0.0";
            default:
                return "null";
        }
    }

    /**
     * 返回方法调用结果的参数名
     *
     * @param type
     * @return
     */
    private static String getResultName(String type) {
        switch (type.trim()) {
            case "int":
            case "short":
            case "byte":
                return "num";
            case "long":
                return "l";
            case "char":
                return "c";
            case "boolean":
                return "bool";
            case "float":
            case "double":
                return "doub";
            default:
                return type;
        }
    }
}
