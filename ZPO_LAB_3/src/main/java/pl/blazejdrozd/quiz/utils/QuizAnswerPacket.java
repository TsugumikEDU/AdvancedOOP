package pl.blazejdrozd.quiz.utils;

import java.io.Serializable;

public class QuizAnswerPacket implements Serializable {
    private final String answer;
    private final String nick;

    public QuizAnswerPacket(String answer, String nick) {
        this.answer = answer;
        this.nick = nick;
    }

    public String getAnswer() {
        return answer;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public String toString() {
        return "QuizAnswerPacket [answer=" + answer + ", nick=" + nick + "]";
    }
}
