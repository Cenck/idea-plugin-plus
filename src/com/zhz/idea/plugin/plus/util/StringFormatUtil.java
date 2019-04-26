package com.zhz.idea.plugin.plus.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-24 16:24
 */
public class StringFormatUtil {

    private static final Pattern CLASS_PATTEN = Pattern.compile("[a-zA-Z0-9_.$]{1,}");

    /**
     * 获取变量名称  ，开头小写
     *
     * @param var
     */
    public static String getVarName(String var) {
        if (var == null || var.trim().length() == 0) {
            return var;
        }
        char[] arr = var.toCharArray();
        arr[0] = Character.toLowerCase(arr[0]);
        List<Character> characters = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (Character.isLetter(c) || Character.isDigit(c)) {
                characters.add(c);
            }
        }
        Character[] ret = characters.toArray(new Character[]{});
        StringBuilder sb = new StringBuilder();
        for (Character character : ret) {
            sb.append(character);
        }
        return sb.toString();
    }


    /**
     * 从类型中提取类，供包名后 import
     * java.util.List<java.lang.object> 可以提出java.util.List和 java.lang.object
     *
     * @param type
     * @return
     */
    public static List<String> getClassFromType(String type) {
        List<String> list = new ArrayList<>();
        Matcher matcher = CLASS_PATTEN.matcher(type);
        while (matcher.find()) {
            safeAddImport(matcher.group(), list);
        }
        return list;
    }

    private static void safeAddImport(String clazz, List<String> list) {
        if (clazz == null || list == null) {
            return;
        }
        clazz = clazz.trim();
        if (clazz.startsWith("java.lang.")) {
            //lang包不需要导入
            return;
        }
        if ("void".equals(clazz)
                || "boolean".equals(clazz)
                || "double".equals(clazz)
                || "long".equals(clazz)
                || "char".equals(clazz)
                || "byte".equals(clazz)
                || "shot".equals(clazz)
                || "float".equals(clazz)
                || "int".equals(clazz)) {
            // 返回空类型或基本类型不需要导入
            return;
        }
        list.add(clazz);
    }


}
