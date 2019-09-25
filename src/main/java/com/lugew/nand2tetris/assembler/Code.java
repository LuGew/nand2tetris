package com.lugew.nand2tetris.assembler;


public class Code {
    private static final String C_COMMAND_START_BITS = "111";

    public Code() {
    }

    private static String dest(String mnemonic) {
        return SymbolTable.getCodeFromDestMap(mnemonic);
    }

    private static String comp(String mnemonic) {
        return SymbolTable.getCodeFromCompMap(mnemonic);
    }

    private static String jump(String mnemonic) {
        return SymbolTable.getCodeFromJumpMap(mnemonic);
    }

    public static String get16bitsCode(String compMnemonic, String destMnemonic, String jumpMnemonic) {
        StringBuffer code = new StringBuffer(C_COMMAND_START_BITS);
        code.append(comp(compMnemonic)).append(dest(destMnemonic)).append(jump(jumpMnemonic));
        return code.toString();
    }
}
