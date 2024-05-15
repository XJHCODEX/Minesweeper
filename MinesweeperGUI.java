package Minesweeper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private Grid grid;

    public MinesweeperGUI() {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid = new Grid(); // calling methods from Grid class
        int numRows = grid.getNumRows();
        int numColumns = grid.getNumColumns();
        
        initializeGrid(numRows, numColumns);
        
        JPanel panel = new JPanel(new GridLayout(numRows, numColumns));
        buttons = new JButton[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40)); // Adjust size as needed
                final int row = i;
                final int col = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle button click, e.g., reveal cell or mark bomb
                        handleButtonClick(row, col);}});
                panel.add(button);
                buttons[i][j] = button;
            }
        }

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        controlPanel.add(newGameButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        
    }
    private void resetGame() {
        // Reset the game by clearing the button text and background color
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
            }
        }
        grid.createBombGrid(); // Reset the bomb grid
        frame.repaint();
    }
    private void initializeGrid(int numRows, int numColumns) {
        // Initialize BombGrid and buttons
        grid.createBombGrid(); // method name for initializing BombGrid

        JPanel panel = new JPanel(new GridLayout(numRows, numColumns));
        buttons = new JButton[numRows][numColumns];
        
        // Create buttons and add ActionListener to each button
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
               
            }
        }

        frame.add(panel); // Add panel to frame
    }
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int column;

        public ButtonClickListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement logic for handling button click
            handleButtonClick(row, column);
        }
    }
    
    private void handleButtonClick(int row, int col) {
        boolean isBomb = grid.IsBombAtLocation(row, col);
        int count = grid.getCountAtLocation(row, col);
        System.out.println("Clicked cell: (" + row + ", " + col + ")");
        System.out.println("Is bomb: " + isBomb);
        System.out.println("Count: " + count);
        if (isBomb) {
        	buttons[row][col].setText("X");
            buttons[row][col].setBackground(Color.RED); // Set text/icon for bomb cell
        } else {
            buttons[row][col].setText(Integer.toString(count));
            buttons[row][col].setBackground(Color.GREEN); // text/icon for non-bomb cell
        }
        frame.repaint();

        JOptionPane.showMessageDialog(frame, "Button at (" + row + ", " + col + ") clicked.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MinesweeperGUI();
            }
        });
    }
}
