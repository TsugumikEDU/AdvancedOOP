package pl.blazejdrozd.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.blazejdrozd.quiz.utils.QuizAnswerPacket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class QuizClientController {
    private static final String SERVER_ADDRESS = "127.0.0.1";

    @FXML
    private TextField answer, nick;

    @FXML
    public void onSendAnswerClick() {
        String answerText = answer.getText();
        String nickText = nick.getText();

        if (answerText.isEmpty() || nickText.isEmpty()) {
            return;
        }

        QuizAnswerPacket answerPacket = new QuizAnswerPacket(answerText, nickText);

        try (Socket socket = new Socket(SERVER_ADDRESS, QuizServerController.SERVER_PORT);
             ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream())) {

            outStream.writeObject(answerPacket);
            outStream.flush();

        } catch (IOException e) {
            System.out.println("Can't send answer to server");
        }
    }
}