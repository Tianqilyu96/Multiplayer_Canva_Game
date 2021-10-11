package client;

import java.awt.*;


import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.RepaintManager;
import javax.xml.crypto.Data;


import remote.RemoteInterface;


import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import javax.swing.JList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ClientUI {

	private JFrame frame;
	private JTextField Status;
	private JTextField Name;
	private JTextField Port;
	public RemoteInterface remoteInterface;
	private static Client client;
	private Thread upload;
	private Thread download;
	private Thread showList;
	private JTextField Joined;
	JList<String> Userlist ;
	Graphics g;
	private String shape = "draw";
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 client = new Client("user", "2000", "bbc");
				try {
					ClientUI window = new ClientUI();
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
	public ClientUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					client.remoteInterface.removeUser(Name.getText());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.setBounds(850, 850, 850, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		MyCanvas canvas = new MyCanvas();
		canvas.setBackground(Color.WHITE);
		BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		canvas.setImage(bi);
		
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
              public void mouseDragged(MouseEvent e) {
            	  g = canvas.getGraphics();
            	  if(shape == "Circle") {
            		    g.setColor(Color.YELLOW); 
    				  
				        // get X and y position 
				        int x, y; 
				        x = e.getX(); 
				        y = e.getY(); 
				  
				        // draw a Oval at the point where mouse is moved 
				        g.fillOval(x, y, 50, 50);
            	  }
            	  else if (shape == "draw") {
            		    g.setColor(Color.white); 
    				  
				        // get X and y position 
				        int x, y; 
				        x = e.getX(); 
				        y = e.getY(); 
				  
				        // draw a Oval at the point where mouse is moved 
				        g.fillOval(x, y, 5, 5);
				        
            	  }
            	  else if (shape =="Rec") {
            		    g.setColor(Color.red); 
    				  
				        // get X and y position 
				        int x, y; 
				        x = e.getX(); 
				        y = e.getY(); 
				  
				        // draw a Oval at the point where mouse is moved 
				        g.fillRect(x, y, 50, 50);
            	  }else {
            		  
            	  }
              }
		});
		
		canvas.setBounds(72, 83, 452, 420);
		frame.getContentPane().add(canvas);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(753, 232, -133, 209);
		frame.getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Request Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = Name.getText().trim().toString();
				String port = Port.getText().trim().toString();
				try {
				Registry registry = LocateRegistry.getRegistry("localhost", Integer.parseInt(port));
				client.remoteInterface = (RemoteInterface) registry.lookup("RemoteOperation");
				Status.setText("Connected");
				
				} catch (Exception e2) {
					e2.printStackTrace();
					Status.setText("Connect Failed");
				}

				
				
			}
		});
		btnNewButton.setBounds(158, 631, 174, 29);
		frame.getContentPane().add(btnNewButton);
		
		Status = new JTextField();
		Status.setBounds(341, 566, 130, 26);
		frame.getContentPane().add(Status);
		Status.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Status:");
		lblNewLabel.setBounds(271, 571, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Rectangle");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				shape = "Rec";
				
			}
			
		});

		btnNewButton_1.setBounds(598, 26, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Circle");
		btnNewButton_2.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				shape = "Circle";
				
				}
				
			
		});
		btnNewButton_2.setBounds(598, 67, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Line");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				g.setColor(Color.white);
				g.drawLine(10, 10, 20, 20);
            
			}
		});
		btnNewButton_3.setBounds(598, 108, 117, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_1 = new JLabel("Users:");
		lblNewLabel_1.setBounds(610, 299, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		Name = new JTextField();
		Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		Name.setBounds(119, 566, 130, 26);
		frame.getContentPane().add(Name);
		Name.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Yourname:");
		lblNewLabel_2.setBounds(40, 571, 67, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_4 = new JButton("Draw");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				shape = "draw";
				
			}
		});

		btnNewButton_4.setBounds(598, 149, 117, 29);
		frame.getContentPane().add(btnNewButton_4);
		
		Port = new JTextField();
		Port.setBounds(341, 593, 130, 26);
		frame.getContentPane().add(Port);
		Port.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Port:");
		lblNewLabel_4.setBounds(261, 599, 61, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		JButton btnNewButton_5 = new JButton("Join");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				upload = new Thread() {
					@Override
					public void run() {
						try {
							while(true) {
								if(client.remoteInterface.canSynchronize()==true) {
									if(canvas.getGraphics()!=null) {
										
										
										BufferedImage bo = imageToBufferedImage(canvas.getImage());
										byte[] imageBytes = null;			
											ByteArrayOutputStream bos = new ByteArrayOutputStream();
											ImageIO.write(bi, "jpg", bos);
											bos.flush();
											imageBytes = bos.toByteArray();
											bos.close();
											
											client.remoteInterface.loadBoardImage(imageBytes);
											System.out.println("uploaded..");
											while(client.remoteInterface.canSynchronize() != true) {
												Thread.sleep(50);
											}
										
										
									}
								}
							}
						} catch (Exception e2) {
						   e2.printStackTrace();
						}
					}
				};
				upload.start();
				
				download = new Thread() {
					@Override
					public void run() {
						try {
							while(true) {
								if(client.remoteInterface.canSynchronize() == true) {
									Thread.sleep(200);
									InputStream in = new ByteArrayInputStream(client.remoteInterface.getBoardImage());
									BufferedImage getImage = ImageIO.read(in);
									in.close();
									canvas.setImage(getImage);
									
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				};
				download.start();
				Joined.setText("Joined");
				String user = Name.getText();
				try {
					client.remoteInterface.addUser(user);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				showList = new Thread(){
					@Override
					public void run(){
					while(true) {
						try {
							ArrayList<String> clientList;
							clientList = client.remoteInterface.getUserList();
							String[] Data = (String[])clientList.toArray(new String[0]);
							Userlist.setListData(Data);
							
							
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					}
				};
				showList.start();
				
				
			}
		});
		btnNewButton_5.setBounds(34, 686, 117, 29);
		frame.getContentPane().add(btnNewButton_5);
		
		Joined = new JTextField();
		Joined.setBounds(202, 686, 130, 26);
		frame.getContentPane().add(Joined);
		Joined.setColumns(10);
		
		Userlist = new JList<String>();
		Userlist.setBounds(610, 343, 131, 219);
		frame.getContentPane().add(Userlist);
	
		
		
		
		Joined = new JTextField();
		Joined.setBounds(202, 686, 130, 26);
		frame.getContentPane().add(Joined);
		Joined.setColumns(10);
		
		Userlist = new JList<String>();
		Userlist.setBounds(610, 343, 131, 219);
		frame.getContentPane().add(Userlist);
	}
	  public static BufferedImage imageToBufferedImage(Image im) {
		     BufferedImage bi = new BufferedImage
		        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
		     Graphics bg = bi.getGraphics();
		     bg.drawImage(im, 0, 0, null);
		     bg.dispose();
		     return bi;
		  }
}
