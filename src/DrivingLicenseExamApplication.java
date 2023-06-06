import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DrivingLicenseExamApplication extends JFrame {

    private final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int)size.getWidth()*5/10;
    private int height = (int)size.getHeight()*8/10;

    public DrivingLicenseExamApplication(){
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setTitle("Driving License Exam Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        try {
            Image img= ImageIO.read(getClass().getResource("/images/logo.jpg"));
            this.setIconImage(img);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        setPreferredSize(new Dimension(width,height));
        pack();
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("   Driving License Exam Application");
        titleLabel.setFont(new Font("Aharoni",Font.BOLD,30));
        Color titleBackgroundColor = new Color(123, 200, 150);
        titleLabel.setOpaque(true);
        titleLabel.setBounds(230,180,540,40);
        titleLabel.setBackground(titleBackgroundColor);

        JButton drivingLicenseExamButton = new JButton();
        drivingLicenseExamButton.setLayout(new BorderLayout());

        JLabel blabel1 = new JLabel("   Driving License");
        blabel1.setFont(new Font("Aharoni",Font.BOLD,25));
        JLabel blabel2 = new JLabel("         Exams");
        blabel2.setFont(new Font("Aharoni",Font.BOLD,25));

        int casualButtonWidth = 240;
        int casualButtonHeight = 80;
        drivingLicenseExamButton.setBounds(190,400, casualButtonWidth, casualButtonHeight);
        drivingLicenseExamButton.add(BorderLayout.NORTH,blabel1);
        drivingLicenseExamButton.add(BorderLayout.SOUTH,blabel2);

        drivingLicenseExamButton.addActionListener(new ExamsButtonListener());

        JButton bookmarkedQuestionsButton = new JButton();
        bookmarkedQuestionsButton.setLayout(new BorderLayout());

        JLabel blabel3 = new JLabel("    Bookmarked");
        blabel3.setFont(new Font("Aharoni",Font.BOLD,25));
        JLabel blabel4 = new JLabel("      Questions");
        blabel4.setFont(new Font("Aharoni",Font.BOLD,25));

        bookmarkedQuestionsButton.setBounds(570,400, casualButtonWidth, casualButtonHeight);
        bookmarkedQuestionsButton.add(BorderLayout.NORTH,blabel3);
        bookmarkedQuestionsButton.add(BorderLayout.SOUTH,blabel4);
        bookmarkedQuestionsButton.addActionListener(new BookmarkedQuestionsButtonListener());

        add(titleLabel);
        add(drivingLicenseExamButton);
        add(bookmarkedQuestionsButton);

        setLayout(null);
        setVisible(true);
    }

    class ExamsButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new ExamsWindow(DrivingLicenseExamApplication.this);
        }
    }

    class BookmarkedQuestionsButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new BookmarkedQuestionsWindow(DrivingLicenseExamApplication.this);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
