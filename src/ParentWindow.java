import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ParentWindow extends JFrame {
    public ParentWindow(DrivingLicenseExamApplication application){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(application.getWidth(), application.getHeight()));

        try {
            Image img= ImageIO.read(getClass().getResource("/images/logo.jpg"));
            this.setIconImage(img);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        getContentPane().setBackground(Color.LIGHT_GRAY);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(null);
        setVisible(true);
    }
}
