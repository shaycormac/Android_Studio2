package com.itheima.binghua.shele.domain;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-14 17:21 GMT+8
 * @email 574583006@qq.com
 */
public class Student
{
    public Student() {
    }

    public Student(String name, char sex, double score) {
       
        this.name = name;
        this.sex = sex;
        this.score = score;
    }

   

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    
    private String name;
    private char sex;
    private double score;

    @Override
    public String toString() {
        return "姓名：-" + name + "性别：-" + sex + "得分：-" + score;
    }
}
