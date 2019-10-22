package com.lugew.nand2tetris.compiler;

import java.io.File;
import java.io.IOException;

/**
 * jack 分析器
 *
 * @author LuGew
 * @since 2019/10/15
 */
public class JackAnalyzer {

    public static void main(String[] args) throws IOException {
        String fileName = "Main.jack";
        String path = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\10\\ArrayTest\\";
        String realPath = path + fileName;
        realPath = DeleteComments.clearComment(new File(realPath));
        JackTokenizer jackTokenizer = new JackTokenizer(realPath);
        jackTokenizer.writeStart();
        jackTokenizer.writeCurrentTokenToFile();
        jackTokenizer.writeEnd();
        jackTokenizer.closeWriter();
    }
}
