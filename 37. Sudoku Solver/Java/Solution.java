/**
Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.

Constraints:

board.length == 9
board[i].length == 9
board[i][j] is a digit or '.'.
It is guaranteed that the input board has only one solution.
*/

import java.util.HashMap;
import java.util.ArrayList;

// Получился просто отличный пример того как не надо делать =))
// Может быть когда-нибудь я это перепишу

class Grid {
    char[][] board;
    char[][] bruteBackup = new char[9][9];
    private boolean isBrute = false;
    public String[] usedInRow = new String[9];
    public String[] usedInColumn = new String[9];
    public String[] usedInBlock = new String[9];
    private String[][] brute = new String[9][9];
    private int emptyCount = 0;
    
    public Grid(char[][] b){
        this.board = b;

        for (int i = 0; i < 9; i++) {
            this.calcUsedInRow(i);
            this.calcUsedInColumn(i);
            this.calcUsedInBlock(i);
            for (int y = 0; y < 9; y++) {
                if (this.board[i][y] == '.') {
                    this.emptyCount++;
                }
            }
        }
        
    }

    private void calcEmpty() {
        this.emptyCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int y = 0; y < 9; y++) {
                if (this.board[i][y] == '.') {
                    this.emptyCount++;
                }
            }
        }
    }
    
    private void calcUsedInRow(int idx) {
        String result = "";
        for (int i = 0; i < this.board[idx].length; i++) {
            if (this.board[idx][i] != '.') {
                if (result.indexOf(this.board[idx][i]) == -1) {
                    result = result + this.board[idx][i];
                }
            }
        }

        this.usedInRow[idx] = result;
    }
    
    private void calcUsedInColumn(int idx) {
        String result = "";
        for (int i = 0; i < 9; i++) {
            if (this.board[i][idx] != '.') {
                if (result.indexOf(this.board[i][idx]) == -1) {
                    result = result + this.board[i][idx];
                }
            }
        }

        this.usedInColumn[idx] = result;
    }

    private void calcUsedInBlock(int idx) {
        int startRow = (idx / 3) * 3;
        int startColumn = (idx % 3) * 3;
        String result = "";

        for (int i = startRow; i < startRow + 3; i ++) {
            for (int y = startColumn; y < startColumn + 3; y++) {
                if (this.board[i][y] != '.') {
                    if (result.indexOf(this.board[i][y]) == -1) {
                        result = result + this.board[i][y];
                    }
                }
            }
        }

        this.usedInBlock[idx] = result;
    }

    public static int getBlockIdx(int row, int col) {
        return (row / 3) * 3 + (col / 3);
    }

    public int[] findVariant(boolean ... args) {
        for (int i = 0; i < 9; i++) {
            for (int y = 0; y < 9; y++) {
                String variants;

                if (this.board[i][y] == '.') {
                    variants = this.getVariants(i, y);

                    if (args.length == 0) {
                        if (variants.length() == 1) {
                            return new int[]{i, y};
                        }
                    } else {
                        if (variants.length() > 0 && variants.length() < 4 && args[0]) {
                            if (this.brute[i][y] == null) {
                                return new int[]{i, y};
                            } else {
                                variants = this.removeVariants(variants, this.brute[i][y]);

                                if (variants.length() > 0) {
                                    return new int[]{i, y};
                                }
                            }
                        }
                    }
                }
            }
        }

        return new int[0];
    }

    /**
     * Здесь определяеться какие значения еще не заполнены в блоке
     * если для доступного значения есть только один вариант, то он будет возвращен
     * @param idx Идентификатор блока
     * @return Возвращает координаты ячейки для однозначного заполнения или пустой массив
     */
    public HashMap<String, int[]> getBlockVariants(int idx) {
        // Получаем начальную строку
        int row = (idx / 3) * 3, col = (idx - row) * 3;

        // Карта вариантов по числам
        HashMap<String, ArrayList> blockMap = new HashMap<>();
        //Свободные ячейки блока
        ArrayList<int[]> emptyCells = new ArrayList<>();
        //Карта вариантов
        HashMap<String, int[]> availableVars = new HashMap<>();
        String variants = "123456789";

        for (int i = 0; i < this.usedInBlock[idx].length(); i++) {
            if (variants.indexOf(this.usedInBlock[idx].charAt(i)) != -1) {
                variants = variants.replace(this.usedInBlock[idx].charAt(i) + "", "");
            }
        }

        // Для начала нужно определить какие значения доступны для заполнения
        // Так же нужны координаты свободных ячеек
        for (int i = row; i < row + 3; i++) {
            for (int y = col; y < col + 3; y++) {
                if (this.board[i][y] == '.') {
                    emptyCells.add(new int[]{i, y});
                }
            }
        }

        // Бежим по числам которых нет в блоке
        for (int y = 0; y < variants.length(); y++) {
            // Бежим по пустым ячейкам блока
            for (int i = 0; i < emptyCells.size(); i++) {
                String vars = this.getVariants(emptyCells.get(i)[0], emptyCells.get(i)[1]);
                // Если в эту ячейку допустимо добавлять это число то добавим координаты в список
                if (vars.indexOf(variants.charAt(y)) != -1) {
                    if (blockMap.containsKey(variants.charAt(y) + "")) {
                        blockMap.get(variants.charAt(y) + "").add(new int[]{
                                emptyCells.get(i)[0], emptyCells.get(i)[1]
                        });
                    } else {
                        ArrayList<int[]> newCoords = new ArrayList<>();
                        newCoords.add(new int[]{emptyCells.get(i)[0], emptyCells.get(i)[1]});
                        blockMap.put(variants.charAt(y) + "", newCoords);
                    }
                }
            }
        }

        for (String key : blockMap.keySet()) {
            ArrayList varsList = blockMap.get(key);
            if (varsList.size() == 1) {
                int[] t = ((int[]) varsList.get(0));

                availableVars.put(key, t);
            }
        }

        return availableVars;
    }

    public String getVariants(int row, int col) {
        String variants = "123456789";
        String removeString = this.usedInRow[row] +
                this.usedInColumn[col] +
                this.usedInBlock[Grid.getBlockIdx(row, col)];

        variants = this.removeVariants(variants, removeString);

        return variants;
    }

    private String removeVariants(String vars, String filter) {
        for (int i = 0; i < filter.length(); i ++) {
            if (vars.indexOf(filter.charAt(i)) != -1) {
                vars = vars.replace(filter.charAt(i) + "", "");
            }
        }

        return vars;
    }

    private void fillCell(int i, int y) {
        char value = this.getVariants(i , y).charAt(0);

        this.fillCell(i, y, value);
    }

    private void fillCell(int i, int y, char val) {
        //System.out.println("Заполняем ячейку " + i + " / " + y + " значением " + val);
        this.board[i][y] = val;
        this.emptyCount--;
        this.calcUsedInRow(i);
        this.calcUsedInColumn(y);
        this.calcUsedInBlock(Grid.getBlockIdx(i, y));
    }

    private boolean tryBrute() {
        this.isBrute = true;
        int[] vars = this.findVariant(true);
        String values = "123456789";
        String filter = "";
        this.backupBoard();

        if (vars.length == 0) {
            return false;
        }

        filter += this.usedInRow[vars[0]];
        filter += this.usedInColumn[vars[1]];
        filter += usedInBlock[Grid.getBlockIdx(vars[0], vars[1])];
        if (brute[vars[0]][vars[1]] != null) {
            filter += brute[vars[0]][vars[1]];
        }

        values = this.removeVariants(values, filter);

        //System.out.println("Брут, варианты " + values);

        //System.out.println("Добавляем значение" + values.charAt(0) + " с координатими " +
        //        vars[0] + " / " + vars[1] + " в справочник");
        if (brute[vars[0]][vars[1]] != null) {
            brute[vars[0]][vars[1]] = brute[vars[0]][vars[1]] + (values.charAt(0) + "");
        } else {
            brute[vars[0]][vars[1]] = values.charAt(0) + "";
        }

        this.fillCell(vars[0], vars[1], values.charAt(0));

        return true;
    }

    private void backupBoard() {
        for (int i = 0; i < 9; i++) {
            for (int y = 0; y < 9; y++) {
                this.bruteBackup[i][y] = this.board[i][y];
            }
        }
    }

    private void restoreBoard() {
        for (int i = 0; i < 9; i++) {
            for (int y = 0; y < 9; y++) {
                this.board[i][y] = this.bruteBackup[i][y];
            }
        }
        for (int i = 0; i < 9; i++) {
            this.calcUsedInRow(i);
            this.calcUsedInColumn(i);
            this.calcUsedInBlock(i);
        }
    }

    public void trySolve() {
        int bruteCounter = 0;
        while (this.emptyCount > 0) {
            int empCountToBegin = this.emptyCount;
            for (int i = 0; i < 9; i++) {
                HashMap<String, int[]> blockVariant = this.getBlockVariants(i);
                for (String key : blockVariant.keySet()) {
                    //System.out.println("Заполнение по блоку");
                    this.fillCell(blockVariant.get(key)[0], blockVariant.get(key)[1], key.charAt(0));
                }
            }

            int[] variant = this.findVariant();

            if (variant.length != 0) {
                //System.out.println("Заполнение по клетке");
                this.fillCell(variant[0], variant[1]);
            }

            //System.out.println("Осталось заполнить " + this.emptyCount);

            if (empCountToBegin == this.emptyCount) {
                if (!this.isBrute) {
                    if (bruteCounter < 1000) {
                        bruteCounter++;
                        //System.out.println("Не удалось найти вариантов");
                        this.tryBrute();
                    } else {
                        for (int i = 0; i < 9; i++) {
                            for (int y = 0; y < 9; y++) {
                                //System.out.println(i + " / " + y + " = " + this.brute[i][y]);
                            }
                        }
                        break;
                    }
                } else {
                    this.isBrute = false;
                    //System.out.println("Бекап!!!");
                    this.restoreBoard();
                    this.calcEmpty();
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.println(this.board[i]);
        }
    }
}

class Solution {
    public void solveSudoku(char[][] board) {
        Grid grid = new Grid(board);
        grid.trySolve();
        System.out.println(grid.board);
    }
}