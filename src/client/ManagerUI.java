package client;

import java.awt.EventQueue;


import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;

import remote.RemoteInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManagerUI {

	private JFrame frame;
	private JTextField textField;
	private static Client client;
	private JTextField Status;
	private JTextField Port;
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				client = new Client("abc", "port", "username");
				try {
					ManagerUI window = new ManagerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Create Board");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            try {
				client.remoteInterface.createWhiteBoard();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		btnNewButton.setBounds(33, 229, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Accept");
		btnNewButton_1.setBounds(162, 229, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Reject");
		btnNewButton_2.setBounds(291, 229, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("User List:");
		lblNewLabel.setBounds(309, 22, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(41, 48, 201, 73);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Connect");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String portString = Port.getText();
				
				try {
					Registry registry = LocateRegistry.getRegistry("localhost", Integer.parseInt(portString));
					client.remoteInterface = (RemoteInterface) registry.lookup("RemoteOperation");
					client.remoteInterface.addUser("host");
					Status.setText("Success");
					
					
				} catch (NumberFormatException | RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Thread showList = new Thread(){
					@Override
					public void run(){
					while(true) {
						try {
							ArrayList<String> clientList;
							clientList = client.remoteInterface.getUserList();
							String[] Data = (String[])clientList.toArray(new String[0]);
							list.setListData(Data);
							
							
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					}
				};
				showList.start();
				// Retrieve the stub/proxy for the remote operation from the registry
				
				
			}
		});
		btnNewButton_3.setBounds(162, 150, 117, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		Status = new JTextField();
		Status.setBounds(149, 191, 130, 26);
		frame.getContentPane().add(Status);
		Status.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Request:");
		lblNewLabel_1.setBounds(33, 22, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Status:");
		lblNewLabel_2.setBounds(58, 196, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Port:");
		lblNewLabel_3.setBounds(33, 133, 61, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		Port = new JTextField();
		Port.setBounds(20, 150, 130, 26);
		frame.getContentPane().add(Port);
		Port.setColumns(10);
		
		list = new JList<String>();
		list.setBounds(309, 50, 89, 151);
		frame.getContentPane().add(list);
	}
}
