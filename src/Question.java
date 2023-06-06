import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Question {
    private String question;
    private String [] choices;
    private int answer;
    private String answerToString;
    private int answerGiven;
    private String answerGivenToString;

    private int includes_image;
    private BufferedImage [] choicesImages;
    private BufferedImage [] questionImages;

    public Question(String question, String[] choices, int answer,int includes_image,String questionImage) throws IOException {

        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.includes_image = includes_image;
        answerGiven = -1;

        if (this.includes_image == 1){
            choicesImages = new BufferedImage[4];
            for (int i = 0; i < choicesImages.length; i++){
                choicesImages[i] = ImageIO.read(getClass().getResourceAsStream(choices[i]));
            }
        }

        else if (this.includes_image == 2){
            questionImages = new BufferedImage[1];
            questionImages[0] = ImageIO.read(getClass().getResourceAsStream(questionImage));
        }

        if (answer == 0){
            answerToString = "A";
        }
        if (answer == 1){
            answerToString = "B";
        }
        if (answer == 2){
            answerToString = "C";
        }
        if (answer == 3){
            answerToString = "D";
        }
        if (answer == -1){
            answerToString = "Blank";
        }
    }

    public int getAnswerGiven() {
        return answerGiven;
    }

    public void setAnswerGiven(int answerGiven) {
        this.answerGiven = answerGiven;
    }

    public String getAnswerGivenToString() {
        return answerGivenToString;
    }

    public void setAnswerGivenToString(String answerGivenToString) {
        this.answerGivenToString = answerGivenToString;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getAnswerToString() {
        return answerToString;
    }

    public void setAnswerToString(String answerToString) {
        this.answerToString = answerToString;
    }

    public int getIncludes_image() {
        return includes_image;
    }

    public void setIncludes_image(int includes_image) {
        this.includes_image = includes_image;
    }

    public BufferedImage[] getChoicesImages() {
        return choicesImages;
    }

    public void setChoicesImages(BufferedImage[] choicesImages) {
        this.choicesImages = choicesImages;
    }

    public BufferedImage[] getQuestionImages() {
        return questionImages;
    }

    public void setQuestionImages(BufferedImage[] questionImages) {
        this.questionImages = questionImages;
    }
}
