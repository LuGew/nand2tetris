package com.lugew.nand2tetris.vmtranslator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        String fileName = "StackTest";
//        String fileName = "BasicTest";
//        String fileName = "PointerTest";
//        String fileName = "StaticTest";
//        String fileName = "BasicLoop";
//        String fileName = "FibonacciSeries";
//        String fileName = "SimpleFunction";
        String fileName = "FibonacciElement";
        List<Parser> parsers = getParsers();
        CodeWriter codeWriter = getFileCodeWriter(fileName);
        codeWriter.writeInit();
        for (Parser parser : parsers) {
            codeWriter.setFileName(parser.getFileName());
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
                    case Parser.C_LABEL:
                        codeWriter.writeLabel(parser.arg1());
                        break;
                    case Parser.C_GOTO:
                        codeWriter.writeGoto(parser.arg1());
                        break;
                    case Parser.C_IF:
                        codeWriter.writeIf(parser.arg1());
                        break;
                    case Parser.C_FUNCTION:
                        codeWriter.writeFunction(parser.arg1(), parser.arg2());
                        break;
                    case Parser.C_RETURN:
                        codeWriter.writeReturn();
                        break;
                    case Parser.C_CALL:
                        codeWriter.writeCall(parser.arg1(), parser.arg2());
                        break;
                }
            }
        }
        codeWriter.close();
    }

    private static Parser getParser(String fileName) throws FileNotFoundException {
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\BasicTest\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\PointerTest\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\BasicLoop\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\FibonacciSeries\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\FunctionCalls\\SimpleFunction\\" + fileName + ".vm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\FunctionCalls\\FibonacciElement\\" + fileName + ".vm";
        return null;
    }

    private static List<Parser> getParsers() throws FileNotFoundException {
        List<Parser> parsers = new ArrayList<>();

        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\FunctionCalls\\FibonacciElement\\";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\FunctionCalls\\SimpleFunction\\";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\BasicLoop\\";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\FibonacciSeries\\";

        File file = new File(pathName);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File tempFile = files[i];
            String fileName = tempFile.getName();
            System.out.println(fileName);
            if (fileName.endsWith(".vm")) {
                parsers.add(new Parser(pathName + fileName, fileName.replace(".vm", "")));
            }
        }
        return parsers;
    }

    private static CodeWriter getFileCodeWriter(String fileName) throws IOException {
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\StackArithmetic\\StackTest\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\BasicTest\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\07\\MemoryAccess\\PointerTest\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\FunctionCalls\\SimpleFunction\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\BasicLoop\\" + fileName + ".asm";
//        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\ProgramFlow\\FibonacciSeries\\" + fileName + ".asm";
        String pathName = "D:\\LuGew\\Study\\Nand2Tetris\\nand2tetris\\projects\\08\\FunctionCalls\\FibonacciElement\\" + fileName + ".asm";
        return new CodeWriter(pathName);
    }
}
