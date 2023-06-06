
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookmarkedQuestionsWindow extends ParentWindow {
    private final DrivingLicenseExamApplication application;

    private final JButton bookmarkedQuestionsButton;
    private final JButton previousWindowButton;

    public BookmarkedQuestionsWindow(DrivingLicenseExamApplication application){
        super(application);
        this.application = application;

        setTitle("Bookmarked Questions");

        JLabel blabel1 = new JLabel("    Bookmarked");
        blabel1.setFont(new Font("Aharoni",Font.BOLD,20));
        JLabel blabel2 = new JLabel("       Questions");
        blabel2.setFont(new Font("Aharoni",Font.BOLD,20));

        bookmarkedQuestionsButton = new JButton();
        bookmarkedQuestionsButton.setLayout(new BorderLayout());
        bookmarkedQuestionsButton.setBounds(getWidth()/2 - 130,30,220,60);
        bookmarkedQuestionsButton.add(BorderLayout.NORTH,blabel1);
        bookmarkedQuestionsButton.add(BorderLayout.SOUTH,blabel2);
        bookmarkedQuestionsButton.addActionListener(new BookmarkedQuestionsWindowButtonListener());

        previousWindowButton = new JButton("<<");
        previousWindowButton.setBounds(20,30,60,30);
        previousWindowButton.setFont(new Font("Aharoni",Font.BOLD,20));
        previousWindowButton.addActionListener(new BookmarkedQuestionsWindowButtonListener());

        add(bookmarkedQuestionsButton);
        add(previousWindowButton);
    }

    class BookmarkedQuestionsWindowButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bookmarkedQuestionsButton){

                BookmarkedExam exam = new BookmarkedExam(BookmarkedQuestionsWindow.this);
                //exam.setVisible(false);

                if (exam.bookmarked_questions == null || exam.bookmarked_questions.size() == 0){
                    JOptionPane.showMessageDialog(BookmarkedQuestionsWindow.this,"There are no bookmarked questions!");
                    setVisible(false);
                    application.setVisible(true);
                }

                else if (exam.bookmarked_questions != null){
                    setVisible(false);
                    exam.setVisible(true);
                    exam.displayQuestionsOnScreen();
                }
            }

            if (e.getSource() == previousWindowButton){
                setVisible(false);
                application.setVisible(true);
            }
        }
    }

}
