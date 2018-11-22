import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.net.*;

public class Player implements Runnable {

	Player p2;
	BufferedReader in;
	PrintWriter out;
	double x, y, deltaY, acc;
	double enemyX, enemyY;
	boolean up, down, started, enemy;
	int points, id;
	Socket socket;

	public Player(boolean enemy) {
		if (!enemy) {
			try {
				socket = new Socket("localhost", 4040);

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
				id = Integer.parseInt(in.readLine());
				started = Boolean.parseBoolean(in.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(id);
			y = 150;
			deltaY = 5;
			acc = 0.9;
			p2 = new Player(true);
			if (id == 1) {
				p2.id = 2;
				this.x = 30;
			} else if (id == 2) {
				p2.id = 1;
				this.x = 650;
			}
			new Thread(this).start();
		} 

		this.enemy = enemy;
	}

	@Override
	public void run() {
		while(true) {
			try {
				out.println(x);
				out.println(y);
				out.println(points);
				out.flush();

				p2.x = Double.parseDouble(in.readLine());
				p2.y = Double.parseDouble(in.readLine());
				p2.points = Integer.parseInt(in.readLine());

				//System.out.println("[" + enemyX + "," + enemyY + "]");
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x,(int)y,20,100);
		g.fillRect((int)p2.x, (int)p2.y, 20, 100);
	}

	public void move() {
		if (up) {
			deltaY -= 2;
		} else if (down) {
			deltaY += 2;
		} else if (!up && !down) {
			deltaY *= acc;
		}

		if (deltaY >= 5) 
			deltaY = 5;
		else if (deltaY <= -5) 
			deltaY = -5;

		y += deltaY;

		if (y < 0) {
			y = 0;
		} else if (y > 300) {
			y = 300;
		}

	}

	public void moveUp(boolean moveUp) {
		up = moveUp;
	} 

	public void moveDown(boolean moveDown) {
		down = moveDown;
	}

	public void slowDown() {
		down = false;
		up = false;
	}
}