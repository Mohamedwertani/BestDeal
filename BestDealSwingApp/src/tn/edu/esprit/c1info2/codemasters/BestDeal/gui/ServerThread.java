package tn.edu.esprit.c1info2.codemasters.BestDeal.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket socket;
	private Runnable task;

	public ServerThread(Socket socket, Runnable task) {
		this.socket = socket;
		this.task = task;
	}

	public void run() {
		while (true) {
			if (socket.isConnected()) {
				try {
					System.out.println("Listening on port " + socket.getPort() + " for incoming messages");
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String msg = reader.readLine();
					System.out.println("[Client Id = " + socket.toString() + "] Message received: " + msg);
					if (msg.equals("updateview")) {
						task.run();
					}
				} catch (IOException e) {
					System.out.println("I/O error while trying to read messages from server");
					return;
				}
			} else {
				Thread.yield();
			}
		}
	}

}
