import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class IterativeMethods {
    //static Scanner input = new Scanner(system.in);
    public static void main(String[] args){
        try {
            Gauss obj = createSystemOfFile(args[0]);
            int method = Integer.valueOf(args[1]);
            obj.solveSystem(method);
        }catch (FileNotFoundException exc){
            System.out.println("Arquivo não encontrado: " + exc.getMessage());
        }catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }

    public static Gauss createSystemOfFile(String namefile) throws FileNotFoundException, Exception{
        File arq = new File(namefile);
        Scanner read = new Scanner(arq);
        double erro = Double.valueOf(read.nextLine());
        int n = Integer.valueOf(read.nextLine());
        double[][] matriz = new double[n][n];
        double[] vetor = new double[n];
        double[] x = new double[n];
        int i = 0;
        while (read.hasNextLine()) {
            String line = read.nextLine();
            if(line.isEmpty()){
                vetor = toArrayOfDouble(read.nextLine().split(" "));
            }else if (!read.hasNextLine()) {
                x = toArrayOfDouble(line.split(" "));
            } else {
                matriz[i++] = toArrayOfDouble(line.split(" "));
            }
        }
        return new Gauss(matriz, vetor,x,erro);
    }

    public static double[] toArrayOfDouble(String[] line) {
        int size = line.length;
        double[] row = new double[size];
        for (int i = 0; i < size; i++) {
            row[i] = Double.valueOf(line[i]);
        }
        return row;
    }

}

class Gauss{
    public double dr;
    public double []x_anterior;
    public double []x_atual;
    public double [][] a;
    public double [] b;
    public double []d;
    public double e;
    private int k;

    public Gauss(double [][]a,double[]b, double []x, double e){
        this.a = a;
        this.b = b;
        this.e = e;
        this.d = new double[x.length];
        this.x_anterior = x;
        this.x_atual = new double[x.length];
        this.dr = 1;
        this.k = 0;
    }

    private double[] gaussSidel(){
        if(this.dr < this.e){
            return this.x_atual;
        }

        for (int i = 0; i < a.length;i++){
            double sum = 0;
            for (int j = 0; j < a.length; j++){
                if(j != i){
                    sum += this.a[i][j] * this.x_atual[j];
                }
            }
            this.x_atual[i] = (this.b[i] - sum) / this.a[i][i];
            d[i] = Math.abs(this.x_atual[i] - this.x_anterior[i]);
        }
        this.dr = maxArray(this.d) / maxArray(this.x_atual);
        this.x_anterior = this.x_atual.clone();
        k++;
        return this.gaussSidel();
    }

    public void showMatrix(){
        int sz = a.length;
        for (int i = 0; i < sz; i++){
            for (int j = 0; j < sz; j++ ){
                System.out.printf("%f\t", a[i][j]);
            }
            System.out.printf("%f\n", b[i]);
        }
    }


    private double[] gaussJacobi(){
        if(this.dr < this.e){
            return this.x_atual;
        }

        for (int i = 0; i < a.length;i++){
            double sum = 0;
            for (int j = 0; j < a.length; j++){
                if(j != i){
                    sum += this.a[i][j] * this.x_anterior[j];
                }
            }
            this.x_atual[i] = (this.b[i] - sum) / this.a[i][i];
            d[i] = Math.abs(this.x_atual[i] - this.x_anterior[i]);
        }

        this.dr = maxArray(this.d) / maxArray(this.x_atual);

        this.x_anterior = this.x_atual.clone();
        k++;
        return this.gaussJacobi();
    }

    public void solveSystem(int method){
        if(method == 1){
            System.out.println("Método Gauss-Jacobi");
            this.x_atual = this.gaussJacobi();

        }else{
            System.out.println("Método Gauss-Sidel");
            this.x_atual = this.gaussSidel();
        }
        this.displayResult();
    }

    public void displayResult(){
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><>");
        for(int i = 0; i < this.x_atual.length; i++){
            System.out.printf("x[%d] =\t\t\t %.4f \n",i,this.x_atual[i]);
        }
        System.out.printf("Erro final =\t\t %.4f\n",maxArray(this.d));
        System.out.printf("Erro relativo final =\t %.4f\n",this.dr);
        System.out.printf("K = %d", this.k);
        System.out.println("\n<><><><><><><><><><><><><><><><><><><><><><><><>");
    }


    private double maxArray(double []array){
        double max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}