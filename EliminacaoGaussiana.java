import java.util.Scanner;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class EliminacaoGaussiana {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            LinearSystem problem = createSystemOfFile("Linear_System.txt");
//            problem.displaySystem();
            problem.eliminationGauss();
            problem.displaySystem();
            problem.calculation();
            problem.getMatrizX();
        } catch (FileNotFoundException exc) {
            System.out.println("Arquivo não encontrado: " + exc.getMessage());
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

    private static LinearSystem createSystemOfInput() {
        System.out.printf("Enter with row and column numbers [NxN] = ");
        int n = input.nextInt();
        double[][] matriz = new double[n][n];
        double[] vetor = new double[n];
        for (int i = 0; i < (n * n); i++) {
            int row = i / n;
            int column = i % n;
            matriz[row][column] = input.nextDouble();
        }
        for (int column = 0; column < n; column++) {
            vetor[column] = input.nextDouble();
        }
        return new LinearSystem(matriz, vetor);
    }

    public static LinearSystem createSystemOfFile(String namefile) throws FileNotFoundException, Exception{
        File arq = new File(namefile);
        Scanner read = new Scanner(arq);
        int n = Integer.valueOf(read.nextLine());
        double[][] matriz = new double[n][n];
        double[] vetor = new double[n];
        int i = 0;
        while (read.hasNextLine()) {
            String[] line = read.nextLine().split(" ");
            if (line.length == n) {
                if (!read.hasNextLine()) {
                    vetor = toArrayOfDouble(line);
                } else {
                    matriz[i++] = toArrayOfDouble(line);
                }
            } else {
                throw new Exception("Linha não é de uma matriz quadrada.");
            }
        }
        return new LinearSystem(matriz, vetor);
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

/* Class representativa de um systema linear A.x = b
   @Matriz   = A => double[][]
   @Vetor    = b => double[]
   @Matrix_x = x => double[]
 */
class LinearSystem {
    private double[][] matriz;
    private double[] vetor;
    private double[] matriz_x;
    private int n;

    public LinearSystem(double[][] matriz, double[] vetor) {
        this.matriz = matriz;
        this.vetor = vetor;
        this.n = this.matriz.length;
        this.matriz_x = new double[this.n];
    }

    public void displaySystem() {
        System.out.println("|--------------------------------------------------------------------------------|--------------|");
        System.out.printf("|");
        for (int i = 0; i < this.n * this.n; i++) {
            int row = i / this.n;
            int column = i % this.n;
            System.out.printf(" %.2f\t", this.matriz[row][column]);
            if (column == this.n - 1) {
                System.out.printf(" |  %.2f\t|\n", this.vetor[row]);
                System.out.printf("|");
            }
        }
        System.out.println("--------------------------------------------------------------------------------|--------------|");
    }

    public void eliminationGauss() {
        for (int i = 0; i < this.n; i++) {
            if(this.matriz[i][i] == 0){
                this.changeRow(i);
            }
            for (int j = i + 1; j < this.n; j++) {
                double m = this.matriz[j][i] / this.matriz[i][i];
                this.vetor[j] -= m * vetor[i];
                for (int k = 0; k < this.n; k++) {
                    this.matriz[j][k] -= m * this.matriz[i][k];
                }
            }
        }
    }

    public void changeRow(int row){

        double[] aux = this.matriz[row];
        this.matriz[row] = this.matriz[row+1];
        this.matriz[row+1] = aux;

    }

    public void calculation() {
        this.matriz_x[this.n - 1] = this.matriz[this.n - 1][this.n - 1];
        for (int i = this.n - 1; i >= 0; i--) {
            double soma = 0;
            for (int j = i + 1; j < this.n; j++) {
                soma += this.matriz[i][j] * this.matriz_x[j];
            }
            this.matriz_x[i] = (this.vetor[i] - soma) / this.matriz[i][i];
        }
    }

    public void getMatrizX() {
        System.out.printf("\nx = [ ");
        for (double el : this.matriz_x) {
            System.out.printf(" %.2f\t", el);
        }
        System.out.printf(" ]\n");
    }
}