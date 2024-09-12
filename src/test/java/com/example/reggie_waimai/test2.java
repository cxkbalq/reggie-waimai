package com.example.reggie_waimai;

import java.io.File;
import java.io.FilenameFilter;
import java.net.Socket;
import java.util.Scanner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CountDownLatch;
import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;
import java.io.*;

public class test2 {
    public static void main(String[] args) {
        // 文件拷贝部分
        try (FileInputStream in = new FileInputStream("input.txt");
             FileOutputStream out = new FileOutputStream("output_byte.txt");
             FileReader reader = new FileReader("input.txt");
             FileWriter writer = new FileWriter("output_char.txt");
             BufferedReader bufferedReader = new BufferedReader(reader);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            // 使用字节流拷贝
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            // 使用字符流拷贝
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 密码验证部分
        BufferedReader passwordReader = new BufferedReader(new InputStreamReader(System.in));
        String password = "123456";
        int attempts = 5;

        for (int i = 0; i < attempts; i++) {
            System.out.println("请输入密码：");
            try {
                String input = passwordReader.readLine();
                if (input.equals(password)) {
                    System.out.println("恭喜你进入游戏");
                    break;
                } else {
                    System.out.println("密码错误");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("结束游戏");
        System.exit(0);
    }
}