import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(" Enter a number for value of M [4...16] ");
        int m_size = in.nextInt();
        while (m_size > 16 || m_size < 4){
            System.out.println(" Please enter a number between 4 and 16 ");
            System.out.println(" Enter a number for value of M [4...16] ");
            m_size = in.nextInt();
        }

        Matrix m_matrix = Matrix.make_M_Matrix(m_size,m_size);
        int n_size = 0;
        int num = 0;
        do{
            Random random = new Random();
            num = random.nextInt(5)+1;
        }while (num*m_size > 16);

        n_size = num * m_size;



        Matrix n_matrix = Matrix.make_N_Matrix(n_size,n_size);

        System.out.println("The " + n_size + " by " + n_size + " N matrix is follow : ");
        System.out.println(n_matrix.toString());
        System.out.println();
        System.out.println("The " + m_size + " by " + m_size + " M dither matrix is follow : ");
        System.out.println(m_matrix.toString());



        Matrix result_dither = Matrix.ordered_dither(n_matrix,m_matrix,n_size,m_size);
        System.out.println("The ordered dithering resultant pictures is : ");
        System.out.println(result_dither.toString());


        Matrix halftone_matrix = Matrix.halftone(n_matrix,m_matrix,n_size,m_size);
        System.out.println("The halftone resultant pictures is : ");
        System.out.println(halftone_matrix.toString());




    }
}
