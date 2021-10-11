package client;

import remote.RemoteInterface;

public class Client {
	private String host;
	private String port;
	public String username;	
	public RemoteInterface remoteInterface;
	
	public Client(String hostname, String port, String username) {
		// Get the host name and port number
		this.host = hostname;
		this.port = port;
		this.username = username;
		
	}
	
	
}
