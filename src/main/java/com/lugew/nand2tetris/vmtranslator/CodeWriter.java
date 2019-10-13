package com.lugew.nand2tetris.vmtranslator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 将vm命令翻译程hack汇编代码
 *
 * @author LuGew
 * @since 2019/10/1
 */
public class CodeWriter {
    private int index = 0;
    private int callIndex = 0;
    private Writer writer;
    private String fileName;
    private Map<String, String> segmentMap = new HashMap<String, String>() {{
        put("local", "LCL");
        put("argument", "ARG");
        put("this", "THIS");
        put("that", "THAT");
        put("pointer", "3");
        put("temp", "5");
    }};

    public CodeWriter(String fileName) throws IOException {
        writer = new PrintWriter(fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void writeArithmetic(String command) throws IOException {
        switch (command) {
            case Parser.ADD:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D+M\n" +
                        "@SP\n" +
                        "M=M-1\n");
                break;
            case Parser.SUB:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=M-D\n" +
                        "@SP\n" +
                        "M=M-1\n");
                break;
            case Parser.NEG:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "M=-M\n");
                break;
            case Parser.EQ:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "@EQ" + index + "\n" +
                        "D;JEQ\n" +
                        "@SP\n" +
                        "M=M-1\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@NEQ" + index + "\n" +
                        "0;JMP\n" +
                        "(EQ" + index + ")\n" +
                        "@SP\n" +
                        "M=M-1\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "(NEQ" + index + ")\n");
                index++;
                break;
            case Parser.GT:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "@GT" + index + "\n" +
                        "D;JGT\n" +
                        "@SP\n" +
                        "M=M-1\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@NGT" + index + "\n" +
                        "0;JMP\n" +
                        "(GT" + index + ")\n" +
                        "@SP\n" +
                        "M=M-1\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "(NGT" + index + ")\n");
                index++;
                break;
            case Parser.LT:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "@LT" + index + "\n" +
                        "D;JLT\n" +
                        "@SP\n" +
                        "M=M-1\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@NLT" + index + "\n" +
                        "0;JMP\n" +
                        "(LT" + index + ")\n" +
                        "@SP\n" +
                        "M=M-1\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "(NLT" + index + ")\n");
                index++;
                break;
            case Parser.AND:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D&M\n" +
                        "@SP\n" +
                        "M=M-1\n");
                break;
            case Parser.OR:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D|M\n" +
                        "@SP\n" +
                        "M=M-1\n");
                break;
            case Parser.NOT:
                writer.append("@SP\n" +
                        "A=M\n" +
                        "A=A-1\n" +
                        "M=!M\n");
                break;
        }

    }

    public void writePushPop(int type, String segment, int index) throws IOException {
        switch (type) {
            case Parser.C_PUSH:
                switch (segment) {
                    case Parser.CONSTANT:
                        writer.append(
                                "@" + index + "\n" +
                                        "D=A\n" +
                                        "@SP\n" +
                                        "A=M\n" +
                                        "M=D\n" +
                                        "@SP\n" +
                                        "M=M+1\n");
                        break;
                    case Parser.LOCAL:
                    case Parser.ARGUMENT:
                    case Parser.THIS:
                    case Parser.THAT:
                        writer.append("@").append(segmentMap.get(segment)).append("\n");
                        writer.append("A=M\n");
                        for (int i = 0; i < index; i++) {
                            writer.append("A=A+1\n");
                        }
                        writer.append("D=M\n");
                        writer.append("@SP\n");
                        writer.append("A=M\n");
                        writer.append("M=D\n");
                        writer.append("@SP\n");
                        writer.append("M=M+1\n");
                        break;
                    case Parser.POINTER:
                    case Parser.TEMP:
                        writer.append("@").append(segmentMap.get(segment)).append("\n");
                        for (int i = 0; i < index; i++) {
                            writer.append("A=A+1\n");
                        }
                        writer.append("D=M\n");
                        writer.append("@SP\n");
                        writer.append("A=M\n");
                        writer.append("M=D\n");
                        writer.append("@SP\n");
                        writer.append("M=M+1\n");
                        break;
                    case Parser.STATIC:
                        writer.append("@").append(fileName + '.' + index).append("\n");
                        writer.append("D=M\n");
                        writer.append("@SP\n");
                        writer.append("A=M\n");
                        writer.append("M=D\n");
                        writer.append("@SP\n");
                        writer.append("M=M+1\n");
                        break;

                }

                break;
            case Parser.C_POP:
                switch (segment) {
                    case Parser.LOCAL:
                    case Parser.ARGUMENT:
                    case Parser.THIS:
                    case Parser.THAT:
                        writer.append("@SP\n");
                        writer.append("A=M-1\n");
                        writer.append("D=M\n");
                        writer.append("@").append(segmentMap.get(segment)).append("\n");
                        writer.append("A=M\n");
                        for (int i = 0; i < index; i++) {
                            writer.append("A=A+1\n");
                        }
                        writer.append("M=D\n");
                        writer.append("@SP\n");
                        writer.append("M=M-1\n");
                        break;
                    case Parser.POINTER:
                    case Parser.TEMP:
                        writer.append("@SP\n");
                        writer.append("A=M-1\n");
                        writer.append("D=M\n");
                        writer.append("@").append(segmentMap.get(segment)).append("\n");
                        for (int i = 0; i < index; i++) {
                            writer.append("A=A+1\n");
                        }
                        writer.append("M=D\n");
                        writer.append("@SP\n");
                        writer.append("M=M-1\n");
                        break;
                    case Parser.STATIC:
                        writer.append("@SP\n");
                        writer.append("A=M-1\n");
                        writer.append("D=M\n");
                        writer.append("@").append(fileName + '.' + index).append("\n");
                        writer.append("M=D\n");
                        writer.append("@SP\n");
                        writer.append("M=M-1\n");
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void writeInit() throws IOException {
        writer.append("@256\n" +
                "D=A\n" +
                "@SP\n" +
                "M=D\n");
        writeCall("Sys.init", 0);
    }

    public void writeLabel(String label) throws IOException {
        writer.append("(").append(label).append(")\n");
    }

    public void writeGoto(String label) throws IOException {
        writer.append("@").append(label).append("\n").append("0;JMP\n");
    }

    public void writeIf(String label) throws IOException {
        writer.append("@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@" + label + "\n" +
                "D;JLT\n");
    }

    public void writeReturn() throws IOException {
        //FRAME=LCL
        writer.append("@LCL\n" +
                "D=M\n" +
                "@FRAME\n" +
                "M=D\n");
        //RET=*(FRAME-5)
        pushSegment("FRAME");
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, 5);
        writeArithmetic(Parser.SUB);
        writer.append("@SP\n" +
                "M=M-1\n");
        writer.append("@SP\n" +
                "A=M\n" +
                "A=M\n" +
                "D=M\n" +
                "@RET\n" +
                "M=D\n");
        //*ARG=pop()
        writer.append("@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n");
        writer.append("@ARG\n" +
                "A=M\n" +
                "M=D\n");

        //SP=ARG+1
        writer.append("@ARG\n" +
                "D=M+1\n" +
                "@SP\n" +
                "M=D\n");

        //THAT=*(FRAME-1)
        pushSegment("FRAME");
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, 1);
        writeArithmetic(Parser.SUB);
        writer.append("@SP\n" +
                "M=M-1\n");
        writer.append("@SP\n" +
                "A=M\n" +
                "A=M\n" +
                "D=M\n" +
                "@THAT\n" +
                "M=D\n");
        //THIS=*(FRAME-2)
        pushSegment("FRAME");
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, 2);
        writeArithmetic(Parser.SUB);
        writer.append("@SP\n" +
                "M=M-1\n");
        writer.append("@SP\n" +
                "A=M\n" +
                "A=M\n" +
                "D=M\n" +
                "@THIS\n" +
                "M=D\n");
        //ARG=*(FRAME-3)
        pushSegment("FRAME");
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, 3);
        writeArithmetic(Parser.SUB);
        writer.append("@SP\n" +
                "M=M-1\n");
        writer.append("@SP\n" +
                "A=M\n" +
                "A=M\n" +
                "D=M\n" +
                "@ARG\n" +
                "M=D\n");
        //LCL=*(FRAME-4)
        pushSegment("FRAME");
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, 4);
        writeArithmetic(Parser.SUB);
        writer.append("@SP\n" +
                "M=M-1\n");
        writer.append("@SP\n" +
                "A=M\n" +
                "A=M\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n");
        writer.append("@RET\n" +
                "A=M\n" +
                "0;JMP\n");
    }

    public void writeCall(String functionName, int numArgs) throws IOException {
        //push return-address
        pushReturn("RETURN-ADDRESS" + callIndex);
        //push lcl
        //push arg
        //push this
        //push that
        pushSegment(segmentMap.get(Parser.LOCAL));
        pushSegment(segmentMap.get(Parser.ARGUMENT));
        pushSegment(segmentMap.get(Parser.THIS));
        pushSegment(segmentMap.get(Parser.THAT));
        pushSegment("SP");
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, numArgs);
        writePushPop(Parser.C_PUSH, Parser.CONSTANT, 5);
        writeArithmetic(Parser.ADD);
        writeArithmetic(Parser.SUB);
        //ARG=SP-n-5
      /*  writer.append("@SP\n" +
                "M=M-1\n");*/
        writer.append("@SP\n" +
                "A=M-1\n" +
                "D=M\n" +
                "@ARG\n" +
                "M=D\n");
        //LCL=SP
        writer.append("@SP\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n");
        //goto function
        writeGoto(functionName);
        //return label
        writeLabel("RETURN-ADDRESS" + callIndex);
        callIndex++;
    }

    public void writeFunction(String functionName, int numLocals) throws IOException {
        writeLabel(functionName);
        for (int i = 0; i < numLocals; i++) {
            writePushPop(Parser.C_PUSH, Parser.CONSTANT, 0);
        }
    }

    private void pushSegment(String segment) throws IOException {
        writer.append("@" + segment + "\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n");
    }

    private void pushReturn(String returnAddress) throws IOException {
        writer.append("@" + returnAddress + "\n" +
                "D=A\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n");
    }

    public void close() throws IOException {
        writer.flush();
        writer.close();
    }
}
