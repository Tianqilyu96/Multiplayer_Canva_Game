package server;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import remote.RemoteInterface;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;

public class RemoteImplementation extends UnicastRemoteObject implements RemoteInterface{
	private ArrayList<String> clientList;
	private ArrayList<String>  dialogueList;
	private BoardMonitor drawingBoard;
	
	protected RemoteImplementation() throws RemoteException {
		this.clientList = new ArrayList<String>();
		this.dialogueList = new ArrayList<String>();
	}
	
    public static int request=0;
	
	public int getRequest() throws RemoteException{
		return request;
	}
	public void setRequest(int require)throws RemoteException {
		this.request = require;	
	}
	
	public static int approve=0;
	
	public int getApprove() throws RemoteException{
		return approve;
	}
	public void setApprove(int approve) throws RemoteException {
		this.approve = approve;
	}
	
	public void addUser(String username) throws RemoteException {
		this.clientList.add(username);
		
		System.out.println("Users:");
		for(String name:this.clientList) {
            System.out.print(name+ " ");
        }
		 System.out.println();
	}
	
	public void removeUser(String username) throws RemoteException {
		this.clientList.remove(username);
		
		System.out.println("Current users:");
		for(String name:this.clientList) {
            System.out.print(name+ " ");
        }
		 System.out.println();
		
	}
	
	public ArrayList<String> getDialogue() {
		return this.dialogueList;
	}
	
	public void updateDialogue(String dialogue, String username) throws RemoteException {
		this.dialogueList.add("["+username+"]: "+dialogue);
	}
	
	public void createWhiteBoard() throws RemoteException{

		try {
			// Determine if the canvas has been created before
			if(this.drawingBoard != null) {

				this.drawingBoard.dispose();
				this.drawingBoard = new BoardMonitor();
				this.drawingBoard.setTitle("Board Monitor");
				this.drawingBoard.setVisible(true);				
			}
			else {
				this.drawingBoard = new BoardMonitor();
				this.drawingBoard.setTitle("Board Monitor");
				this.drawingBoard.setVisible(true);		
			}	
	
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Create White Board Failed...");
		}
	}
	
	public byte[] getBoardImage() throws RemoteException{	
		try {
			// Convert the buffered image to bytes and return
			BufferedImage boardImg = this.drawingBoard.image;
			byte[] imageBytes = null;			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(boardImg, "jpg", bos);
			bos.flush();
			imageBytes = bos.toByteArray();
			bos.close();
			
			return imageBytes;
			
			
		} catch (IOException e) {
			System.out.println("Get image failed");
			e.printStackTrace();
			return null;
		}
	}
	public void loadBoardImage(byte[] imageBytes) throws RemoteException{	
		try {
			
			InputStream inputImage = new ByteArrayInputStream(imageBytes);
			BufferedImage image = ImageIO.read(inputImage);
			inputImage.close();			
		
			this.drawingBoard.setCanvas(image);
			
		} catch (IOException e) {
			System.out.println("Load image failed");
			e.printStackTrace();
		}
	}
	
	public byte[] joinWhiteBoard() throws RemoteException{
		try {
			if(this.drawingBoard != null) {
				byte[] imageBytes = null;			
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(this.drawingBoard.image, "jpg", bos);
				bos.flush();
				imageBytes = bos.toByteArray();
				bos.close();
				
				return imageBytes;
			}
			else {
				return null;

			}
		} catch (Exception e) {
			System.out.println("Can not join white board...");
			e.printStackTrace();
			return null;
		}	
	}
	
	public void openWhiteBoard(byte[] imageBytes) throws RemoteException{
		try {
			
			InputStream in = new ByteArrayInputStream(imageBytes);
			BufferedImage image = ImageIO.read(in);
			in.close();	
			
			if(this.drawingBoard != null) {
				this.drawingBoard.synchronize = false;
				this.drawingBoard.openTriger = true;
				
				
				this.drawingBoard.setCanvas(image);
	
	
				this.drawingBoard.openTriger = false;
				this.drawingBoard.synchronize = true;
			}
			else {
				this.drawingBoard = new BoardMonitor(image);
				this.drawingBoard.setTitle("Board Monitor");
				this.drawingBoard.setVisible(true);
				
				this.drawingBoard.openTriger = false;
				this.drawingBoard.synchronize = true;
			}
		
			
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("Can not open white board...");
		}
	}
	
	public boolean canSynchronize() throws RemoteException{
		return this.drawingBoard.synchronize;
	}
	

	
	public void changeSynchronize(boolean bool) throws RemoteException{
		this.drawingBoard.synchronize = bool;
	}
	
    public void approveRequest() throws RemoteException{
    	request = 1;
    }
	
	public int returnApprove() throws RemoteException{
		while(getRequest()==1){
	       System.out.println("");
		}
		return getApprove();
	}
	public ArrayList<String> getUserList() {
		return this.clientList;
	}
	
	
}
