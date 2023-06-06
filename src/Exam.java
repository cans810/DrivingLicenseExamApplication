
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Exam extends JFrame{
    private JLabel questionLabel;
    private JRadioButton [] choices = new JRadioButton[4];
    private JLabel [] choicesArrayForImages = new JLabel[4];
    private ImageIcon [] choicesImages = new ImageIcon[4];
    private ImageIcon [] questionImages = new ImageIcon[4];
    private JButton nextQuestionButton;
    private JButton finishExamButton;
    private JButton bookmarkQuestionButton;
    private JButton previousWindowButton;
    private ButtonGroup bg;

    int current;
    int currentBookmarked;
    private String examNum;
    private ArrayList<Question> questions;
    private int points;

    private ExamListener examListener = new ExamListener();

    private ParentWindow examsWindow;
    private BookmarkedExam bookmarkedExam;
    private ExamAnswerSheet answerSheet;
    private int answerGivenToFirstQuestion;

    public Exam(String examSelected,ParentWindow examsWindow){
        this.examNum = examSelected;
        this.examsWindow = examsWindow;
        current = 0;
        currentBookmarked = 0;
        points = 0;

        bookmarkedExam = new BookmarkedExam(examsWindow);
        bookmarkedExam.setVisible(false);

        setTitle("Driving License Exams");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        try {
            Image img= ImageIO.read(getClass().getResource("/images/logo.jpg"));
            this.setIconImage(img);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        setPreferredSize(new Dimension(examsWindow.getWidth(), examsWindow.getHeight()));
        pack();
        setLocationRelativeTo(null);

        questionLabel=new JLabel();

        bg = new ButtonGroup();

        for (int i = 0; i< choicesImages.length; i++){
            choicesImages[i] = new ImageIcon();
        }

        for (int i = 0; i< choicesImages.length; i++){
            questionImages[i] = new ImageIcon();
        }

        for (int i=0;i<4;i++){
            choicesArrayForImages[i] = new JLabel();
            add(choicesArrayForImages[i]);
        }

        for(int i=0;i<4;i++)
        {
            choices[i]=new JRadioButton();
            choices[i].addActionListener(examListener);
            add(choices[i]);
            bg.add(choices[i]);
        }

        nextQuestionButton = new JButton("Next");
        finishExamButton = new JButton("Finish");
        bookmarkQuestionButton = new JButton("Bookmark");

        questionLabel.setBounds(260,15,450,290);
        questionLabel.setFont(new Font("Aharoni",Font.BOLD,15));

        choices[0].setBounds(255,260,400,70);
        choicesArrayForImages[0].setBounds(310,260,400,90);
        choices[0].setFont(new Font("Aharoni",Font.BOLD,15));
        choices[1].setBounds(255,350,400,70);
        choicesArrayForImages[1].setBounds(310,370,400,90);
        choices[1].setFont(new Font("Aharoni",Font.BOLD,15));
        choices[2].setBounds(255,440,400,70);
        choicesArrayForImages[2].setBounds(310,480,400,90);
        choices[2].setFont(new Font("Aharoni",Font.BOLD,15));
        choices[3].setBounds(255,530,400,70);
        choicesArrayForImages[3].setBounds(310,590,400,90);
        choices[3].setFont(new Font("Aharoni",Font.BOLD,15));

        nextQuestionButton.setBounds(170,700,150,60);
        nextQuestionButton.setFont(new Font("Aharoni",Font.BOLD,20));
        finishExamButton.setBounds(680,700,150,60);
        finishExamButton.setFont(new Font("Aharoni",Font.BOLD,20));
        bookmarkQuestionButton.setBounds(430,700,150,60);
        bookmarkQuestionButton.setFont(new Font("Aharoni",Font.BOLD,20));

        previousWindowButton = new JButton("<<");
        previousWindowButton.setBounds(20,30,60,30);
        previousWindowButton.setFont(new Font("Aharoni",Font.BOLD,20));
        previousWindowButton.addActionListener(examListener);

        finishExamButton.setEnabled(false);

        nextQuestionButton.addActionListener(examListener);
        finishExamButton.addActionListener(examListener);
        bookmarkQuestionButton.addActionListener(examListener);

        add(questionLabel);
        add(nextQuestionButton);
        add(finishExamButton);
        add(bookmarkQuestionButton);
        add(previousWindowButton);

        setLayout(null);
        setVisible(true);
    }

    public void setQuestions(String exam){

        String url = "jdbc:sqlite:C:\\Users\\Can\\Desktop\\SQLite\\driverlicensequestions.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from questions");

            int questionNum = 0;
            int questionNumDisplay;

            questions = new ArrayList<>();

            while(rs.next()){
                setTitle(examNum);
                questionNumDisplay = questionNum + 1;

                if (exam.equals("Exam 1") && rs.getString("exam_num").equals("1")) {
                    if (rs.getString("includes_image").equals("1")){

                        Question question = new Question(questionNumDisplay + ")" + rs.getString("question_text")
                                ,new String[]{rs.getString("choice_a"),rs.getString("choice_b"),rs.getString("choice_c"),rs.getString("choice_d")}
                                ,Integer.parseInt(rs.getString("Answer")),1,rs.getString("image"));

                        questions.add(question);
                    }

                    else if (rs.getString("includes_image").equals("2")){

                        Question question = new Question(questionNumDisplay + ")" + rs.getString("question_text")
                                ,new String[]{rs.getString("choice_a"),rs.getString("choice_b"),rs.getString("choice_c"),rs.getString("choice_d")}
                                ,Integer.parseInt(rs.getString("Answer")),2,rs.getString("image"));

                        questions.add(question);
                    }

                    else if (rs.getString("includes_image").equals("-1")){
                        questions.add(new Question(questionNumDisplay + ")" + rs.getString("question_text")
                                ,new String[]{rs.getString("choice_a"),rs.getString("choice_b"),rs.getString("choice_c"),rs.getString("choice_d")}
                                ,Integer.parseInt(rs.getString("Answer")),-1,rs.getString("image")));
                    }

                    questionNum++;
                }

                /*if (exam.equals("Exam 2") && rs.getString("exam_num").equals("2")) {
                    questions.add(questionNum,new Question(questionNumDisplay + ")" + rs.getString("question_text")
                            ,new String[]{rs.getString("choice_a"),rs.getString("choice_b"),rs.getString("choice_c"),rs.getString("choice_d")}
                            ,Integer.parseInt(rs.getString("Answer")),-1,"image"));

                    questionNum++;
                }*/

            }

            answerSheet = new ExamAnswerSheet(this,examsWindow);
            answerSheet.setVisible(false);
        }
        catch (SQLException | IOException er) {
            System.out.println(er.getMessage());
            System.out.println("An error occurred while connecting MySQL database");
        }
    }

    public void displayQuestionsOnScreen(){
        bg.clearSelection();

        String question = questions.get(current).getQuestion();

        questionLabel.setText("<html><p>" + question + "</p></html>");

        if (questions.get(current).getIncludes_image() == 1){
            choices[0].setBounds(255,290,400,70);
            choices[0].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[1].setBounds(255,390,400,70);
            choices[1].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[2].setBounds(255,490,400,70);
            choices[2].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[3].setBounds(255,600,400,70);
            choices[3].setFont(new Font("Aharoni",Font.BOLD,15));

            choicesImages[0] = new ImageIcon(questions.get(current).getChoicesImages()[0]);
            choicesImages[1] = new ImageIcon(questions.get(current).getChoicesImages()[1]);
            choicesImages[2] = new ImageIcon(questions.get(current).getChoicesImages()[2]);
            choicesImages[3] = new ImageIcon(questions.get(current).getChoicesImages()[3]);

            for (int j=0;j<4;j++){
                String choice = "";
                if (j == 0){
                    choice = "A)";
                }
                else if (j == 1){
                    choice = "B)";
                }
                else if (j == 2){
                    choice = "C)";
                }
                else if (j == 3){
                    choice = "D)";
                }
                choices[j].setText(choice + " ");
            }

            for(int i=0;i<4;i++)
            {
                choices[i].setBounds(choices[i].getX(),choices[i].getY(),60,20);
            }

            questionLabel.setIcon(null);
            choicesArrayForImages[0].setIcon(choicesImages[0]);
            choicesArrayForImages[1].setIcon(choicesImages[1]);
            choicesArrayForImages[2].setIcon(choicesImages[2]);
            choicesArrayForImages[3].setIcon(choicesImages[3]);
        }

        if (questions.get(current).getIncludes_image() == 2){
            choices[0].setBounds(255,260,400,70);
            choices[0].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[1].setBounds(255,350,400,70);
            choices[1].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[2].setBounds(255,440,400,70);
            choices[2].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[3].setBounds(255,530,400,70);
            choices[3].setFont(new Font("Aharoni",Font.BOLD,15));

            questionImages[0] = new ImageIcon(questions.get(current).getQuestionImages()[0]);

            for (int j=0;j<4;j++){
                String choice = "";
                if (j == 0){
                    choice = "A)";
                }
                else if (j == 1){
                    choice = "B)";
                }
                else if (j == 2){
                    choice = "C)";
                }
                else if (j == 3){
                    choice = "D)";
                }
                choices[j].setText(choice + " ");
            }

            String choice = "";
            for (int j=0;j<4;j++){
                if (j == 0){
                    choice = "A)";
                }
                else if (j == 1){
                    choice = "B)";
                }
                else if (j == 2){
                    choice = "C)";
                }
                else if (j == 3){
                    choice = "D)";
                }
                choices[j].setText("<html><p>" + choice + " " + questions.get(current).getChoices()[j] + "</p></html>");
            }

            choicesArrayForImages[0].setIcon(null);
            choicesArrayForImages[1].setIcon(null);
            choicesArrayForImages[2].setIcon(null);
            choicesArrayForImages[3].setIcon(null);
            questionLabel.setIcon(questionImages[0]);
        }

        if(questions.get(current).getIncludes_image() == -1){
            choices[0].setBounds(255,260,400,70);
            choices[0].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[1].setBounds(255,350,400,70);
            choices[1].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[2].setBounds(255,440,400,70);
            choices[2].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[3].setBounds(255,530,400,70);
            choices[3].setFont(new Font("Aharoni",Font.BOLD,15));

            String choice = "";
            for (int j=0;j<4;j++){
                if (j == 0){
                    choice = "A)";
                }
                else if (j == 1){
                    choice = "B)";
                }
                else if (j == 2){
                    choice = "C)";
                }
                else if (j == 3){
                    choice = "D)";
                }
                choices[j].setText("<html><p>" + choice + " " + questions.get(current).getChoices()[j] + "</p></html>");
            }

            questionLabel.setIcon(null);
        }
    }

    public int checkAnswer(Question question){
        int choice;

        if (choices[0].isSelected()){
            choice = 0;
            question.setAnswerGiven(0);
            question.setAnswerGivenToString("A");
        }
        else if (choices[1].isSelected()){
            choice = 1;
            question.setAnswerGiven(1);
            question.setAnswerGivenToString("B");
        }
        else if (choices[2].isSelected()){
            choice = 2;
            question.setAnswerGiven(2);
            question.setAnswerGivenToString("C");
        }
        else if (choices[3].isSelected()){
            choice = 3;
            question.setAnswerGiven(3);
            question.setAnswerGivenToString("D");
        }
        else {
            choice = -1;
            question.setAnswerGiven(-1);
            question.setAnswerGivenToString("Blank");
        }

        if (question.getAnswerGiven() == 0){
            question.setAnswerGivenToString("A");
        }
        else if (question.getAnswerGiven() == 1){
            question.setAnswerGivenToString("B");
        }
        else if (question.getAnswerGiven() == 2){
            question.setAnswerGivenToString("C");
        }
        else if (question.getAnswerGiven() == 3){
            question.setAnswerGivenToString("D");
        }
        else if (question.getAnswerGiven() == -1){
            question.setAnswerGivenToString("Blank");
        }

        if (choice == question.getAnswer()){
            return 1;
        }
        else if (choice == -1){
            return 0;
        }
        else{
            return -1;
        }
    }

    public void putIncorrectlyAnsweredQuestionsToBookmarkedQuestionsArray(Question question){
        if (!containsQuestion(question)){
            this.bookmarkedExam.bookmarked_questions.add(question);
        }
    }

    public boolean containsQuestion(Question question){
        for (int i=0;i<bookmarkedExam.bookmarked_questions.size();i++){
            if (question.getQuestion().equals(bookmarkedExam.bookmarked_questions.get(i).getQuestion())){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    class ExamListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nextQuestionButton){

                if (checkAnswer(questions.get(current)) == 1){
                    //JOptionPane.showMessageDialog(Exam.this,"Correct!");
                    points += 2;
                }
                else if (checkAnswer(questions.get(current)) == 0){
                    //JOptionPane.showMessageDialog(Exam.this,"Answer: " + questions.get(current).answerToString);
                }
                else if (checkAnswer(questions.get(current)) == -1){
                    //JOptionPane.showMessageDialog(Exam.this,"Wrong! Answer: " + questions.get(current).answerToString);
                    putIncorrectlyAnsweredQuestionsToBookmarkedQuestionsArray(questions.get(current));
                    currentBookmarked++;
                }

                current++;

                displayQuestionsOnScreen();
                //System.out.println(questions.size());

                if (current == questions.size()-1){
                    nextQuestionButton.setEnabled(false);
                    finishExamButton.setEnabled(true);
                }
            }

            if (e.getSource() == finishExamButton){
                if (checkAnswer(questions.get(current)) == 1){
                    //JOptionPane.showMessageDialog(Exam.this,"Correct!");
                    points += 2;
                }
                else if (checkAnswer(questions.get(current)) == 0){
                    //JOptionPane.showMessageDialog(Exam.this,"Answer: " + questions.get(current).answerToString);
                }
                else if (checkAnswer(questions.get(current)) == -1){
                    //JOptionPane.showMessageDialog(Exam.this,"Wrong! Answer: " + questions.get(current).answerToString);
                    putIncorrectlyAnsweredQuestionsToBookmarkedQuestionsArray(questions.get(current));
                    currentBookmarked++;
                }

                if (points >= 70){
                    JOptionPane.showMessageDialog(Exam.this,"You passed! \n" +
                            "Your score: " + points);
                }
                else {
                    JOptionPane.showMessageDialog(Exam.this,"You failed, score needed to pass = 70 \n" +
                            "Your score: " + points);
                }

                int result = JOptionPane.showConfirmDialog(Exam.this,"Do you want to see the results?","",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION){
                    Exam.this.setVisible(false);
                    answerSheet.setVisible(true);
                }

                else{
                    Exam.this.setVisible(false);
                    examsWindow.setVisible(true);
                }
            }

            if (e.getSource() == bookmarkQuestionButton){

                int result = JOptionPane.showConfirmDialog(Exam.this,"Are you sure you want to bookmark this question?", "",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);


                if (!containsQuestion(questions.get(current))){
                    if (result == JOptionPane.YES_OPTION){
                        bookmarkedExam.bookmarked_questions.add(questions.get(current));
                        if (current != questions.size()-1){
                            current++;
                            displayQuestionsOnScreen();
                        }
                        else{
                            Exam.this.setVisible(false);
                            examsWindow.setVisible(true);
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(Exam.this,"This question is already in bookmarked questions.");
                }
            }

            if (e.getSource() == previousWindowButton){
                Exam.this.setVisible(false);
                examsWindow.setVisible(true);
            }
        }
    }
}
