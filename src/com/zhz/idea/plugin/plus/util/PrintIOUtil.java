package com.zhz.idea.plugin.plus.util;

import java.io.*;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 17:17
 */
public class PrintIOUtil {

    private PrintIOUtil() {
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
