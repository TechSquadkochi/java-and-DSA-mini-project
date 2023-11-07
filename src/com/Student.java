package com;

import java.io.*;
import java.util.*;

public class Student {
    private int user;
    private String name;
    private String course;

    public Student(int user, String name, String course) {
        this.user = user;
        this.name = name;
        this.course = course;
    }

    public int getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public static void createStudent(Student student) {
        try (FileWriter writer = new FileWriter("students.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(student.getUser() + "," + student.getName() + "," + student.getCourse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Student searchStudent(ArrayList<Student> students, int userId) {
        for (Student student : students) {
            if (student.getUser() == userId) {
                return student;
            }
        }
        return null;
    }
    public static ArrayList<Student> readStudentsFromFile() {
        ArrayList<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int userId = Integer.parseInt(parts[0]);
                String userName = parts[1];
                String course = parts[2];

                Student student = new Student(userId, userName, course);
                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }

    }

