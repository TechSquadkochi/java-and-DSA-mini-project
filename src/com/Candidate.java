package com;

import java.io.*;
import java.util.ArrayList;

public class Candidate {
    private String id;
    private String partyName;
    private String student;
    private static final String CANDIDATES_FILE = "candidates.txt";

    public Candidate(String id, String partyName, String student) {
        this.partyName = partyName;
        this.student = student;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getStudent() {
        return student;
    }

    public void saveCandidate() {
        try (FileWriter fileWriter = new FileWriter(CANDIDATES_FILE, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("Candidate ID: " + id + "\n");
            bufferedWriter.write("Party Name: " + partyName + "\n");
            bufferedWriter.write("Student: " + student + "\n\n");
            System.out.println("Candidate details saved successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Candidate searchCandidate(ArrayList<Candidate> candidates, String searchID) {
        for (Candidate candidate : candidates) {
            if (candidate.getId().equals(searchID)) {
                return candidate;
            }
        }
        return null;
    }

    public void printData() {
        System.out.println("Candidate ID: " + id);
        System.out.println("Party Name: " + partyName);
        System.out.println("Student: " + student);
        System.out.println();
    }

    public static void printAllCandidates(ArrayList<Candidate> candidates) {
        System.out.println("All Candidates:");
        for (Candidate candidate : candidates) {
            System.out.println("Candidate ID: " + candidate.getId());
            System.out.println("Party Name: " + candidate.getPartyName());
            System.out.println("Student: " + candidate.getStudent());
            System.out.println();
        }
    }

    public void addVote() {
        Vote.saveVote(partyName);
    }

    public int getVotesReceived() {
        return Vote.sum(partyName);
    }

    public static Candidate findWinner(ArrayList<Candidate> candidates) {
        Candidate winner = candidates.get(0);
        for (Candidate candidate : candidates) {
            if (candidate.getVotesReceived() > winner.getVotesReceived()) {
                winner = candidate;
            }
        }
        return winner;
    }
}
