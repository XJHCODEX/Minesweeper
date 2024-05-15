package Minesweeper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private Grid grid;
    private boolean[][] flags;
    private int totalFlags; // Total flags available
    private int flagsPlaced; // Flags already placed
    private JLabel flagsLeftLabel;

    public MinesweeperGUI() {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        totalFlags = 15;

        grid = new Grid(); // calling methods from Grid class
        int numRows = grid.getNumRows();
        int numColumns = grid.getNumColumns();
        
        flagsLeftLabel = new JLabel("Flags Left: " + totalFlags);
        frame.add(flagsLeftLabel, BorderLayout.NORTH);

        initializeGrid(numRows, numColumns);
        
        JPanel panel = new JPanel(new GridLayout(numRows, numColumns));
        buttons = new JButton[numRows][numColumns];
        flags = new boolean[numRows][numColumns];

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
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (SwingUtilities.isRightMouseButton(evt)) {
                                toggleFlag(row, col);}}});
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
    private void toggleFlag(int row, int col) {
    if(flagsPlaced < totalFlags) {
        if (!flags[row][col]) {
            // Set flag icon
            ImageIcon flagIcon = new ImageIcon("flag.png"); // Replace with actual path to flag icon
            Image scaledFlag = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            flagIcon = new ImageIcon(scaledFlag);
            buttons[row][col].setIcon(flagIcon);

            flagsPlaced++; // Increment flags placed

        } else {
            // Clear flag
            buttons[row][col].setIcon(null); // Remove icon
            flagsPlaced--; // Decrement flags placed
        }
        flags[row][col] = !flags[row][col]; // Toggle flag status

        if (grid.IsBombAtLocation(row, col) && flags[row][col]) {
            buttons[row][col].setBackground(Color.GREEN);}

        // Update flags left display
        int flagsLeft = totalFlags - flagsPlaced;
        // Update a JLabel or any other component to display flagsLeft
        // For example, if you have a JLabel called flagsLeftLabel:
        flagsLeftLabel.setText("Flags Left: " + flagsLeft);
        }
        else {
            // Alert the player that they cannot place more flags
            JOptionPane.showMessageDialog(frame, "You have placed all available flags!");
        }
    }
    private void handleButtonClick(int row, int col) {
        boolean isBomb = grid.IsBombAtLocation(row, col);
        int count = grid.getCountAtLocation(row, col);
        System.out.println("Clicked cell: (" + row + ", " + col + ")");
        System.out.println("Is bomb: " + isBomb);
        System.out.println("Count: " + count);
        
        if (isBomb) {
        	// buttons[row][col].setText("X");
            ImageIcon bombIcon = new ImageIcon("bomb.png");
            Image scaledBomb = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            bombIcon = new ImageIcon(scaledBomb);
            buttons[row][col].setIcon(bombIcon);
            buttons[row][col].setBackground(Color.RED); // Set text/icon for bomb cell
        } else {
            buttons[row][col].setText(Integer.toString(count));
            // buttons[row][col].setBackground(Color.GREEN); // text/icon for non-bomb cell
        }
        
        frame.repaint();
        //if (isBomb){
        //    JOptionPane.showMessageDialog(frame, "BOMBED");
        //}
        //else{
        //    JOptionPane.showMessageDialog(frame, "SAFE");
        //}
        if (count == 0) {
            // Reveal adjacent cells if the clicked cell has no adjacent bombs
            revealAdjacentCells(row, col);
        }
        frame.repaint();
        if (checkWinCondition()) {
            JOptionPane.showMessageDialog(frame, "Congratulations! You've won!");
        }
    }
    private void revealAdjacentCells(int row, int col) {
        // Recursive method to reveal adjacent cells if the clicked cell has no adjacent bombs
        if (row < 0 || col < 0 || row >= buttons.length || col >= buttons[0].length || buttons[row][col].getText().length() > 0) {
            // Base case: Return if cell is out of bounds or already revealed
            return;
        }
        // need to fix recursion for adjacent cells
        int count = grid.getCountAtLocation(row, col);
        buttons[row][col].setText(Integer.toString(count));
        buttons[row][col].setBackground(Color.GREEN); // Set background color for revealed cell
        
        if (count == 0) {
            // Continue revealing adjacent cells recursively
            revealAdjacentCells(row - 1, col - 1);
            revealAdjacentCells(row - 1, col);
            revealAdjacentCells(row - 1, col + 1);
            revealAdjacentCells(row, col - 1);
            revealAdjacentCells(row, col + 1);
            revealAdjacentCells(row + 1, col - 1);
            revealAdjacentCells(row + 1, col);
            revealAdjacentCells(row + 1, col + 1);
        }
    }
    private boolean checkWinCondition() {
        // Check if all non-bomb cells have been revealed
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (!grid.IsBombAtLocation(i, j) && buttons[i][j].getText().isEmpty()) {
                    return false; // Return false if any non-bomb cell is unrevealed
                }
            }
        }
        return true; // Return true if all non-bomb cells have been revealed
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
