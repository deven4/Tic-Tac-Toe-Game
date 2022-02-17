import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text);
        setPreferredSize(new Dimension(150, 40));
        setFont(new Font("Calibre", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setBackground(Color.decode("#24A0ED"));
        setFocusPainted(false);
        setBorder(new BevelBorder(BevelBorder.RAISED));
    }
}
