package com.zhz.idea.plugin.plus.domain;

import java.io.IOException;

public interface IPrintText {

    /** 写出data */
    String toData();

    /**
     * 写入类文件
     */
    /**
     *
     * @throws IOException
     */
    void writeToNewFile() throws IOException;


    /**
     * 重新优化方法，为重载的方法(方法名重复)重新起名
     * 保证打印的方法名不能重复
     */
    void renderMethods();

}
