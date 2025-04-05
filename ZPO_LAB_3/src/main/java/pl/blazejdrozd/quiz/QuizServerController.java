package pl.blazejdrozd.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import pl.blazejdrozd.quiz.utils.QuizAnswerPacket;
import pl.blazejdrozd.quiz.utils.QuizQuestion;
import pl.blazejdrozd.quiz.utils.QuizServerConsumer;
import pl.blazejdrozd.quiz.utils.QuizServerProducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.*;

public class QuizServerController {
    private AtomicBoolean running = new AtomicBoolean(true);
    public static final int SERVER_PORT = 5025;
    private BlockingQueue<QuizAnswerPacket> queue = new ArrayBlockingQueue<QuizAnswerPacket>(2);
    private ArrayList<QuizQuestion> questions = new ArrayList<QuizQuestion>();

    @FXML
    private TextArea textAreaLog;


    public void initialize() {
        String filePath = "questions.json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String question = "#" + (i + 1) + ": " + obj.getString("question");
                String answer = obj.getString("answer");

                questions.add(new QuizQuestion(question, answer));
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        textAreaLog.setEditable(false);

        QuizServerProducer producer = new QuizServerProducer(queue, SERVER_PORT, running);
        QuizServerConsumer consumer = new QuizServerConsumer(questions, queue, textAreaLog, running);

        Thread producer_thread = new Thread(producer);
        Thread consumer_thread = new Thread(consumer);

        producer_thread.start();
        consumer_thread.start();
    }
}
