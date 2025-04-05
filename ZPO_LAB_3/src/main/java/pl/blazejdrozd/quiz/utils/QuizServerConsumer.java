package pl.blazejdrozd.quiz.utils;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuizServerConsumer implements Runnable {
    private BlockingQueue<QuizAnswerPacket> queue;
    private final ArrayList<QuizQuestion> questions;
    private AtomicBoolean running;
    private int questionIndex;
    private boolean printNextQuestion = false;

    private QuizAnswerPacket answer;

    private TextArea textArea;

    public QuizServerConsumer(ArrayList<QuizQuestion> questions, BlockingQueue<QuizAnswerPacket> queue, TextArea textArea, AtomicBoolean running) {
        this.textArea = textArea;
        this.questions = questions;
        this.queue = queue;
        this.running = running;
        questionIndex = questions.size() - 1;

        if (questionIndex < 0) {
            running.set(false);
        }
    }

    public void nextQuestion() {
        if (running.get()) textArea.appendText(questions.get(questionIndex).getQuestion() + "\n");
    }

    @Override
    public void run() {
        nextQuestion();

        while (this.running.get()) {
            try {
                QuizAnswerPacket answer = this.queue.take();

                QuizQuestion question = this.questions.get(questionIndex);

                String textAreaLog;

                if (question.validateAnswer(answer)) {
                    questionIndex--;
                    printNextQuestion = true;
                    textAreaLog = answer.getNick() + " odpowiedział poprawnie!\n";

                    if (questionIndex < 0) {
                        running.set(false);
                    }
                } else {
                    textAreaLog = "Nadeszła odpowiedź błędna!\n";
                    printNextQuestion = false;
                }

                textArea.appendText(textAreaLog);

                if (printNextQuestion) nextQuestion();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Quiz Server Consumer stopped!");
        textArea.appendText("Quiz ended - server stopped!\n");
    }
}
