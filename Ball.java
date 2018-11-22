import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	private final int RADIUS = 20;
	public double x,y, deltaX, deltaY, acc;

	public Ball() {
		x = 350;
		y = 200;
		deltaX = -7;
		deltaY = 2;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)x,(int)y, RADIUS, RADIUS);
	}

	public void collisionTest(Player p1, Player p2) {
		if (x == p1.x+12 && y >= p1.y-15 && y <= p1.y+100) {
			deltaX = -deltaX;
		}

		else if (x == p2.x-13 && y >= p2.y-15 && y <= p2.y+100) {
			deltaX = - deltaX;
		} 


	}

	public void reset() {
		deltaX = -deltaX;
		x = 350;
		y = 200;
	}

	public void move() {
		//System.out.println("[" + (int)x + "," + (int)y + "]");
		
		y += deltaY;
		x += deltaX;
		//System.out.println(y);

		if (y < 0) {
			deltaY = -deltaY;
		} 
		else if (y > 380) {
			deltaY = -deltaY;
		}	

	}

}