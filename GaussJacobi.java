import java.util.Scanner;
import java.util.Arrays;


public class GaussJacobi {
    //static Scanner input = new Scanner(system.in);
    public static void main(String[] args){

        //init()
        double [][] a = {{5,2},{2,4}};
        double[]b = {9,10};
        double []x = {0,0};
        double e = 0.01;
        Jacobi obj = new Jacobi(a,b,x,e);
        obj.solveSystem();
        obj.displayResult();
    }
}

class Jacobi{
    public double dr;
    public double []x_anterior;
    public double []x_atual;
    public double [][] a;
    public double [] b;
    public double []d;
    public double e;

    public Jacobi(double [][]a,double[]b, double []x, double e){
        this.a = a;
        this.b = b;
        this.e = e;
        this.d = new double[x.length];
        this.x_anterior = x;
        this.x_atual = new double[x.length];
        this.dr = 1;
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

        return this.gaussJacobi();
    }

    public void solveSystem(){
        this.x_atual = this.gaussJacobi();
    }

    public void displayResult(){
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><>");
        for(int i = 0; i < this.x_atual.length; i++){
            System.out.printf("x[%d] =\t\t\t %.4f \n",i,this.x_atual[i]);
        }
        System.out.printf("Erro final =\t\t %.6f\n",maxArray(this.d));
        System.out.printf("Erro relativo final =\t %f",this.dr);
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