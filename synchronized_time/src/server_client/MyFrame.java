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
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Calendar;
import java.util.Random;

public class MyFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel timeLabel;
	
	private JButton button;
	private String time;
	Random rd = new Random();
	private int hours = rd.nextInt(24), minute = rd.nextInt(59), second = rd.nextInt(59);
	//rd.nextInt(24)
	
	MyFrame(String tittle){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(tittle);
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
		
		button = new JButton();
		button.setBounds(200, 100, 100, 50);
		button.setText("Request Time");
		button.addActionListener(e -> updateTime());
		
		this.add(timeLabel);
		this.setVisible(true);
		if (tittle.equals("CLIENT")) {
			this.add(button);
			setTimeClient();
		}
		else setTimeServer();
	}
	
	public void setTimeServer() {
		SimpleDateFormat timeFormat = new  SimpleDateFormat("HH:mm:ss");
		while(true) {
		  time = timeFormat.format(Calendar.getInstance().getTime());
		  timeLabel.setText(time);
		  try {
		   Thread.sleep(1000);
		  } catch (InterruptedException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		}
	}
	public void setTimeClient() {

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
				if(this.second < 59) this.second++;
				else {
					this.second = 0;
					if(this.minute < 59) this.minute ++;
					else {
						this.minute = 0;
						if (this.hours < 23) this.hours++;
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
			
			while(true){
				
				str2=din.readUTF();
				if(!str2.isEmpty()) {
					System.out.println(str2);
					str2.replace(" ", "");
					String [] unitsOfClock = str2.split(":");
					this.hours = Integer.parseInt(unitsOfClock[0]);
					this.minute = Integer.parseInt(unitsOfClock[1]);
					this.second = Integer.parseInt(unitsOfClock[2]);
					this.time = str2;
					break;
				}	
			}
			dout.close();  
			s.close(); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JFrame frame = new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, "Can't connect to server! Server is not operating now.", "Connect error", JOptionPane.ERROR_MESSAGE);
		} 
	}	
}