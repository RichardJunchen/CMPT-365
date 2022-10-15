import java.util.ArrayList;
import java.util.Random;

public class Matrix {
    public final int NUM_ROWS;
    public final int NUM_COLS;
    private final int[][] values;

    public Matrix (int rows, int cols){
        NUM_ROWS = rows;
        NUM_COLS = cols;
        values = new int[NUM_ROWS][NUM_COLS];
    }

    public static ArrayList<Integer> makeRandomArrayList (int size){
        ArrayList<Integer> list = new ArrayList<Integer>();

        Random random = new Random();

        while (list.size() < size*size){
            int num = random.nextInt(size*size);
            boolean check = true;
            for (int temp : list) {
                if (num == temp)
                    check = false;
            }
            if (check)
                list.add(num);
        }
        return list;
    }

    public static Matrix make_M_Matrix (int rows, int cols){
        ArrayList<Integer> list = makeRandomArrayList(rows);
        Matrix matrix = new Matrix(rows,cols);
        int list_index = 0;
        for (int i = 0; i< rows; i++){
            for (int j =0; j< cols; j++){
                matrix.values[i][j] = list.get(list_index);
                list_index++;
            }
        }
        return matrix;
    }

    public static Matrix make_N_Matrix (int rows, int cols) {
        Random random = new Random();
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.values[i][j] = random.nextInt(256);
            }
        }
        return matrix;
    }

    public static Matrix proportion_change (Matrix n, int n_size, int m_size){
        Matrix temp = new Matrix(n_size,n_size);
        int num = m_size * m_size;
        num+=1;
        for (int i = 0; i < n_size; i++){
            for (int j = 0; j< n_size; j++){
                temp.values[i][j] = n.values[i][j]/(256/num);
            }
        }
        return temp;
    }

    public static Matrix ordered_dither (Matrix n, Matrix m, int n_size, int m_size ){
        Matrix temp = proportion_change (n,n_size,m_size);
        System.out.println(" After the proportion changed, the N matrix is : ");
        System.out.println(temp.toString());
        Matrix result = new Matrix(n_size,n_size);
        for (int i = 0; i < n_size; i++){
            for (int j =0; j < n_size; j++){
                int index1 = i % m_size;
                int index2 = j % m_size;
                if (temp.values[i][j] > m.values[index1][index2])
                    result.values[i][j] = 1;
                else
                    result.values[i][j] = 0;
            }
        }
        return result;
    }

    public static void print_dots (Matrix output_matrix , Matrix m, int m_size, int size, int start,ArrayList<Integer>temp){
        int index = -1;
        start = (start * m_size) -1;


        for (int i =0; i< m_size; i++){
            int count = 0;
            index++;
            start++;
            for (int j =0; j < size; j+=m_size){
                for (int k = 0; k< m_size; k++){
                    if (m.values[index][k] < temp.get(count))
                        output_matrix.values[start][j+k] = 1;
                    else
                        output_matrix.values[start][j+k] = 0;
                }
                count++;
            }
        }
    }

    public static void process_halftone (Matrix output_matrix, Matrix n, Matrix m, int n_size, int m_size, int size){

        for (int i = 0; i< n_size; i++){
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < n_size; j++){
                temp.add(n.values[i][j]);
            }
            print_dots(output_matrix,m,m_size,size,i,temp);
        }

    }
    public static Matrix halftone (Matrix n, Matrix m, int n_size, int m_size){
        int size = n_size * m_size;
        Matrix after = proportion_change(n,n_size,m_size);
        Matrix output_matrix = new Matrix(size,size);
        process_halftone (output_matrix,after,m,n_size,m_size,size);
        return output_matrix;
    }

    @Override
    public String toString(){
        String ret = "";
        for (int i =0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++){
                ret +=values [i][j];
                if (!(j == NUM_COLS -1 ))
                    ret += " ";
            }
            ret += "\n";
        }
        return ret;
    }
}
