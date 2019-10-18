package com.lugew.nand2tetris.compiler;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
    private String currentToken;
    private String fileName;
    Set<String> classMap = new HashSet<>();

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
    public static final String BOOLEAN = "boolean";
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
    private Writer writer;

    public JackTokenizer(String fileName) throws IOException {
        this.fileName = fileName;
        scanner = new Scanner(new FileReader(fileName));
        int dotIndex = fileName.lastIndexOf(".");
        String prefix = fileName.substring(0, dotIndex);
        writer = new PrintWriter(prefix + "Test.xml");
        currentToken = EMPTY;

    }

    public boolean hasMoreTokens() {
        advance();
        return !EMPTY.equals(currentToken);
    }

    public int tokenType() {
        switch (currentToken) {
            case CLASS:
            case METHOD:
            case INT:
            case FUNCTION:
            case CONSTRUCTOR:
            case CHAR:
            case BOOLEAN:
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
            case SWUNG_DASH:
                return SYMBOL;
            default:
                if (isIdentifier(currentToken)) {
                    return IDENTIFIER;
                } else if (isIntegerConstant(currentToken)) {
                    return INT_CONST;
                } else if (isStringConstant(currentToken)) {
                    return STRING_CONST;
                }
                return 0;
        }

    }

    public String keyword() {
        return currentToken;
    }

    public String identifier() {
        return currentToken;
    }

    public String inVal() {
        return currentToken;
    }

    public String stringVal() {
        return currentToken;
    }

    private void advance() {
        while (scanner.hasNext()) {
            currentToken = scanner.next();
            if (!isComment() && !isSpace()) {
                return;
            } else {
                scanner.nextLine();
            }
        }
        currentToken = EMPTY;
    }

    private boolean isComment() {
        return match(currentToken, "\\/\\/[^\\n]*") || match(currentToken, "/\\*\\*");
    }

    private boolean isSpace() {
        return match(currentToken, "\\s*");
    }

    private boolean isIntegerConstant(String value) {

        return match(currentToken, "^[0-9]*$");
    }

    private boolean isStringConstant(String value) {
        return match(currentToken, "\"\\w*\"");
    }

    private boolean isIdentifier(String value) {
        return match(currentToken, "[A-Za-z_$]+[A-Za-z_$\\d]");
    }

    private boolean match(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public String getCurrentToken() {
        return currentToken;
    }


    public void writeCurrentTokenToFile() throws IOException {
        while (hasMoreTokens()) {
            int tokenType = tokenType();
            switch (tokenType) {
                case KEYWORD:
                    writeKeyword();
                    break;
                case SYMBOL:
                    writeSymbol();
                    break;
                case IDENTIFIER:
                    writeIdentifier();
                    break;
                case INT_CONST:
                    writeIntConst();
                    break;
                case STRING_CONST:
                    writeStringConst();
                    break;
                default:
                    break;
            }
        }

    }


    private void writeParameterList() throws IOException {
        writer.append("<parameterList>\n");
        boolean endFlagSubroutineDec;
        while (hasMoreTokens()) {
            endFlagSubroutineDec = false;
            switch (currentToken) {
                case CLOSE_PAREN:
                    writeSymbol();
                    endFlagSubroutineDec = true;
                    break;
                case COMMA:
                    writeSymbol();
                    break;
                default:
                    writeTypeOrVar();
                    break;
            }
            if (endFlagSubroutineDec) {
                break;
            }
        }
        writer.append("</parameterList>\n");
    }

    private void writeTypeOrVar() throws IOException {
        switch (currentToken) {
            case INT:
            case CHAR:
            case BOOLEAN:
                writeKeyword();
                break;
            default:
                if (classMap.contains(currentToken)) {
                    writeKeyword();
                } else {
                    writeIdentifier();
                }
                break;
        }
    }

    private void writeSymbol() throws IOException {
        String output = currentToken;
        if (LESS_THAN.equals(currentToken)) {
            output = "&lt;";
        } else if (GREATER_THAN.equals(currentToken)) {
            output = "&gt;";
        } else if (AND.equals(currentToken)) {
            output = "&amp";
        }
        writer.append("<symbol>").append(output).append("</symbol>\n");
    }

    private void writeKeyword() throws IOException {
        writer.append("<keyword>").append(currentToken).append("</keyword>\n");
    }

    private void writeIdentifier() throws IOException {
        writer.append("<identifier>").append(currentToken).append("</identifier>\n");
    }

    private void writeIntConst() throws IOException {
        writer.append("<integerConstant>").append(currentToken).append("</integerConstant>\n");
    }

    private void writeStringConst() throws IOException {
        writer.append("<stringConstant>").append(currentToken).append("</stringConstant>\n");
    }

    public void writeStart() throws IOException {
        writer.append("<tokens>\n");
    }

    public void writeEnd() throws IOException {
        writer.append("</tokens>\n");
    }

    public void closeWriter() throws IOException {
        writer.flush();
        writer.close();
    }
}
