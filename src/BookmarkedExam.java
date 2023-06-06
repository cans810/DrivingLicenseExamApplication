import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class BookmarkedExam extends JFrame {
    private JLabel questionLabel;
    private JRadioButton [] choices = new JRadioButton[4];
    private JLabel [] choicesArrayForImages = new JLabel[4];
    private ImageIcon [] choicesImages = new ImageIcon[4];
    private ImageIcon [] questionImages = new ImageIcon[4];
    private JButton nextQuestionButton;
    private JButton finishExamButton;
    private JButton removeBookmarkButton;
    private JButton previousWindowButton;
    private ButtonGroup bg;

    private int current;
    public static ArrayList<Question> bookmarked_questions = new ArrayList<>();
    private ExamListener examListener = new ExamListener();

    private ParentWindow examsWindow;

    public BookmarkedExam(ParentWindow examsWindow){
        this.examsWindow = examsWindow;
        current = 0;

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

        questionLabel = new JLabel();

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
        removeBookmarkButton = new JButton("Un-Bookmark");

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
        removeBookmarkButton.setBounds(410,700,170,60);
        removeBookmarkButton.setFont(new Font("Aharoni",Font.BOLD,20));

        previousWindowButton = new JButton("<<");
        previousWindowButton.setBounds(20,30,60,30);
        previousWindowButton.setFont(new Font("Aharoni",Font.BOLD,20));
        previousWindowButton.addActionListener(examListener);

        //finishExamButton.setEnabled(false);

        nextQuestionButton.addActionListener(examListener);
        finishExamButton.addActionListener(examListener);
        removeBookmarkButton.addActionListener(examListener);

        add(questionLabel);
        add(nextQuestionButton);
        add(finishExamButton);
        add(removeBookmarkButton);
        add(previousWindowButton);

        setLayout(null);
        setVisible(false);
    }

    public void displayQuestionsOnScreen(){
        bg.clearSelection();

        if (current == bookmarked_questions.size()-1){
            nextQuestionButton.setEnabled(false);
            //finishExamButton.setEnabled(true);
        }

        String question = bookmarked_questions.get(current).getQuestion();

        questionLabel.setText("<html><p>" + question + "</p></html>");

        if (bookmarked_questions.get(current).getIncludes_image() == 1){
            choices[0].setBounds(255,290,400,70);
            choices[0].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[1].setBounds(255,390,400,70);
            choices[1].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[2].setBounds(255,490,400,70);
            choices[2].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[3].setBounds(255,600,400,70);
            choices[3].setFont(new Font("Aharoni",Font.BOLD,15));

            choicesImages[0] = new ImageIcon(bookmarked_questions.get(current).getChoicesImages()[0]);
            choicesImages[1] = new ImageIcon(bookmarked_questions.get(current).getChoicesImages()[1]);
            choicesImages[2] = new ImageIcon(bookmarked_questions.get(current).getChoicesImages()[2]);
            choicesImages[3] = new ImageIcon(bookmarked_questions.get(current).getChoicesImages()[3]);

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

        else if (bookmarked_questions.get(current).getIncludes_image() == 2){
            choices[0].setBounds(255,260,400,70);
            choices[0].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[1].setBounds(255,350,400,70);
            choices[1].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[2].setBounds(255,440,400,70);
            choices[2].setFont(new Font("Aharoni",Font.BOLD,15));

            choices[3].setBounds(255,530,400,70);
            choices[3].setFont(new Font("Aharoni",Font.BOLD,15));

            questionImages[0] = new ImageIcon(bookmarked_questions.get(current).getQuestionImages()[0]);

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
                choices[j].setText("<html><p>" + choice + " " + bookmarked_questions.get(current).getChoices()[j] + "</p></html>");
            }

            choicesArrayForImages[0].setIcon(null);
            choicesArrayForImages[1].setIcon(null);
            choicesArrayForImages[2].setIcon(null);
            choicesArrayForImages[3].setIcon(null);
            questionLabel.setIcon(questionImages[0]);
        }

        else if(bookmarked_questions.get(current).getIncludes_image() == -1){
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
                choices[j].setText("<html><p>" + choice + " " + bookmarked_questions.get(current).getChoices()[j] + "</p></html>");
            }

            questionLabel.setIcon(null);
        }
    }

    public int checkAnswer(Question question){
        int choice;

        if (choices[0].isSelected()){
            choice = 0;
        }
        else if (choices[1].isSelected()){
            choice = 1;
        }
        else if (choices[2].isSelected()){
            choice = 2;
        }
        else if (choices[3].isSelected()){
            choice = 3;
        }
        else {
            choice = -1;
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

    class ExamListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nextQuestionButton){

                if (checkAnswer(bookmarked_questions.get(current)) == 1){
                    JOptionPane.showMessageDialog(BookmarkedExam.this,"Correct!");
                    bookmarked_questions.remove(bookmarked_questions.get(current));
                    current--;
                }
                else if (checkAnswer(bookmarked_questions.get(current)) == 0){
                    JOptionPane.showMessageDialog(BookmarkedExam.this,"Answer: " + bookmarked_questions.get(current).getAnswerToString());
                    //putIncorrectlyAnsweredQuestionToBookmarkedQuestionsArray(bookmarked_questions.get(current));
                }
                else if (checkAnswer(bookmarked_questions.get(current)) == -1){
                    JOptionPane.showMessageDialog(BookmarkedExam.this,"Wrong! Answer: " + bookmarked_questions.get(current).getAnswerToString());
                    //putIncorrectlyAnsweredQuestionToBookmarkedQuestionsArray(bookmarked_questions.get(current));
                }

                if (current != bookmarked_questions.size()-1){
                    current++;
                    displayQuestionsOnScreen();
                }

                else {
                    nextQuestionButton.setEnabled(false);
                    //finishExamButton.setEnabled(true);
                }

            }

            if (e.getSource() == finishExamButton){
                if (current == bookmarked_questions.size()){
                    nextQuestionButton.setEnabled(false);
                    //finishExamButton.setEnabled(true);
                }

                else{
                    if (checkAnswer(bookmarked_questions.get(current)) == 1){
                        JOptionPane.showMessageDialog(BookmarkedExam.this,"Correct!");
                        bookmarked_questions.remove(bookmarked_questions.get(current));
                        current--;
                    }
                    else if (checkAnswer(bookmarked_questions.get(current)) == 0){
                        JOptionPane.showMessageDialog(BookmarkedExam.this,"Answer: " + bookmarked_questions.get(current).getAnswerToString());
                        //putIncorrectlyAnsweredQuestionToBookmarkedQuestionsArray(bookmarked_questions.get(current));
                    }
                    else if (checkAnswer(bookmarked_questions.get(current)) == -1){
                        JOptionPane.showMessageDialog(BookmarkedExam.this,"Wrong! Answer: " + bookmarked_questions.get(current).getAnswerToString());
                        //putIncorrectlyAnsweredQuestionToBookmarkedQuestionsArray(bookmarked_questions.get(current));
                    }
                }

                BookmarkedExam.this.setVisible(false);
                examsWindow.setVisible(true);
            }

            if (e.getSource() == removeBookmarkButton){
                if (bookmarked_questions.size()-1 <= 0 || current+1 >= bookmarked_questions.size()){
                    bookmarked_questions.remove(bookmarked_questions.get(current));
                    BookmarkedExam.this.setVisible(false);
                    examsWindow.setVisible(true);
                }

                else{
                    bookmarked_questions.remove(bookmarked_questions.get(current));
                    displayQuestionsOnScreen();

                    if (current == bookmarked_questions.size()-1){
                        nextQuestionButton.setEnabled(false);
                        //finishExamButton.setEnabled(true);
                    }
                }
            }

            if (e.getSource() == previousWindowButton){
                BookmarkedExam.this.setVisible(false);
                examsWindow.setVisible(true);
            }
        }
    }
}
