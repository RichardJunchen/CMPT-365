
public class Matrix {
    public final int NUM_ROWS;
    public final int NUM_COLS;
    public double [][] values;

    public Matrix(int rows, int cols) {
        NUM_ROWS = rows;
        NUM_COLS = cols;
        values = new double[NUM_ROWS][NUM_COLS];
    }

    public static Matrix makeMatrix (int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0 ; i < rows; i++){
            for (int j = 0 ; j <cols; j++){
                matrix.values[i][j] = 0;
            }
        }
        return matrix;
    }

}
