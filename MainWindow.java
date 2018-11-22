import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class MainWindow extends JPanel implements Runnable, KeyListener {
	private final int WIDTH = 700;
	private final int HEIGHT = 400;
	Player p1;
	Player p2;
	Ball ball;
	Font pointsFont = new Font("Unispace", Font.PLAIN, 150);
	Font startFont = new Font("Unispace", Font.PLAIN, 30);
	//boolean started;

	public MainWindow() {
		setSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);

		JFrame frame = new JFrame();
		frame.setTitle("IntelliJ IDEA Ultimate");		
		frame.setDefaultLookAndFeelDecorated(true);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		addKeyListener(this);

		
		p1 = new Player(false);
		//p2 = new Player();
		ball = new Ball();

		new Thread(this).start();
	}

	private boolean connected() {
		if(p1 != null && p1.started) {
			return true;
		}

		return false;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH,HEIGHT);

		

		if (!connected()) {
			g.setColor(Color.WHITE);
			g.setFont(startFont);
			g.drawString("Waiting for player", 200, 50);
		} else {

			p1.draw(g);
			//p2.draw(g);
			ball.draw(g);
			g.setColor(Color.GRAY);
			g.drawLine(350, 0, 350, 400);

			g.setFont(pointsFont); 
			
			if (p1.id == 1) {
				if(p1.points < 10) { 
					g.drawString(String.valueOf(p1.points), 130, 235);
				} else {
					g.drawString(String.valueOf(p1.points), 70, 235);
				}
			
			
				if(p1.p2.points < 10) {
					g.drawString(String.valueOf(p1.p2.points), 480, 235);
				} else {
					g.drawString(String.valueOf(p1.p2.points), 430, 235);
				}
			}

			else {
				if(p1.points < 10) { 
					g.drawString(String.valueOf(p1.p2.points), 130, 235);
				} else {
					g.drawString(String.valueOf(p1.p2.points), 70, 235);
				}
			
			
				if(p1.p2.points < 10) {
					g.drawString(String.valueOf(p1.points), 480, 235);
				} else {
					g.drawString(String.valueOf(p1.points), 430, 235);
				}
			}
			
		}
	}

	@Override
	public void run() {
		while (true) {
			if(connected()) {
				ball.move();
				p1.move();
				//p2.move();
				gainPoint();
				if(p1.id == 1) {
					ball.collisionTest(p1,p1.p2);
				}
				else { 
					ball.collisionTest(p1.p2,p1);
				}
			}
			
			repaint();
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void gainPoint() {
		 
		if (ball.x < 0) {
			ball.reset();
			if (p1.id == 1) {
				//p1.p2.points++;
			} else {
				p1.points++;
			}
		} 
				
		else if (ball.x > 680) {
			ball.reset();
			if (p1.id == 1) {
				p1.points++;
			} else {
				//p1.p2.points++;
			}
		}
			

		//System.out.println(p1.points + " : " + p2.points);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			System.out.println("Up pressed!");
			p1.moveUp(true);
		}

		else if (key == KeyEvent.VK_DOWN) {
			System.out.println("Down pressed!");
			p1.moveDown(true);
		}

		// if (key == KeyEvent.VK_W) {
		// 	System.out.println("Up pressed!");
		// 	p2.moveUp(true);
		// }

		// else if (key == KeyEvent.VK_S) {
		// 	System.out.println("Down pressed!");
		// 	p2.moveDown(true);
		// }

		if (key == KeyEvent.VK_ENTER) {
			System.out.println("ENTER");
			//started = true;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {	
		int key = e.getKeyCode();	
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			System.out.println("Slowing down...");
			p1.slowDown();
		}

		// if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
		// 	System.out.println("Slowing down...");
		// 	p2.slowDown();
		// }
	}
}