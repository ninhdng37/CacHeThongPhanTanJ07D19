package server_client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Random;

public class MyFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel timeLabel;
	private JLabel dayLabel;
	private JLabel dateLabel;
	private JButton button;
	private String time;
	Random rd = new Random();
	private int hours = rd.nextInt(24), minute = rd.nextInt(60), second = rd.nextInt(60);
	
	
	MyFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("My Clock Program");
		this.setLayout(new FlowLayout());
		this.setSize(350, 200);
		this.setResizable(false);
		
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		
		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
		timeLabel.setForeground(new Color(0x00FF00));
		timeLabel.setBackground(Color.black);
		timeLabel.setOpaque(true);
		
		dayLabel = new JLabel();
		dayLabel.setFont(new Font("Ink Free", Font.PLAIN, 35));
		
		
		dateLabel = new JLabel();
		dateLabel.setFont(new Font("Ink Free", Font.PLAIN, 35));
		
		button = new JButton();
		button.setBounds(200, 100, 100, 50);
		button.setText("Request Time");
		button.addActionListener(e -> updateTime());
		
		this.add(timeLabel);
		this.setVisible(true);
		this.add(button);
		setTime();
	}
	
	public void setTime() {

		String hours = new String();
		String minute = new String();
		String second = new String();
		
		
		while(true) {
			if(this.hours < 10)
				hours = "0" + Integer.toString(this.hours);
			else hours = Integer.toString(this.hours);
			if(this.minute < 10)
				minute = "0" + Integer.toString(this.minute);
			else minute = Integer.toString(this.minute);
			if(this.second < 10)
				second = "0" + Integer.toString(this.second);
			else second = Integer.toString(this.second);
			time = hours + ": " + minute + ": " + second;
			timeLabel.setText(time);
			
			try {
				if(this.second < 60) this.second++;
				else {
					this.second = 0;
					if(this.minute < 60) this.minute ++;
					else {
						this.minute = 0;
						if (this.hours < 24) this.hours++;
						else this.hours = 0;
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateTime() {
		Socket s;
		try {
			s = new Socket("192.168.1.22",9999);
			DataInputStream din=new DataInputStream(s.getInputStream());  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			
			String str="",str2="";
			str = "Request Time"; 
			dout.writeUTF(str);  
			dout.flush();
			int waitingTime = 0;
			while(waitingTime <= 10){
				
				str2=din.readUTF();
				if(!str2.isEmpty()) {
					System.out.println(str2);
					str2.replace(" ", "");
					String [] unitsOfClock = str2.split(":");
					this.hours = Integer.parseInt(unitsOfClock[0]);
					this.minute = Integer.parseInt(unitsOfClock[1]);
					this.second = Integer.parseInt(unitsOfClock[2]);
					time = str2;
					break;
				}
				else {
					Thread.sleep(1000);
					waitingTime++;
				}
			}
			if (waitingTime > 10) System.out.println("Server do not  response!");
			dout.close();  
			s.close(); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't connect to server");
//			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
	}
	
}
