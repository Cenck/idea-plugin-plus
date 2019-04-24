package com.zhz.idea.plugin.plus.domain;

import com.zhz.idea.plugin.plus.domain.aggregate.PrintTextAgg;

import java.io.IOException;
import java.io.Serializable;

/**
 * 当前编辑的类信息
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-22 19:23
 */
public interface IClassInfo extends Serializable {

    /**
     * 获取类名
     * @return
     */
    String getSimpleName();
    
    /** 获取包名 */
    String getPkg();
    
    /** 获取类路径 */
    String getPath();

    /** 测试类名 */
    String getTestName();

    /**
     * 测试类输出路径
     * @return
     */
    String getTestPath();

    /**
     * 测试类是否存在
     * @return
     */
    boolean isTestClassExists();

    /** 转化成打印实体 */
    PrintTextAgg toPrintModel();


}
