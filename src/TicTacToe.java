import javax.swing.*;

public class TicTacToe extends JFrame implements onPanelBtnListener, onGamePanelListener {

    private GamePanel gamePanel;
    private DashboardPanel dashboardPanel;

    public TicTacToe() {

        initialiseGUI();

        getContentPane().add(dashboardPanel);
        setVisible(true);
        setResizable(false);
        setSize(600, 500);
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initialiseGUI() {
        dashboardPanel = new DashboardPanel();
        dashboardPanel.setBtnListener(this);

        gamePanel = new GamePanel();
        gamePanel.addGameListener(this);
    }

    private void displayPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        repaint();
        printAll(getGraphics());
    }

    public static void main(String[] args) {
        new TicTacToe();
    }

    @Override
    public void onStartGame() {
        displayPanel(gamePanel);
    }

    @Override
    public void onSettings() {

    }

    @Override
    public void onExit() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure?",
                "Confirm Exit?", JOptionPane.OK_CANCEL_OPTION);
        if (response == 0) this.dispose();
    }

    @Override
    public int onGameFinished(String winner) {
        return JOptionPane.showConfirmDialog(this, winner
                        + " Want to play again?", "Game Finished",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onBackPressed() {
        displayPanel(dashboardPanel);
    }
}
