package com.lugew.nand2tetris.compiler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jack 字元化
 *
 * @author LuGew
 * @since 2019/10/15
 */
public class JackTokenizer {
    private Scanner scanner;
    private String currentCommand;
    private String fileName;

    public static final String EMPTY = "";
    public static final int KEYWORD = 1;
    public static final int SYMBOL = 2;
    public static final int IDENTIFIER = 3;
    public static final int INT_CONST = 4;
    public static final int STRING_CONST = 5;
    public static final String CLASS = "class";
    public static final String METHOD = "method";
    public static final String INT = "int";
    public static final String FUNCTION = "function";
    public static final String CONSTRUCTOR = "constructor";
    public static final String CHAR = "char";
    public static final String VOID = "void";
    public static final String VAR = "var";
    public static final String STATIC = "static";
    public static final String FIELD = "field";
    public static final String LET = "let";
    public static final String DO = "do";
    public static final String IF = "if";
    public static final String ELSE = "else";
    public static final String WHILE = "while";
    public static final String RETURN = "return";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String NULL = "null";
    public static final String THIS = "this";

    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";
    public static final String OPEN_PAREN = "(";
    public static final String CLOSE_PAREN = ")";
    public static final String OPEN_BRACKET = "[";
    public static final String CLOSE_BRACKET = "]";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String SLASH = "/";
    public static final String AND = "&";
    public static final String VERTICAL_BAR = "|";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String EQUAL = "=";
    public static final String SWUNG_DASH = "~";


    public JackTokenizer(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        scanner = new Scanner(new FileReader(fileName));
        currentCommand = EMPTY;
    }

    public boolean hasMoreCommands() {
        advance();
        return !EMPTY.equals(currentCommand);
    }

    public int tokenType() {
        switch (currentCommand) {
            case CLASS:
            case METHOD:
            case INT:
            case FUNCTION:
            case CONSTRUCTOR:
            case CHAR:
            case VOID:
            case VAR:
            case STATIC:
            case FIELD:
            case LET:
            case DO:
            case IF:
            case ELSE:
            case WHILE:
            case RETURN:
            case TRUE:
            case FALSE:
            case NULL:
            case THIS:
                return KEYWORD;
            case OPEN_BRACE:
            case CLOSE_BRACE:
            case OPEN_PAREN:
            case CLOSE_PAREN:
            case OPEN_BRACKET:
            case CLOSE_BRACKET:
            case DOT:
            case COMMA:
            case SEMICOLON:
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case SLASH:
            case AND:
            case VERTICAL_BAR:
            case GREATER_THAN:
            case LESS_THAN:
            case EQUAL:
                return SYMBOL;
            default:
                if (isIdentifier(currentCommand)) {
                    return IDENTIFIER;
                } else if (isIntegerConstant(currentCommand)) {
                    return INT_CONST;
                } else if (isStringConstant(currentCommand)) {
                    return STRING_CONST;
                }
                return 0;
        }

    }

    private void advance() {
        while (scanner.hasNext()) {
            currentCommand = scanner.next();
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

    private boolean isIntegerConstant(String value) {

        return match(currentCommand, "^[0-9]*$");
    }

    private boolean isStringConstant(String value) {
        return match(currentCommand, "\"\\w*\"");
    }

    private boolean isIdentifier(String value) {
        return match(currentCommand, "[A-Za-z_$]+[A-Za-z_$\\d]");
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
