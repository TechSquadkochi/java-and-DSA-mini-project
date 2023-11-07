package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    private String user;
    private String name;
    private static final String FILE_NAME = "users.txt";

    public Admin(String user, String name) {
        this.user = user;
        this.name = name;
    }

    public void printDetails() {
        System.out.println("Admin Details:");
        System.out.println("User: " + user);
        System.out.println("Name: " + name);

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println("Admin Details:");
            writer.println("User: " + user);
            writer.println("Name: " + name);
            writer.println(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(ArrayList<Student> students) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter student details:");
        System.out.print("Student ID: ");
        int id = Integer.parseInt(reader.readLine()); 
        System.out.print("Student Name: ");
        String name = reader.readLine();
        System.out.print("Student Course: ");
        String course = reader.readLine();
        Student student = new Student(id, name, course);
        Student.createStudent(student);
        System.out.println("Student added successfully.");
    }

    public void addCandidate(ArrayList<Candidate> candidates) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter candidate details:");
            System.out.print("Candidate ID: ");
            String id = reader.readLine();
            System.out.print("Candidate Name: ");
            String name = reader.readLine();
            System.out.print("Party Name: ");
            String partyName = reader.readLine();

            Candidate newCandidate = new Candidate(id, name, partyName);
            newCandidate.saveCandidate();
            candidates.add(newCandidate);

            System.out.println("Candidate added successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void searchStudent(ArrayList<Student> students) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter the student ID to search: ");
            int searchID = Integer.parseInt(reader.readLine());

            Student foundStudent = Student.searchStudent(students, searchID);
            if (foundStudent != null) {
                System.out.println("Student found!");
                System.out.println("Student ID: " + foundStudent.getUser());
                System.out.println("Student Name: " + foundStudent.getName());
                System.out.println("Student Course: " + foundStudent.getCourse());
            } else {
                System.out.println("Student not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }
    public void searchCandidate(ArrayList<Candidate> candidates) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter the candidate ID to search: ");
            String searchID = reader.readLine();

            Candidate foundCandidate = Candidate.searchCandidate(candidates, searchID);

            if (foundCandidate != null) {
                System.out.println("Candidate found!");
                foundCandidate.printData();
            } else {
                System.out.println("Candidate not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }



    public void findElectionResult(ArrayList<Candidate> candidates) {
        for (Candidate candidate : candidates) {
            candidate.addVote(); 
        }

        Candidate winner = candidates.get(0);
        for (Candidate candidate : candidates) {
            if (candidate.getVotesReceived() > winner.getVotesReceived()) {
                winner = candidate;
            }
        }

        System.out.println("Election Winner: " + winner.getPartyName());
    }


    public void adminMenu() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Candidate> candidates = new ArrayList<>();

        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Candidate");
            System.out.println("3. Search A Student");
            System.out.println("4. Search A Candidate");
            System.out.println("5. Print Details of all Students");
            System.out.println("6. Find the Election Result");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            String choice;
            try {
                choice = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            switch (choice) {
                case "1":
                    System.out.println("Adding Student...");
                    try {
                        addStudent(students);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    System.out.println("Adding Candidate...");
				addCandidate(candidates);
                    break;
                case "3":
                    System.out.println("Searching Student...");
                    searchStudent(students);
                    break;
                case "4":
                    System.out.println("Searching Candidate...");
                    searchCandidate(candidates);
                    break;
                case "5":
                	ArrayList<Student> student1 = Student.readStudentsFromFile();
                	if (student1 != null && !student1.isEmpty()) {
                	    System.out.println("Printing Details of all Students...");
                	    for (Student student : student1) { 
                	        System.out.println("Student ID: " + student.getUser());
                	        System.out.println("Student Name: " + student.getName());
                	        System.out.println("Student Course: " + student.getCourse());
                	        System.out.println();
                	    }
                	} else {
                	    System.out.println("No students found.");
                	}
                	break;

                case "6":
                    System.out.println("Finding Election Result...");
                    findElectionResult(candidates);
                    break;
                case "7":
                    System.out.println("Exiting the Admin Menu. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

   }