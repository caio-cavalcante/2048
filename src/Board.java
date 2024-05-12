public class Board {
    public Tile[][] board;
    int grids = 4;
    int border = 0;
    public int score = 0;

    public Board() {
        board = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    public int getScore() {return score;}

    public int getHighTile() {
        int highTile = board[0][0].getValue();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].getValue() > highTile) {
                    highTile = board[i][j].getValue();
                }
            }
        }
        return highTile;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                string += board[i][j].toString() + " ";
            }
            string += "\n";
        }
        string += "Score: " + score;
        return string;
    }

    public void spawn() {
        boolean emptyTile = true;
        while(emptyTile){
            int row = (int) (Math.random() * 4);
            int col = (int) (Math.random() * 4);
            double x = Math.random();
            if (board[row][col].getValue() == 0) {
                if (x < 0.2) {
                    board[row][col] = new Tile(4);
                    emptyTile = false;
                } else {
                    board[row][col] = new Tile(2);
                    emptyTile = false;
                }
            }
        }
    }

    public boolean blackOut() {
        int numTiles = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() > 0){numTiles++;}
            }
        }

        if (numTiles == 16) {return true;}

        return false;
    }

    public boolean gameOver() {
        int numTiles = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue() > 0) {
                    if (i == 0 && j == 0){
                        if (board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()) {
                            numTiles++;
                        }
                    } else if (i == 0 && j == 3) {
                        if (board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue()) {
                            numTiles++;
                        }
                    } else if (i == 3 && j == 3) {
                        if (board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue()) {
                            numTiles++;
                        }
                    } else if (i == 3 && j == 0) {
                        if (board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()) {
                            numTiles++;
                        }
                    } else if (i == 0 && (j == 1 || j == 2)) {
                        if (board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue()) {
                            numTiles++;
                        }
                    } else if (i == 3 && (j == 1 || j == 2)) {
                        if (board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue()) {
                            numTiles++;
                        }
                    } else if (j == 0 && (i == 1 || i == 2)) {
                        if (board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue()) {
                            numTiles++;
                        }
                    } else if (j == 3 && (i == 1 || i ==2)) {
                        if (board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue()) {
                            numTiles++;
                        }
                    } else {
                        if (board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue()) {
                            numTiles++;
                        }
                    }
                }
            }
        }
        if (numTiles == 16) {
            return true;
        }
        return false;
    }

    public void up() {
        for (int i = 0; i < grids; i++) {
            border = 0;
            for (int j = 0; j < grids; j++) {
                if (board[j][i].getValue() != 0) {
                    if (border <= j) {
                        verticalMove(j, i, "up");
                    }
                }
            }
        }
    }

    public void down() {
        for (int i = 0; i < grids; i++) {
            border = (grids - 1);
            for (int j = grids - 1; j >= 0; j--) {
                if (board[j][i].getValue() != 0) {
                    if (border >= j) {
                        verticalMove(j, i, "down");
                    }
                }
            }
        }
    }

    public void verticalMove(int row, int col, String direction) {
        Tile moveTile = board[border][col];
        Tile matchTile = board[row][col];
        if (moveTile.getValue() == 0 || moveTile.getValue() == matchTile.getValue()) {
            if (row > border || (direction.equals("down") && (row < border))) {
                int addScore = moveTile.getValue() + matchTile.getValue();
                if (moveTile.getValue() != 0) {
                    score += addScore;
                }
                moveTile.setValue(addScore);
                matchTile.setValue(0);
            }
        } else {
            if (direction.equals("down")) {
                border--;
            } else {
                border++;
            }
            verticalMove(row, col, direction);
        }
    }

    public void left() {
        for (int i = 0; i < grids; i++) {
            border = 0;
            for (int j = 0; j < grids; j++) {
                if (board[i][j].getValue()!= 0) {
                    if (border <= j) {
                        horizontalMove(i, j, "left");
                    }
                }
            }
        }
    }

    public void right() {
        for (int i = 0; i < grids; i++) {
            border = (grids - 1);
            for (int j = (grids - 1); j >= 0; j--) {
                if (board[i][j].getValue() != 0) {
                    if (border >= j) {
                        horizontalMove(i, j, "right");
                    }
                }
            }
        }
    }

    public void horizontalMove(int row, int col, String direction) {
        Tile moveTile = board[row][border];
        Tile matchTile = board[row][col];
        if (moveTile.getValue() == 0 || moveTile.getValue() == matchTile.getValue()) {
            if (col > border || (direction.equals("right") && (col < border))) {
                int addScore = moveTile.getValue() + matchTile.getValue();
                if (moveTile.getValue() != 0) {
                    score += addScore;
                }
                moveTile.setValue(addScore);
                matchTile.setValue(0);
            }
        } else {
            if (direction.equals("right")) {
                border--;
            } else {
                border++;
            }
            horizontalMove(row, col, direction);
        }
    }
}