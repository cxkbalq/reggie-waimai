package com.example.reggie_waimai;

import org.junit.Test;
import java.util.*;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.util.Scanner;



public class test{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("文件管理");
            System.out.println("1. 查询所有文件");
            System.out.println("2. 查询指定文件");
            System.out.println("3. 删除文件");
            System.out.println("请输入您要的操作:");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listAllFiles();
                    break;
                case 2:
                    System.out.println("请输入查询的文件名关键字:");
                    String keyword = scanner.next();
                    searchFiles(keyword);
                    break;
                case 3:
                    System.out.println("请输入要删除的文件名称:");
                    String fileName = scanner.next();
                    deleteFile(fileName);
                    break;
                default:
                    System.out.println("无效的选项！");
            }
        }
    }

    private static void listAllFiles() {
        File folder = new File("src/test/java/com/example/reggie_waimai");
        File[] files = folder.listFiles();
        if (files != null) {
            System.out.println("-----文件列表------");
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }

    private static void searchFiles(String keyword) {
        File folder = new File("src/test/java/com/example/reggie_waimai");
        File[] files = folder.listFiles();
        if (files != null) {
            System.out.println("-----文件列表------");
            for (File file : files) {
                if (file.getName().contains(keyword)) {
                    System.out.println(file.getName());
                }
            }
        }
    }

    private static void deleteFile(String fileName) {
        File file = new File("src/test/java/com/example/reggie_waimai" + File.separator + fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(fileName + "删除成功");
            } else {
                System.out.println(fileName + "删除失败");
            }
        } else {
            System.out.println(fileName + "不存在");
        }
    }
}

