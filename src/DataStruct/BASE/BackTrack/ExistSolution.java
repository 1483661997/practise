package DataStruct.BASE.BackTrack;

/**
 * 79. 单词搜索
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 */

public class ExistSolution {
    private boolean[][] isVisited;
    private boolean valid;

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        valid = false;
        isVisited = new boolean[m][n];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(board[i][j] == word.charAt(0)){

                    search(board, word,1, i, j);
                }
                if(valid) return valid;
            }
        }
        return valid;
    }

    public void search(char[][] board, String word, int pos, int x, int y){
        if(pos == word.length()){
            valid = true;
            return ;
        }

        int[] row = new int[]{-1,1,0,0};
        int[] col = new int[]{0,0,-1,1};
        isVisited[x][y] = true;
        for(int i = 0; i < 4 && !valid; i++){
            int newRow = x + row[i];
            int newCol = y + col[i];
            if(newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && !isVisited[newRow][newCol]){
                if(board[newRow][newCol] == word.charAt(pos)){

                    search(board, word, pos+1, newRow, newCol);
                }
            }
        }
        isVisited[x][y] = false;

    }
}
