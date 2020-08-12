package gamestore.model.service;

public class ContactServiceModel extends BaseServiceModel {
    private String userName;
    private String question;

    public ContactServiceModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
