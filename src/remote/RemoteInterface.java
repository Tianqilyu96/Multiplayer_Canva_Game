package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote{

	public void createWhiteBoard() throws RemoteException;
	public byte[] getBoardImage() throws RemoteException;
	public void loadBoardImage(byte[] imageBytes) throws RemoteException;
	public byte[] joinWhiteBoard() throws RemoteException;
	public void openWhiteBoard(byte[] imageBytes) throws RemoteException;
	public boolean canSynchronize() throws RemoteException;
	public void changeSynchronize(boolean bool) throws RemoteException;
	public void approveRequest() throws RemoteException;
	public int returnApprove() throws RemoteException;
	public int getRequest() throws RemoteException;
	public void setRequest(int require)throws RemoteException;
	public int getApprove() throws RemoteException;
	public void setApprove(int approve) throws RemoteException;
	public void addUser(String username) throws RemoteException;
	public void removeUser(String username) throws RemoteException;
	public ArrayList<String> getDialogue() throws RemoteException;
	public void updateDialogue(String dialogue, String username) throws RemoteException;
	public ArrayList<String> getUserList() throws RemoteException;
	
	
}
