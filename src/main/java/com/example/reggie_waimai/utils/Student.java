package com.example.reggie_waimai.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Student {
    private String name;
    private String gender;
    private int studentId;

    public Student(String name, String gender, int studentId) {
        this.name = name;
        this.gender = gender;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getStudentId() {
        return studentId;
    }

    public static void main(String[] args) {
        // 创建学员对象列表
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Male", 1001));
        students.add(new Student("Emily", "Female", 1003));
        students.add(new Student("Michael", "Male", 1002));

        // 按学号进行排序
        Collections.sort(students, Comparator.comparingInt(Student::getStudentId));

        // 输出学员对象的详细信息
        for (Student student : students) {
            System.out.println("姓名：" + student.getName());
            System.out.println("性别：" + student.getGender());
            System.out.println("学号：" + student.getStudentId());
            System.out.println("----------------------");
        }
    }
}