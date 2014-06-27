package tn.edu.esprit.c1info2.codemasters.BestDeal.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReportingServer {

	private static final int port = 125;
	private static List<Socket> connectedClients = new ArrayList<Socket>();

	static class MyThread extends Thread {

		private Socket clientSocket;

		public MyThread(Socket clientSocket) {
			super();
			this.clientSocket = clientSocket;
		}

		public void run() {
			while (clientSocket.isInputShutdown() == false) {
				DataInputStream inputStream;
				try {
					inputStream = new DataInputStream(clientSocket.getInputStream());
					String message = inputStream.readUTF();
					if (message.equals("dbchanged")) {
						PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
						writer.write("updateview");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				System.out.println("Server is listening on port " + port);
				Socket clientSocket = serverSocket.accept();
				System.out.println("A new client has established a connection");
				new MyThread(clientSocket).run();
				for (Socket cs : connectedClients) {
					try {
						PrintWriter writer = new PrintWriter(cs.getOutputStream());
						writer.write("updateview");
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				connectedClients.add(clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
