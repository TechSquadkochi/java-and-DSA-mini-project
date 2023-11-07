package com;

import java.io.*;
import java.util.ArrayList;

public class Vote {
    private String studentName;
    private String candidateName;
    private static final String VOTES_FILE = "votes.txt";

    public Vote(String studentName, String candidateName) {
        this.studentName = studentName;
        this.candidateName = candidateName;
    }

    public static void saveVote(String partyName) {
        try (FileWriter fileWriter = new FileWriter(VOTES_FILE, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("," + partyName);
            bufferedWriter.newLine();
            System.out.println("Vote saved successfully for the candidate from " + partyName);
        } catch (IOException e) {
            System.err.println("Error saving the vote: " + e.getMessage());
        }
    }


    public static int sum(String candidateName) {
        int totalVotes = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(VOTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[1].equals(candidateName)) {
                    totalVotes++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the votes: " + e.getMessage());
        }
        return totalVotes;
    }
}
