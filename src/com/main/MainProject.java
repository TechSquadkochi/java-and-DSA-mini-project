package com.main;

import com.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainProject {

    public static void main(String[] args) {
    	User user;
    	try
    	{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Welcome to the Voting System!");
            while (true) {
                System.out.println("Please login to proceed:");
                System.out.print("Enter your username: ");
                String username = reader.readLine();
                System.out.print("Enter your password: ");
                String password = reader.readLine();

                user = new User(username, password, 1); 
                if (user.login(username, password)) {
                    System.out.println("Login Successful!");
                    break;
                } else {
                    System.out.println("Invalid credentials. Please try again.");
                }
            }

           
        }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    		
    	}
    }

