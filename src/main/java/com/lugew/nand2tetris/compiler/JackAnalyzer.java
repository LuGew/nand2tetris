package com.lugew.nand2tetris.compiler;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * jack 分析器
 *
 * @author LuGew
 * @since 2019/10/15
 */
public class JackAnalyzer {

    public static void main(String[] args) throws IOException {
        String fileName = "";
        JackTokenizer jackTokenizer = new JackTokenizer(fileName);
        jackTokenizer.writeStart();
        jackTokenizer.writeCurrentTokenToFile();
        jackTokenizer.writeEnd();
    }
}
