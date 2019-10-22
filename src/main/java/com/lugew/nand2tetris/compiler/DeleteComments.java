package com.lugew.nand2tetris.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 删除Java代码中的注释
 *
 * @author Alive
 * @build 2010-12-23
 */
public class DeleteComments {

    private static int count = 0;

    /**
     * 删除文件中的各种注释，包含//、/* * /等
     *
     * @param charset 文件编码
     * @param file    文件
     */
    public static String clearComment(File file, String charset) {
        try {
            // 递归处理文件夹
            if (!file.exists()) {
                return null;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    clearComment(f, charset); // 递归调用
                }
                return null;
            } else if (!file.getName().endsWith(".jack")) {
                // 非java文件直接返回
                return null;
            }
            System.out.println("-----开始处理文件：" + file.getAbsolutePath());

            // 根据对应的编码格式读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            StringBuffer content = new StringBuffer();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                content.append(tmp);
                content.append("\n");
            }
            reader.close();
            String target = content.toString();
            // String s =
            // target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*\\/",
            // ""); //本段正则摘自网上，有一种情况无法满足（/* ...**/），略作修改
            String s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
            // System.out.println(s);
            // 使用对应的编码格式输出
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:/temp.jack"), charset));
            out.write(s);
            out.flush();
            out.close();
            count++;
            System.out.println("-----文件处理完成---" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "c:/temp.jack";
    }

    public static void clearComment(String filePath, String charset) {
        clearComment(new File(filePath), charset);
    }

    public static void clearComment(String filePath) {
        clearComment(new File(filePath), "UTF-8");
    }

    public static String clearComment(File file) {
        return clearComment(file, "UTF-8");
    }


}