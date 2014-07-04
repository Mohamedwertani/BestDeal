package tn.edu.esprit.c1info2.codemasters.BestDeal.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReportingServer {

	private static final int port = 125;
	private static List<Socket> connectedClients = new ArrayList<Socket>();
	private static boolean exit = false;

	static class MyThread extends Thread {

		private Socket clientSocket;

		public MyThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			while (clientSocket.isConnected()) {
				try {
					BufferedReader inputStream = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					String message = inputStream.readLine();
					System.out
							.println("[Server] Message received : " + message);
					if (message.equals("dbchanged")) {
						PrintWriter writer = new PrintWriter(
								clientSocket.getOutputStream(), true);
						writer.println("updateview");
						broadCastUpdateView();
					} else if (message.equals("exit")) {
						exit = true;
					}
				} catch (IOException e) {
					System.out.println("Client I/O error");
					return;
				}
			}
		}
	}

	public static void broadCastUpdateView() {
		for (Socket cs : connectedClients) {
			try {
				if (cs.isConnected()) {
					PrintWriter writer = new PrintWriter(cs.getOutputStream(),
							true);
					writer.println("updateview");
				} else {
					connectedClients.remove(cs);
				}
			} catch (IOException ex) {
				System.out
						.println("An error occured while trying to broadcast server message");
			}
		}
	}

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server is listening on port " + port);
			while (!exit) {
				Socket clientSocket = serverSocket.accept();
				if (clientSocket != null) {
					System.out
							.println("A new client has established a connection");
					new MyThread(clientSocket).start();
					connectedClients.add(clientSocket);
				}
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
