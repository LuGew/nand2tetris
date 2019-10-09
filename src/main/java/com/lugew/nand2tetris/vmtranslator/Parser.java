package com.lugew.nand2tetris.vmtranslator;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static final String EMPTY = "";
    public static final String END = "END";
    private static final String NULL = "NULL";
    public static final String CONSTANT = "constant";
    public static final String LOCAL = "local";
    public static final String ARGUMENT = "argument";
    public static final String THIS = "this";
    public static final String THAT = "that";
    public static final String POINTER = "pointer";
    public static final String TEMP = "temp";
    public static final String STATIC = "static";

    public static final String ADD = "add";
    public static final String SUB = "sub";
    public static final String NEG = "neg";
    public static final String EQ = "eq";
    public static final String GT = "gt";
    public static final String LT = "lt";
    public static final String AND = "and";
    public static final String OR = "or";
    public static final String NOT = "not";
    public static final String PUSH = "push";
    public static final String POP = "pop";
    public static final int C_ARITHMETIC = 1;
    public static final int C_PUSH = 2;
    public static final int C_POP = 3;
    public static final int C_LABEL = 4;
    public static final int C_GOTO = 5;
    public static final int C_IF = 6;
    public static final int C_FUNCTION = 7;
    public static final int C_RETURN = 8;
    public static final int C_CALL = 9;
    public static final int C_OTHER = 0;

    private Scanner scanner;
    private String currentCommand;

    public Parser(String fileName) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(fileName));
        currentCommand = EMPTY;
    }

    public boolean hasMoreCommands() {
        advance();
        return !EMPTY.equals(currentCommand);
    }

    private void advance() {
        while (scanner.hasNextLine()) {
            currentCommand = scanner.nextLine();
            if (!isComment() && !isSpace()) {
                return;
            }
        }
        currentCommand = EMPTY;
    }

    private boolean isComment() {
        return match(currentCommand, "\\/\\/[^\\n]*");
    }

    private boolean isSpace() {
        return match(currentCommand, "\\s*");
    }

    public int commandType() {
        switch (currentCommand.split("\\s")[0]) {
            case ADD:
            case SUB:
            case NEG:
            case EQ:
            case GT:
            case LT:
            case AND:
            case OR:
            case NOT:
                return C_ARITHMETIC;
            case PUSH:
                return C_PUSH;
            case POP:
                return C_POP;
            default:
                return C_OTHER;
        }

    }

    /**
     * 返回当前命令的第一个参数
     *
     * @return 字符串
     */
    public String arg1() {
        switch (commandType()) {
            case C_ARITHMETIC:
                return currentCommand;
            case C_POP:
            case C_PUSH:
                return currentCommand.split("\\s+")[1];
            default:
                return null;
        }
    }

    /**
     * 返回当前命令的第二个参数
     * 仅当命令是push pop function call时才可以调用
     *
     * @return int
     */
    public int arg2() {
        return Integer.parseInt(currentCommand.split("\\s+")[2]);
    }


    private boolean match(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
