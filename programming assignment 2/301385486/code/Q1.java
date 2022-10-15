import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void initialization_dic(ArrayList<Dic_Element> dictionary) {
        Dic_Element A = new Dic_Element(0, "A");
        Dic_Element B = new Dic_Element( 1, "B");
        Dic_Element C = new Dic_Element( 2, "C");
        dictionary.add(A);
        dictionary.add(B);
        dictionary.add(C);
    }

    public static boolean check_include(String test, ArrayList<Dic_Element> dictionary) {
        for (Dic_Element dic_element : dictionary) {
            if (test.equals(dic_element.getElement())) {
                return true;
            }
        }
        return false;
    }
    public static void print_result(ArrayList<String> line, ArrayList<Integer> output, ArrayList<Dic_Element> dictionary, int index) throws IOException {


        FileWriter myWriter = new FileWriter("Q1.out",true);
        myWriter.write("Input sequence : " + line.get(index)+ "\n");
        String x = output.toString().replace('[', ' ');
        x = x.replace(']', ' ');
        x = x.replace(',', ' ');
        myWriter.write("Output sequence : " + x + "\n");
        myWriter.write("\n");
        myWriter.write("Dicitionary : " + "\n");
        for (Dic_Element dic_element : dictionary) {
            myWriter.write(dic_element.getCode() + " " + dic_element.getElement() );
            myWriter.write("\n");
        }
        myWriter.write("\n");
        myWriter.close();


    }
    public static void encoding( ArrayList<String> line, int continue_code) throws IOException {
        for (int l = 0; l < line.size(); l++) {
            //initial element
            ArrayList<Integer> output = new ArrayList<Integer>();
            String s = "";
            String c = "";
            String test = "";
            //initial dic
            ArrayList<Dic_Element> dictionary = new ArrayList<>();
            initialization_dic(dictionary);
            continue_code = 3;
            char[] temp = line.get(l).toCharArray();
            s = String.valueOf(temp[0]);

            for (int i = 0; i < temp.length -1 ; i++) {
//            System.out.println("i is "+ i);
                c = String.valueOf(temp[i + 1]);
                test = s + c;
//            System.out.println("s = " + s );
//            System.out.println("c = " + c );
//            System.out.println("test = " + test );
                if (!check_include(test, dictionary)) {
                    for (Dic_Element dic_element : dictionary) {
                        if (s.equals(dic_element.getElement()))
                            output.add(dic_element.getCode());
                    }
                    Dic_Element new_one = new Dic_Element(continue_code, test);
                    dictionary.add(new_one);
                    continue_code += 1;
                    s = c;
                } else {
                    s = test;

                }
            }
            for (Dic_Element dic_element : dictionary) {
                if (s.equals(dic_element.getElement()))
                    output.add(dic_element.getCode());
            }

//            test = String.valueOf(temp[temp.length-1]);
            print_result(line,output,dictionary,l);
        }
    }
    public static boolean check_length(String line){
        return line.length() > 64;
    }

    public static void main(String[] args) throws IOException {
        File output_file = new File("Q1.out");
        ArrayList<String> data = new ArrayList<String>();
        System.out.println();
        System.out.println(" Generating a output file...");
        Scanner in = new Scanner(new FileReader("Q1.in"));
        System.out.println(" Reading input file successful !");

        int continue_code = 3;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (check_length(line)) {
                System.out.println("The length input : " + line.toString() + "is over the 64! ");
                continue;
            }
            data.add(line);
        }
        System.out.println(" Encoding...");
        encoding(data,continue_code);
        System.out.println(" Encoding successful !");
    }
}
