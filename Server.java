import java.io.*;
import java.net.*;

public class Server {
	static ServerSocket ss;
	static PrintWriter[] players;

	public static void main(String[] args) {
		try {
			ss = new ServerSocket(4040);
			players = new PrintWriter[2];

			System.out.println(ss.getInetAddress());

			Socket player1 = ss.accept();
			System.out.println("Player1 connected");
			players[0] = new PrintWriter(player1.getOutputStream());
			PlayerHandler ph1 = new PlayerHandler(player1);
			new Thread(ph1).start();

			Socket player2 = ss.accept();
			System.out.println("Player2 connected");
			players[1] = new PrintWriter(player2.getOutputStream());
			PlayerHandler ph2 = new PlayerHandler(player2);
			new Thread(ph2).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class PlayerHandler implements Runnable {
	Socket player;
	BufferedReader in;
	PrintWriter out;
	double x, y;
	static int playerID = 1;
	int id, points;

	public PlayerHandler(Socket player) {
		this.player = player;
		try {
			in = new BufferedReader(new InputStreamReader(player.getInputStream()));
			out = new PrintWriter(player.getOutputStream());

			id = playerID;
			out.println((playerID++));
			out.flush();
			if (playerID > 2) {
				for(PrintWriter w : Server.players) {
					System.out.println("fooooo");
					System.out.println(w);
					w.println(true);
					w.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {	
		while(true) {
			try {
				x = Double.parseDouble(in.readLine());
				y = Double.parseDouble(in.readLine());
				points = Integer.parseInt(in.readLine());
				// for(PrintWriter w : Server.players) {
				// 	if(!w.equals(this.out)) {
				// 		w.println(x);
				// 		w.println(y);
				// 		w.flush();
				// 	}
				// }

				if (id == 1) {
					Server.players[1].println(x);
					Server.players[1].println(y);
					Server.players[1].println(points);
					Server.players[1].flush();
				} else {
					Server.players[0].println(x);
					Server.players[0].println(y);
					Server.players[0].println(points);
					Server.players[0].flush();
				}

			} catch(NumberFormatException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}