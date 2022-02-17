import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

public class DashboardPanel extends JPanel implements ActionListener {

    public static final String EXIT_TXT = "Exit";
    public static final String START_GAME_TXT = "Start Game";
    private static final String SETTING_TXT = "Settings";

    private BufferedImage image, muteIm;
    private boolean isMusicOn = true;
    ImageIcon icon, bgImg, volImg;
    private onPanelBtnListener mListener;

    public DashboardPanel() {
        super();
        loadImage();

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLbl = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        LineBorder lineBorder = new LineBorder(Color.RED, 2, true);
        CompoundBorder compoundBorder = new CompoundBorder(lineBorder,
                new EmptyBorder(10, 20, 10, 20));
        titleLbl.setFont(new Font("Arial", Font.BOLD, 24));
        titleLbl.setPreferredSize(new Dimension(200, 40));
        titleLbl.setForeground(Color.DARK_GRAY);
        titleLbl.setBorder(compoundBorder);

        // Start Button
        CustomButton startBtn = new CustomButton(START_GAME_TXT);
        startBtn.addActionListener(this);
        CustomButton settingsBtn = new CustomButton(SETTING_TXT);
        settingsBtn.addActionListener(this);
        CustomButton exitBtn = new CustomButton(EXIT_TXT);
        exitBtn.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLbl, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(60, 0, 0, 0);
        add(startBtn, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(settingsBtn, gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(exitBtn, gbc);
    }

    private void loadImage() {
        try {
            bgImg = new ImageIcon("dashboard_img.jpg");
            icon = new ImageIcon("mute_icon");

            volImg = new ImageIcon("volume.png");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setBtnListener(onPanelBtnListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg.getImage(), 0, 0, this);
        g.setColor(Color.WHITE);
        g.fillRect(getWidth() - 60, getHeight() - 50, 35, 30);
        g.drawImage(volImg.getImage(), getWidth() - 60, getHeight() - 50, 35, 30, this);
//        g.drawImage(icon.getImage(), getWidth() - 60, getHeight() - 50, 35, 30, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case DashboardPanel.START_GAME_TXT -> mListener.onStartGame();
            case DashboardPanel.SETTING_TXT -> mListener.onSettings();
            case DashboardPanel.EXIT_TXT -> mListener.onExit();
        }
    }
}
