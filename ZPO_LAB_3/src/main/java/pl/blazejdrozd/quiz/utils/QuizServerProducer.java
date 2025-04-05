package pl.blazejdrozd.quiz.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuizServerProducer implements Runnable {
    private BlockingQueue<QuizAnswerPacket> anserQueue;
    private ServerSocket serverSocket;
    private AtomicBoolean running;
    private int port;

    public QuizServerProducer(BlockingQueue<QuizAnswerPacket> anserQueue, int serverPort, AtomicBoolean running) {
        this.anserQueue = anserQueue;
        this.port = serverPort;
        this.running = running;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Server started on port " + this.port);

        while (running.get() && !this.serverSocket.isClosed()) {
            try (Socket socket = serverSocket.accept();
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());

                QuizAnswerPacket answerPacket = (QuizAnswerPacket) inputStream.readObject();

                anserQueue.put(answerPacket);

                System.out.println("Packet received and added to queue: " + answerPacket);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Quiz Server Producer stopped!");
    }
}
