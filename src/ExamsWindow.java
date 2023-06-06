
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamsWindow extends ParentWindow{
    private final DrivingLicenseExamApplication application;

    private final JButton exam1Button;
    private final JButton exam2Button;
    private final JButton exam3Button;
    private final JButton exam4Button;

    private final JButton previousWindowButton;

    public ExamsWindow(DrivingLicenseExamApplication application){
        super(application);
        this.application = application;

        setTitle("Driving License Exams");

        exam1Button = new JButton("Exam 1");
        exam1Button.setBounds(getWidth()/2 - 120,30,240,80);
        exam1Button.setFont(new Font("Aharoni",Font.BOLD,30));
        exam1Button.addActionListener(new ExamSelectedListener());

        exam2Button = new JButton("Exam 2");
        exam2Button.setBounds(getWidth()/2 - 120,150,240,80);
        exam2Button.setFont(new Font("Aharoni",Font.BOLD,30));
        exam2Button.addActionListener(new ExamSelectedListener());

        exam3Button = new JButton("Exam 3");
        exam3Button.setBounds(getWidth()/2 - 120,270,240,80);
        exam3Button.setFont(new Font("Aharoni",Font.BOLD,30));
        exam3Button.addActionListener(new ExamSelectedListener());

        exam4Button = new JButton("Exam 4");
        exam4Button.setBounds(getWidth()/2 - 120,390,240,80);
        exam4Button.setFont(new Font("Aharoni",Font.BOLD,30));
        exam4Button.addActionListener(new ExamSelectedListener());

        previousWindowButton = new JButton("<<");
        previousWindowButton.setBounds(20,30,60,30);
        previousWindowButton.setFont(new Font("Aharoni",Font.BOLD,20));
        previousWindowButton.addActionListener(new ExamSelectedListener());

        add(exam1Button);
        add(exam2Button);
        add(exam3Button);
        add(exam4Button);
        add(previousWindowButton);
    }

    class ExamSelectedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exam1Button){
                setVisible(false);
                Exam exam = new Exam("Exam 1",ExamsWindow.this);

                exam.setQuestions("Exam 1");
                exam.setTitle("August 4 2018");

                exam.displayQuestionsOnScreen();
            }
            if (e.getSource() == exam2Button){
                JOptionPane.showMessageDialog(ExamsWindow.this,"COMING SOON!");
            }
            if (e.getSource() == exam3Button){
                JOptionPane.showMessageDialog(ExamsWindow.this,"COMING SOON!");
            }
            if (e.getSource() == exam4Button){
                JOptionPane.showMessageDialog(ExamsWindow.this,"COMING SOON!");
            }
            if (e.getSource() == previousWindowButton){
                setVisible(false);
                application.setVisible(true);
            }
        }
    }
}
