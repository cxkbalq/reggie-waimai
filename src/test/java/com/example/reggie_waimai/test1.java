package com.example.reggie_waimai;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class test1 {
    private Map<String, Member> members;

    public test1() {
        members = new HashMap<>();
    }

 public void registerMember(String memberId, String password, String cardNumber, String name) {
        if (!members.containsKey(memberId)) {
            Member newMember = new Member(memberId, password, cardNumber, name);
            members.put(memberId, newMember);
            System.out.println("会员 " + memberId + " 注册成功！");
            System.out.println("密码为"+password);
            System.out.println("账号：" + memberId + "，姓名：" + name + "，卡号：" + cardNumber);
            System.out.printf("开卡成功");
                  // 输出当前时间
        Date currentDate = new Date();
        System.out.println("当前时间：" + currentDate);
        } else {
            System.out.println("会员 " + memberId + " 已存在！");
        }
    }

    public void accumulatePoints(String memberId, int points) {
        Member member = members.get(memberId);
        if (member != null) {
            member.accumulatePoints(points);
            System.out.println("会员 " + memberId + " 累计积分成功！");
        } else {
            System.out.println("会员 " + memberId + " 不存在！");
        }
    }

    public void redeemPoints(String memberId, int points) {
        Member member = members.get(memberId);
        if (member != null) {
            member.redeemPoints(points);
            System.out.println("会员 " + memberId + " 积分兑换成功！");
        } else {
            System.out.println("会员 " + memberId + " 不存在！");
        }
    }

    public void checkPoints(String memberId) {
        Member member = members.get(memberId);
        if (member != null) {
            System.out.println("会员 " + memberId + " 剩余积分：" + member.getPoints());
        } else {
            System.out.println("会员 " + memberId + " 不存在！");
        }
    }

    public void changePassword(String memberId, String oldPassword, String newPassword) {
        Member member = members.get(memberId);
        if (member != null && member.getPassword().equals(oldPassword)) {
            member.setPassword(newPassword);
            System.out.println("会员 " + memberId + " 密码修改成功！");
        } else {
            System.out.println("会员 " + memberId + " 不存在或密码错误！");
        }
    }

    public static void main(String[] args) {
        test1 system = new test1();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. 注册会员");
            System.out.println("2. 累计积分");
            System.out.println("3. 积分兑换");
            System.out.println("4. 查询剩余积分");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("请输入会员ID和密码：");
                    System.out.println("请输入卡号和用户名：");
                    String memberId = scanner.next();
                    String password = scanner.next();
                    String password1 = scanner.next();
                    String password2 = scanner.next();
                    system.registerMember(memberId, password,password1,password2);
                    break;
                case 2:
                    System.out.println("请输入会员ID和要累计的积分：");
                    memberId = scanner.next();
                    int accumulatePoints = scanner.nextInt();
                    system.accumulatePoints(memberId, accumulatePoints);
                    break;
                case 3:
                    System.out.println("请输入会员ID和要兑换的积分：");
                    memberId = scanner.next();
                    int redeemPoints = scanner.nextInt();
                    system.redeemPoints(memberId, redeemPoints);
                    break;
                case 4:
                    System.out.println("请输入会员ID：");
                    memberId = scanner.next();
                    system.checkPoints(memberId);
                    break;
                case 5:
                    System.out.println("请输入会员ID、旧密码和新密码：");
                    memberId = scanner.next();
                    String oldPassword = scanner.next();
                    String newPassword = scanner.next();
                    system.changePassword(memberId, oldPassword, newPassword);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("无效的选项！");
            }
        }
    }
}

class Member {
  private String memberId;
    private String password;
    private String cardNumber;
    private String name;
    private int points;

    public Member(String memberId, String password, String cardNumber, String name) {
        this.memberId = memberId;
        this.password = password;
        this.cardNumber = cardNumber;
        this.name = name;
        this.points = 0;
    }

    public void accumulatePoints(int points) {
        this.points += points;
    }

    public void redeemPoints(int points) {
        if (this.points >= points) {
            this.points -= points;
        } else {
            System.out.println("积分不足！");
        }
    }

    public int getPoints() {
        return points;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}