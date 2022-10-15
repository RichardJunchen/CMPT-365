import java.util.ArrayList;

public class Main {

    public static ArrayList<Integer> convert_to_YCoCg(int y, int u, int v){

        ArrayList<Integer> temp = new ArrayList<Integer>();
        int r = y + v;
        int g = (int) (y - 0.1942 * u - 0.5094 * v);
        int b = y + u;

        int final_y = (int) (0.5 * g + 0.25 * b + 0.25 * r);
        int co = (int) (-0.5 * b + 0.5 * r);
        int cg = (int) (0.5 * g - 0.25 * b - 0.25 * r);

        temp.add(final_y); temp.add(co); temp.add(cg);

        return temp;
    }
    public static ArrayList<Integer> modify_one(int y, int u, int v){

        ArrayList<Integer> temp = new ArrayList<Integer>();

        int final_y = (int) ( y + 0.153 * u - 0.005 * v);
        int co = (int) (- 0.5 * u + 0.5 * v);
        int cg = (int) (- 0.347 * u - 0.505 * v);

        temp.add(final_y); temp.add(co); temp.add(cg);

        return temp;
    }
    public static ArrayList<Integer> modify_two(int y, int u, int v){

        ArrayList<Integer> temp = new ArrayList<Integer>();

        int co = (int) (- 0.5 * u + 0.5 * v);
        int cg = (int) (- 0.347 * u - 0.505 * v);

        temp.add(co); temp.add(cg);

        return temp;
    }

    public static void main(String[] args) {
	int y = 156;
	int u = 28;
	int v = 37;
	long ans = 0;
	ArrayList<Integer> result = new ArrayList<Integer>();

	long startTime = System.nanoTime();
	for (int i = 0; i< 1000000; i++){
//        long startTime = System.nanoTime();

        //result = convert_to_YCoCg(y,u,v);
        result = modify_one (y,u,v);
        //result = modify_two (y,u,v);

//        long endTime = System.nanoTime();
//        ans = ans + (endTime - startTime);
    }
	long endTime = System.nanoTime();
	ans = (endTime - startTime);

	System.out.println();
	System.out.println( "After converting, the Y = " + result.get(0) + " Co = " + result.get(1) + " Cg = " + result.get(2));


        //System.out.println( "After converting, the Y = " + y + " Co = " + result.get(0) + " Cg = " + result.get(1));

        System.out.println( "The total sum of all running time is " + ans + " ns ");
        System.out.println("The average running time is " + ans/1000000 + " ns ");
    }
}
