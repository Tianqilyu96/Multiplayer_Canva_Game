package server;

import java.io.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import remote.RemoteInterface;


public class RemoteServer {

	static int port = 0;
	static String portString = "";
	static String username = "";
	public RemoteInterface remoteMethods;
	
	
	
	public boolean initialRMI() {
		try {
		
		remoteMethods = new RemoteImplementation();
		Registry registry = LocateRegistry.createRegistry(RemoteServer.port);
		registry.rebind("RemoteOperation", remoteMethods);
		
		System.out.println("Username: "+username+", Port: "+ portString);
		return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public static void main(String[] args) throws Exception {
		
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Please type the host name :");
			username = input.nextLine().toString();
			System.out.println("Please type port number:");
			portString = input.nextLine().toString();
			
			// Close the scanner
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("read failed..");
		}

		port = Integer.parseInt(portString);
		RemoteServer remoteServer = new RemoteServer();
		try {
			boolean success = remoteServer.initialRMI();
			if(success) {
				System.out.println("RMI created, waiting...");
			}
			else {
				System.out.println("RMI creating failed...");
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println( "RMI creating failed...");
		}
		
	}
	}

