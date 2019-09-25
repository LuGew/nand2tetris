package com.lugew.nand2tetris.assembler;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static final String EMPTY = "";
    public static final String END = "END";
    private static final String NULL = "NULL";

    private static final char A_COMMAND_FLAG = '@';

    private static final char L_COMMAND_FLAG_START = '(';
    private static final char L_COMMAND_FLAG_END = ')';
    public static final int A_COMMAND = 1;
    public static final int C_COMMAND = 2;
    public static final int L_COMMAND = 3;
    private static final int ERROR_COMMAND = -1;

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
            if (!isComment()) {
                return;
            }
        }
        currentCommand = EMPTY;
    }

    private boolean isComment() {
        return match(currentCommand, "\\/\\/[^\\n]*");
    }

    public int commandType() {
        if (isACommand()) {
            return A_COMMAND;
        } else if (isLCommand()) {
            return L_COMMAND;
        } else {
            return C_COMMAND;
        }
    }

    private boolean isACommand() {
        char flag = currentCommand.charAt(0);
        String temp = currentCommand.substring(1);
        return flag == A_COMMAND_FLAG && (isNumeric(temp) || isIdentifier(temp));
    }

    private boolean isLCommand() {
        char lCommandFlagStart = currentCommand.charAt(0);
        char lCommandFlagEnd = currentCommand.charAt(currentCommand.length() - 1);
        String temp = currentCommand.substring(1, currentCommand.length() - 1);
        return lCommandFlagStart == L_COMMAND_FLAG_START && lCommandFlagEnd == L_COMMAND_FLAG_END && isIdentifier(temp);
    }


    public boolean isNumeric(String input) {
        return match(input, "[0-9]*");
    }

    public boolean isIdentifier(String input) {
        return match(input, "^[A-Za-z_$]+[A-Za-z_$\\d]+$");
    }

    private boolean match(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public String symbol() {
        String symbol = EMPTY;
        switch (commandType()) {
            case A_COMMAND:
                symbol = getSymbolFromACommand();
                break;
            case L_COMMAND:
                symbol = getSymbolFromLCommand();
                break;
        }
        return symbol;
    }

    public String dest() {
        if (commandType() == C_COMMAND && currentCommand.indexOf('=') != -1) {
            String map = currentCommand.split("=")[0];
            return map;
        } else if (commandType() == C_COMMAND) {
            return NULL;
        }
        return EMPTY;
    }

    public String comp() {
        if (commandType() == C_COMMAND) {
            String map = currentCommand.split(";")[0];
            return map;
        }
        return EMPTY;
    }

    public String jump() {
        if (commandType() == C_COMMAND && currentCommand.indexOf(';') != -1) {
            String map = currentCommand.split(";")[1];
            return map;
        } else if (commandType() == C_COMMAND) {
            return NULL;
        }
        return EMPTY;
    }

    private String getSymbolFromACommand() {
        return currentCommand.substring(1);
    }

    private String getSymbolFromLCommand() {
        return currentCommand.substring(1, currentCommand.length() - 1);
    }

    public String getCurrentCommand() {
        return currentCommand;
    }
}
