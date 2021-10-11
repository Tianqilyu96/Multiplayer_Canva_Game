package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class BoardMonitor extends JFrame{
	int canvasWidth = 1024;
	int canvasHeight = 1024;
	int boardWidth = 1029;
	int boardHeight = 757;
	public boolean synchronize = true;
	public boolean openTriger = false;
	boolean open = false;
	JLayeredPane lp = new JLayeredPane();
	BufferedImage image = new BufferedImage( canvasWidth, canvasHeight, BufferedImage.TYPE_INT_BGR);// Size and type of the image
	Graphics gs = image.getGraphics();
	Graphics2D g = (Graphics2D) gs;
	MyCanvas canvas = new MyCanvas();
	
	int x1;
	int y1;
	int x2;
	int y2;
	int x = -1;
	int y = -1;
	boolean isActive = true;
	
    public BoardMonitor() {
		
		setResizable(false);
		setBounds( 500, 100, boardWidth, boardHeight);
		this.isActive = false;
		
		setTitle("Server Whiteboard Monitor. ");
		init();

	}
    public BoardMonitor(BufferedImage importedImage) {
		setResizable(false);
		setBounds( 500, 100, boardWidth, boardHeight);		
		setTitle("Server Whiteboard Monitor ");
		this.image = importedImage;
		init2(importedImage);
//		publicInit();
//		addListener();
	}
    
    public void init() {
		g.setColor(Color.white);// set the color for drawing
		g.fillRect(0, 0, canvasWidth, canvasHeight);//set background
		g.setColor(Color.black);//set the color for drawing
		canvas.setImage(image);//set background color of canvas
		Container s = getContentPane();
		lp = new JLayeredPane();
		canvas.setBounds(0, 0, canvasWidth, canvasHeight);
		lp.add(canvas, new Integer(100));
		s.add(lp,BorderLayout.CENTER);
	}
	
	public void init2(BufferedImage image2) {
		//clear();
		gs = image2.getGraphics();
		g = (Graphics2D) gs;		
		g.setColor(Color.white);
		g.setColor(Color.black);
		canvas.setImage(image2);
		Container s = getContentPane();
		lp = new JLayeredPane();
		canvas.setBounds(0, 0, canvasWidth, canvasHeight);
		lp.add(canvas, new Integer(100));
		s.add(lp,BorderLayout.CENTER);
        open = true;
	}
	
	public  void setCanvas(BufferedImage importedImage) {		
		
		
			this.image=importedImage;
			this.gs=this.image.getGraphics();
			this.g=(Graphics2D)this.gs;
			this.g.setColor(Color.white);
			this.g.setColor(Color.black);
			this.canvas.setImage(this.image);
			this.canvas.repaint();

		
	}
	public boolean setActive(boolean status) {
		this.isActive = status;
		return this.isActive;
	}
	

	public boolean getActive() {
		return this.isActive;
	}
	


}
