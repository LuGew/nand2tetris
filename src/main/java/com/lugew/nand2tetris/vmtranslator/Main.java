package com.lugew.nand2tetris.vmtranslator;


import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        String fileName = "StackTest";
//        String fileName = "BasicTest";
//        String fileName = "PointerTest";
        String fileName = "StaticTest";
        Parser parser = getParser(fileName);
        CodeWriter codeWriter = getFileCodeWriter(fileName);
        codeWriter.setFileName(fileName);
        while (parser.hasMoreCommands()) {
            switch (parser.commandType()) {
                case Parser.C_ARITHMETIC:
                    codeWriter.writeArithmetic(parser.getCurrentCommand());
                    break;
                case Parser.C_POP:
                    codeWriter.writePushPop(Parser.C_POP, parser.arg1(), parser.arg2());
                    break;
                case Parser.C_PUSH:
                    codeWriter.writePushPop(Parser.C_PUSH, parser.arg1(), parser.arg2());
                    break;
            }
        }
        codeWriter.close();
    }

    private static Parser getParser(String fileName) throws FileNotFoundException {
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\BasicTest\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\PointerTest\\" + fileName + ".vm";
        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\" + fileName + ".vm";
        return new Parser(pathName);
    }

    private static CodeWriter getFileCodeWriter(String fileName) throws IOException {
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\StackArithmetic\\StackTest\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\BasicTest\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\PointerTest\\" + fileName + ".asm";
        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\" + fileName + ".asm";
        return new CodeWriter(pathName);
    }
}
