import javax.swing.*;
import java.awt.*;

public class UIUtils {

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(30, 90, 160));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        return b;
    }
}
