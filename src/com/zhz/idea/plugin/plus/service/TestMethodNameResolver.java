package com.zhz.idea.plugin.plus.service;

import java.util.Set;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-30 10:27
 */
public class TestMethodNameResolver {
    private TestMethodNameResolver() {
    }

    ;
    private static TestMethodNameResolver instance = new TestMethodNameResolver();

    public static TestMethodNameResolver getInstance() {
        return instance;
    }


    /**
     * 获取测试方法名
     *
     * @param prime
     * @param set
     * @return
     */
    public String getTestName(String prime, Set<String> set) {
        for (String s : set) {
            if (s.equals(prime)) {
                if (prime.contains("_")) {
                    String index = getDuplicateMethodNameIndex(prime);
                    if (index != "-1") {
                        prime = prime.substring(0, prime.lastIndexOf("_") + 1) + index;
                        this.getTestName(prime, set);
                    } else {
                        prime = prime + "_01";
                    }
                } else {
                    prime = prime + "_01";
                }
            }
        }
        set.add(prime);
        return prime;
    }

    /**
     * 重复方法名下标
     *
     * @return
     */
    private String getDuplicateMethodNameIndex(String name) {
        if (!name.contains("_")) {
            return "-1";
        }
        String lastTwo = name.substring(name.lastIndexOf("_") + 1);
        try {
            int ret = Integer.parseInt(lastTwo);
            String s = String.valueOf(++ret);
            if (s.length() == 1) {
                s = "0" + s;
            }
            return s;
        } catch (NumberFormatException e) {
            return "-1";
        }
    }


}
