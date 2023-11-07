package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int userType;

    public User(String username, String password, int userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserType() {
        return userType;
    }

    public boolean login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];
                    int storedUserType = Integer.parseInt(parts[2]);
                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        return true;
                    }
                   
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void save() {
        try (FileWriter fw = new FileWriter("users.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(username + "," + password + "," + userType + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMenu(ArrayList<Candidate> candidates) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
            System.out.println("Welcome to the Voting System!");
            System.out.println("1. See all Candidates");
            System.out.println("2. Vote for a Candidate");
            System.out.println("3. Exit");

            try {
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        printAllCandidates(candidates);
                        break;
                    case 2:
                        voteForCandidate(candidates);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private void printAllCandidates(ArrayList<Candidate> candidates) {
        System.out.println("All Candidates:");
        for (Candidate candidate : candidates) {
            System.out.println("Candidate ID: " + candidate.getId());
            System.out.println("Party Name: " + candidate.getPartyName());
            System.out.println("Party: " + candidate.getPartyName());
            System.out.println();
        }
    }

    private void voteForCandidate(ArrayList<Candidate> candidates) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Available Candidates to Vote:");
        Candidate.printAllCandidates(candidates);

        try {
            System.out.print("Enter the ID of the Candidate you want to vote for: ");
            String candidateID = reader.readLine();

            Candidate chosenCandidate = findCandidateByID(candidates, candidateID);
            if (chosenCandidate != null) {
                addVote(chosenCandidate);
            } else {
                System.out.println("Candidate with the provided ID not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }

    private Candidate findCandidateByID(ArrayList<Candidate> candidates, String id) {
        for (Candidate candidate : candidates) {
            if (candidate.getId().equals(id)) {
                return candidate;
            }
        }
        return null;
    }

    public void addVote(Candidate partyName) {
        Vote.saveVote(partyName.getPartyName());
    }
}
