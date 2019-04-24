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

}
