import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ExamAnswerSheet extends JFrame{
    private JLabel questionLabel;
    private JRadioButton [] choices = new JRadioButton[4];
    private JLabel [] choicesArrayForImages = new JLabel[4];
    private JLabel correctAnswer;
    private JLabel answerGiven;
    private ImageIcon [] choicesImages = new ImageIcon[4];
    private ImageIcon [] questionImages = new ImageIcon[4];
    private JButton nextQuestionButton;
    private JButton finishExamButton;
    private JButton previousWindowButton;
    private ButtonGroup bg;

    int current;
    ArrayList<Question> questions;

    private ExamListener examListener = new ExamListener();

    private ParentWindow examsWindow;
    Exam exam;

    public ExamAnswerSheet(Exam exam,ParentWindow examsWindow){
        this.exam = exam;
        this.examsWindow = examsWindow;
        current = 0;

        setTitle("Answer Sheet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        questions = exam.getQuestions();

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

        correctAnswer = new JLabel();
        answerGiven = new JLabel();

        correctAnswer.setBounds(80,150,150,20);
        answerGiven.setBounds(80,180,150,20);

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

        previousWindowButton = new JButton("<<");
        previousWindowButton.setBounds(20,30,60,30);
        previousWindowButton.setFont(new Font("Aharoni",Font.BOLD,20));
        previousWindowButton.addActionListener(examListener);

        finishExamButton.setEnabled(false);

        nextQuestionButton.addActionListener(examListener);
        finishExamButton.addActionListener(examListener);

        add(questionLabel);
        add(nextQuestionButton);
        add(finishExamButton);
        add(previousWindowButton);
        add(correctAnswer);
        add(answerGiven);

        setLayout(null);
        setVisible(true);

        if(current == 0){
            displayQuestionsOnScreen();
        }
    }

    public void displayQuestionsOnScreen(){
        bg.clearSelection();

        String question = questions.get(current).getQuestion();
        System.out.println(current);

        questionLabel.setText("<html><p>" + question + "</p></html>");

        if (current == 0){
            correctAnswer.setText("Correct answer: " + questions.get(current).getAnswerToString());
            questions.get(0).setAnswerGivenToString("B");
            answerGiven.setText("Answer given: " + questions.get(0).getAnswerGivenToString());
        }

        else{
            correctAnswer.setText("Correct answer: " + questions.get(current).getAnswerToString());
            answerGiven.setText("Answer given: " + questions.get(current).getAnswerGivenToString());
        }


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

    class ExamListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == nextQuestionButton){
                current++;

                displayQuestionsOnScreen();
                //System.out.println(questions.size());

                if (current == questions.size()-1){
                    nextQuestionButton.setEnabled(false);
                    finishExamButton.setEnabled(true);
                }
            }

            if (e.getSource() == finishExamButton){

                ExamAnswerSheet.this.setVisible(false);
                examsWindow.setVisible(true);
            }

            if (e.getSource() == previousWindowButton){
                ExamAnswerSheet.this.setVisible(false);
                examsWindow.setVisible(true);
            }
        }
    }
}
