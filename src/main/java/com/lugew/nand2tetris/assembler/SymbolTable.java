package com.lugew.nand2tetris.assembler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LuGew
 * @since 2019/9/25
 */
public class SymbolTable {
    private static final Map<String, String> DEST = new HashMap<String, String>() {
        {
            put("NULL", "000");
            put("M", "001");
            put("D", "010");
            put("MD", "011");
            put("A", "100");
            put("AM", "101");
            put("AD", "110");
            put("AMD", "111");
        }
    };
    private static final Map<String, String> JUMP = new HashMap<String, String>() {
        {
            put("NULL", "000");
            put("JGT", "001");
            put("JEQ", "010");
            put("JGE", "011");
            put("JLT", "100");
            put("JNE", "101");
            put("JLE", "110");
            put("JMP", "111");
        }
    };
    private static final Map<String, String> COMP = new HashMap<String, String>() {
        {
            put("0", "0101010");
            put("1", "0111111");
            put("-1", "0111010");
            put("D", "0001100");
            put("A", "0110000");
            put("M", "1110000");
            put("!D", "0001111");
            put("-A", "0110011");
            put("-M", "1110011");
            put("D+1", "0011111");
            put("A+1", "0110111");
            put("M+1", "1110111");
            put("D-1", "0001110");
            put("A-1", "0110010");
            put("M-1", "1110010");
            put("D+A", "0000010");
            put("D+M", "1000010");
            put("D-A", "0010011");
            put("D-M", "1010011");
            put("A-D", "0000111");
            put("M-D", "1000111");
            put("D&A", "0000000");
            put("D&M", "1000000");
            put("D|A", "0010101");
            put("D|M", "1010101");
        }
    };
    private static final Map<String, String> PREDEFINED_SYMBOLS = new HashMap<String, String>() {
        {
            put("SP", "0000000000000000");
            put("LCL", "0000000000000001");
            put("ARG", "0000000000000010");
            put("THIS", "0000000000000011");
            put("THAT", "0000000000000100");
            put("R0", "0000000000000000");
            put("R1", "0000000000000001");
            put("R2", "0000000000000010");
            put("R3", "0000000000000011");
            put("R4", "0000000000000100");
            put("R5", "0000000000000101");
            put("R6", "0000000000000110");
            put("R7", "0000000000000111");
            put("R8", "0000000000001000");
            put("R9", "0000000000001001");
            put("R10", "0000000000001010");
            put("R11", "0000000000001011");
            put("R12", "0000000000001100");
            put("R13", "0000000000001101");
            put("R14", "0000000000001110");
            put("R15", "0000000000001111");
            put("SCREEN", "0100000000000000");
            put("KBD", "0110000000000000");
        }
    };

    private static Map<String, Integer> customizedSymbols = new HashMap<String, Integer>();
    private static int customizedSymbolAddressIndex = 1;

    public SymbolTable() {
    }

    private static void addSymbol(String customizedSymbol) {
        customizedSymbols.putIfAbsent(customizedSymbol, customizedSymbolAddressIndex++);
    }

    public static int getSymbolAddress(String customizedSymbol) {
        addSymbol(customizedSymbol);
        return customizedSymbols.get(customizedSymbol);
    }

    public static String getCodeFromDestMap(String destKey) {
        return DEST.get(destKey);
    }

    public static String getCodeFromJumpMap(String jumpKey) {
        return JUMP.get(jumpKey);
    }

    public static String getCodeFromCompMap(String compKey) {
        return COMP.get(compKey);
    }
}
