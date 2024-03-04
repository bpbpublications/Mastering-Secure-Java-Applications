package com.bpb.securecoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.StringJoiner;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String grade;

    private Double gpa;

    // avoid this "No default constructor for entity"
    public Student() {
    }

    public Student(Long id, String name, String grade, Double gpa) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.gpa = gpa;
    }

    public Student(String name, String grade, Double gpa) {
        this.name = name;
        this.grade = grade;
        this.gpa = gpa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("grade='" + grade + "'")
                .add("gpa=" + gpa)
                .toString();
    }
}
