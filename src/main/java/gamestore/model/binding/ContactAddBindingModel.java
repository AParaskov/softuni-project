package gamestore.model.binding;

import org.hibernate.validator.constraints.Length;

public class ContactAddBindingModel {
    private String userName;
    private String question;

    public ContactAddBindingModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Length(min = 2, message = "Question must be more than two characters!")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
