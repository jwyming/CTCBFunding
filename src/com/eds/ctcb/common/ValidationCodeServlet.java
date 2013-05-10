package com.eds.ctcb.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Random random = new Random(); 

	private String getRandomNumber(){
		return String.valueOf(random.nextInt(10));
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
			String validationCode = "";
			//
			int width=60, height=20; 
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			Graphics g = image.getGraphics(); 
			//
			g.setColor(new Color(0xDCDCDC)); 
			g.fillRect(0, 0, width, height); 
			//
			g.setColor(Color.black); 
			g.drawRect(0,0,width-1,height-1); 
			//
			g.setFont(new Font("Atlantic Inline",Font.PLAIN,18)); 
			String s;
			for(int i=0;i<4;i++){
				s = getRandomNumber();
				validationCode = validationCode + s;
				g.drawString(getRandomNumber(),10*(i+1),17); 
			}
			//
			Random random = new Random(); 
			for (int i=0;i<20;i++) 
			{ 
			int x = random.nextInt(width); 
			int y = random.nextInt(height); 
			g.drawOval(x,y,0,0); 
			} 
			for (int i=0;i<7;i++){
				int x1 = random.nextInt(width); 
				int y1 = random.nextInt(height); 
				int x2 = random.nextInt(width); 
				int y2 = random.nextInt(height);
				g.drawLine(x1, y1, x2, y2);
			}			
			//
			g.dispose(); 
			try{
				ImageIO.write(image, "JPEG", response.getOutputStream());
				//ImageIO.write(image, "JPEG", new File("a.jpg"));
			}catch(Exception e){
				e.printStackTrace();
			}
			request.getSession().setAttribute("validationCode", validationCode);
		}
}
