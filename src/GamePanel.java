import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

    enum Rect {
        x(25), y(40), width(60), height(30);
        private final int levelCode;

        Rect(int levelCode) {
            this.levelCode = levelCode;
        }

        public int get() {
            return levelCode;
        }
    }

    private JLabel playerTurnLbl;
    boolean player1Turn = true;
    private String winner;
    private Rectangle rectangle;
    private final Color defaultColor;
    private final GridBagConstraints gbc;
    private onGamePanelListener mListener;
    private final JPanel panel = new JPanel();
    private final JButton[][] btnArr = new JButton[3][3];

    public GamePanel() {
        setLayout(new GridBagLayout());
        addMouseListener(this);
        gbc = new GridBagConstraints();
        defaultColor = new JButton().getBackground();
        gbc.insets = new Insets(15, 0, 15, 0);
        setup();
    }

    private void setup() {
        panel.setLayout(new GridLayout(3, 3, 5, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btnArr[i][j] = new JButton();
                btnArr[i][j].setFocusPainted(false);
                btnArr[i][j].setPreferredSize(new Dimension(120, 90));
                btnArr[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                btnArr[i][j].setBorder(new BevelBorder(BevelBorder.RAISED));
                btnArr[i][j].addActionListener(this);
                panel.add(btnArr[i][j]);
            }
        }
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        playerTurnLbl = new JLabel("Player X turn");
        add(playerTurnLbl, gbc);
        gbc.gridy = 1;
        add(panel, gbc);
        gbc.gridy = 2;
        CustomButton restartBtn = new CustomButton("Restart");
        restartBtn.addActionListener(e -> resetBoard());
        add(restartBtn, gbc);
    }

    public void addGameListener(onGamePanelListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(Rect.x.get(), Rect.y.get(), Rect.width.get(), Rect.height.get());
        g.drawString("Back", Rect.x.get() + (Rect.width.get() / 4) + 2,
                Rect.y.get() + Rect.height.get() / 2 + 5);
        rectangle = new Rectangle(Rect.x.get(), Rect.y.get(), Rect.width.get(), Rect.height.get());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof JButton button)) return;
        if (button.getText().isEmpty()) {
            if (player1Turn) {
                button.setText("X");
                playerTurnLbl.setText("Player O turn");
                player1Turn = false;
            } else {
                button.setText("O");
                playerTurnLbl.setText("Player X turn");
                player1Turn = true;
            }
        }

        if (checkWinner()) {
            if (mListener.onGameFinished(winner) == 0)
                resetBoard();
        } else if (isGameDraw()) {
            winner = "Game Draw!";
            if (mListener.onGameFinished(winner) == 0)
                resetBoard();
        }

    }

    private boolean isGameDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String text = btnArr[i][j].getText();
                if (text.equals("")) return false;
            }
        }
        return true;
    }

    private boolean checkWinner() {
        if (checkStrings(btnArr[0][0], btnArr[0][1]) &&
                checkStrings(btnArr[0][0], btnArr[0][2]))
            setButtonColor(btnArr[0][0], btnArr[0][1], btnArr[0][2]);
        else if (checkStrings(btnArr[0][0], btnArr[1][1]) &&
                checkStrings(btnArr[0][0], btnArr[2][2]))
            setButtonColor(btnArr[0][0], btnArr[1][1], btnArr[2][2]);
        else if (checkStrings(btnArr[0][0], btnArr[1][0]) &&
                checkStrings(btnArr[0][0], btnArr[2][0]))
            setButtonColor(btnArr[0][0], btnArr[1][0], btnArr[2][0]);
        else if (checkStrings(btnArr[2][0], btnArr[2][1]) &&
                checkStrings(btnArr[2][0], btnArr[2][2]))
            setButtonColor(btnArr[2][0], btnArr[2][1], btnArr[2][2]);
        else if (checkStrings(btnArr[2][0], btnArr[1][1]) &&
                checkStrings(btnArr[2][0], btnArr[0][2]))
            setButtonColor(btnArr[2][0], btnArr[1][1], btnArr[0][2]);
        else if (checkStrings(btnArr[0][2], btnArr[1][2]) &&
                checkStrings(btnArr[0][2], btnArr[2][2]))
            setButtonColor(btnArr[0][2], btnArr[1][2], btnArr[2][2]);
        else if (checkStrings(btnArr[0][1], btnArr[1][1]) &&
                checkStrings(btnArr[0][1], btnArr[2][1]))
            setButtonColor(btnArr[0][1], btnArr[1][1], btnArr[2][1]);
        else if (checkStrings(btnArr[1][0], btnArr[1][1]) &&
                checkStrings(btnArr[1][0], btnArr[1][2]))
            setButtonColor(btnArr[1][0], btnArr[1][1], btnArr[1][2]);
        else
            return false;

        return true;
    }

    private void resetBoard() {
        player1Turn = true;
        playerTurnLbl.setText("Player X turn");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btnArr[i][j].setText("");
                btnArr[i][j].setBackground(defaultColor);
                btnArr[i][j].setForeground(Color.BLACK);
            }
        }
    }


    public void setButtonColor(JButton... jButtons) {
        for (JButton button : jButtons) {
            button.setForeground(Color.WHITE);
            button.setBackground(Color.GREEN);
        }
    }

    public boolean checkStrings(JButton btn1, JButton btn2) {
        if (btn1.getText().isEmpty() || btn2.getText().isEmpty()) return false;
        else if (btn1.getText().equals(btn2.getText())) {
            winner = "Player " + btn1.getText() + " is the winner.";
            return true;
        } else return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (rectangle.contains(e.getX(), e.getY())) {
            resetBoard();
            mListener.onBackPressed();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
