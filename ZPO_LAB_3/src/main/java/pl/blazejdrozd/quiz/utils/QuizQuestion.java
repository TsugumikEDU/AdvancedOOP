package pl.blazejdrozd.quiz.utils;

public class QuizQuestion {
    private String question;
    private String answer;

    public QuizQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean validateAnswer(QuizAnswerPacket answer) {
        return answer.getAnswer().equals(this.answer);
    }
}
