import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void make_transpose(Matrix transform_matrix, Matrix transpose_matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transpose_matrix.values[i][j] = transform_matrix.values[j][i];
            }
        }
    }

    public static void processing (Matrix input_matrix, Matrix transform_matrix,Matrix result_matrix, int size) throws IOException {
        Matrix temp_matrix = new Matrix(size,size);
        Matrix transpose_matrix = new Matrix(size,size);
        make_transpose(transform_matrix,transpose_matrix,size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {

                double result = 0;
                for (int k = 0; k < size; k++){
                    result += transform_matrix.values[i][k] * input_matrix.values[k][j];
                }
                temp_matrix.values[i][j] = result;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {

                double result = 0;
                for (int k = 0; k < size; k++){
                    result += temp_matrix.values[i][k] * transpose_matrix.values[k][j];
                }
                result_matrix.values[i][j] = Math.round(result);
            }
        }

//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                System.out.println(result_matrix.values[i][j]);
//            }
//        }
//        System.exit(0);
        System.out.println(" Generating a output file...");
        FileWriter myWriter = new FileWriter("Q2.out",true);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                myWriter.write((int) result_matrix.values[i][j]+" ");
            }
            myWriter.write("\n");
        }
        myWriter.write("\n");
        myWriter.close();
    }


    public static void set_transform_matrix(Matrix transform_matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0)
                    transform_matrix.values[i][j] = Math.sqrt((1.0 / size));
                else {
                    double top = ((2 * j) + 1) * i * 3.1415926;
                    double alpha = Math.sqrt((2.0 / size));
                    transform_matrix.values[i][j] = alpha * Math.cos(top / (2 * size));
                }
            }
        }
    }




    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new FileReader("Q2.in"));
        System.out.println(" Reading input file successful !");
        int size = 0;
        int count = 0;
        Matrix transform_matrix = null;
        Matrix input_matrix = null;
        Matrix result_matrix = null;

        boolean check_state = true;
        boolean can_be_continue = true;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.length() <= 2)
                check_state = true;

            if (check_state && !can_be_continue){
                System.out.println(" Reading input file successful !");
                processing (input_matrix, transform_matrix,result_matrix,size);
                can_be_continue = true;
                count = 0;
            }

            if (check_state && can_be_continue){
                size = Integer.parseInt(line);
                transform_matrix = new Matrix(size,size);
                input_matrix = new Matrix(size,size);
                result_matrix = new Matrix(size,size);
                set_transform_matrix(transform_matrix,size);
                check_state = false;
                can_be_continue = false;
            }
            else{
                String[] numbersArray = line.split(" ");
                for (int i = 0 ; i < size; i++){
                    input_matrix.values[count][i] = Integer.parseInt(numbersArray[i]);
                }
                count += 1;
            }
        }
        processing (input_matrix, transform_matrix,result_matrix,size);
    }
}