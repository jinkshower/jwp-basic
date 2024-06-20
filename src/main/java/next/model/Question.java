package next.model;

import java.util.Date;
import java.util.Objects;

public class Question {
    private long questionId;
    private String writer;
    private String title;
    private String contents;
    private Date createdDate;
    private int countOfAnswer;

    public Question (String writer, String title, String contents) {
        this(0, writer, title, contents, new Date(), 0);
    }

    public Question(final long questionId, final String writer, final String title,
        final String contents, final Date createdDate,
        final int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public void update(Question updateQuestion) {
        this.writer = updateQuestion.writer;
        this.title = updateQuestion.title;
        this.contents = updateQuestion.contents;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Question question = (Question) object;
        return questionId == question.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, writer, title, contents, createdDate, countOfAnswer);
    }

    @Override
    public String toString() {
        return "Question{" +
            "questionId=" + questionId +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", createdDate=" + createdDate +
            ", countOfAnswer=" + countOfAnswer +
            '}';
    }
}
