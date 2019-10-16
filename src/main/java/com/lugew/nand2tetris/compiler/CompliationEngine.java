package com.lugew.nand2tetris.compiler;

/**
 * 编译引擎
 *
 * @author LuGew
 * @since 2019/10/16
 */
public class CompliationEngine {

    public CompliationEngine(String inputFileName, String outputFileName) {

    }

    /**
     * 编译整个类
     */
    public void compileClass() {

    }

    /**
     * 编译静态声明或字段声明
     */
    public void compileClassVarDec() {

    }

    /**
     * 编译整个方法、函数或者构造函数
     */
    public void compileSubroutine() {

    }


    /**
     * 编译参数列表（可能为空），不包括括号“（）”
     */
    public void compileParameterList() {

    }

    /**
     * 编译Var声明
     */
    public void comapileVarDec() {

    }

    /**
     * 编译一系列语句，不包含大括号“{}”
     */
    public void compileStatements() {

    }

    /**
     * 编译do语句
     */
    public void compileDo() {

    }

    /**
     * 编译let语句
     */
    public void compleLet() {

    }

    /**
     * 编译while语句
     */
    public void compleWhile() {

    }

    /**
     * 编译return语句
     */
    public void compleReturn() {

    }

    /**
     * 编译if语句，包括可选的else从句
     */
    public void compleIf() {

    }

    /**
     * 编译一个表达式
     */
    public void compleExpression() {

    }

    /**
     * 编译一个“term”。本程序在“从多种可能的分析规则中做出决策”的时候会遇到一点难度。
     * 特别是，如果当前字元为标识符，那么本程序必须要区分变量、数组、子程序调用这三种情况。
     * 通过提前查看下一个字元（可以为”[“、”(”或“.”），就可以区分这三种可能性了。后续其他字元都
     * 不属于这个term，故不需要取用。
     */
    public void compleTerm() {

    }

    /**
     * 编译由逗号分隔的表达式列表（可能为空）
     */
    public void compleExpressionList() {

    }
}
