package com.lugew.nand2tetris.assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "Add";
        Parser parser = getParser(fileName);
        PrintWriter printWriter = getFilePrintWriter(fileName);
        while (parser.hasMoreCommands()) {
            switch (parser.commandType()) {
                case Parser.A_COMMAND:
                    printWriter.print(Integer.valueOf(parser.symbol()));
                    break;
                case Parser.L_COMMAND:
                    break;
                case Parser.C_COMMAND:
                    printWriter.print(Integer.valueOf(Code.get16bitsCode(parser.comp(), parser.dest(), parser.jump()), 2));
                    break;
            }
        }
        printWriter.flush();
        printWriter.close();
    }

    private static Parser getParser(String fileName) throws FileNotFoundException {
        String pathName = "d://test/" + fileName + ".asm";
        return new Parser(pathName);
    }

    private static PrintWriter getFilePrintWriter(String fileName) throws FileNotFoundException {
        String pathName = "d://test/" + fileName + ".hack";
        return new PrintWriter(new File(pathName));
    }
}
