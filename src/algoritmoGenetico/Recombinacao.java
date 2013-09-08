package algoritmoGenetico;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 02/09/13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class Recombinacao {
    private final int[] individuos;
    private double valorAleatorio;
    private int nBits;
    private double probDeRecombinar;
    private int filho1;
    private int filho2;
    private int[] pontosDeCorte;
    private boolean ocorreu = false;
    private int indice1;
    private int indice2;



    public void geraPontosDeCorte(int inicio, int fim) {
        int total = fim - inicio;
        double[] rs = new double[pontosDeCorte.length];
        double soma = 0;
        for (int i = 0; i < pontosDeCorte.length; i++) {
            double r = Math.random();
            rs[i] = r;
            soma += r;
        }
        soma += Math.random();
        for (int i = 0; i < pontosDeCorte.length; i++) {
            rs[i] = (rs[i] / soma);
            pontosDeCorte[i] = (int) (rs[i] * total + inicio);
        }
    }

    public Recombinacao(int[] individuos, int indice1, int indice2,int nBits, int qtDePontosDeCorte, double probDeRecombinar) {
        this.individuos = individuos;
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.nBits = nBits;
        pontosDeCorte = new int[qtDePontosDeCorte];
        this.probDeRecombinar = probDeRecombinar;
        this.valorAleatorio = Math.random();
        geraFilhos();
    }



    public boolean ocorreu() {
        return ocorreu;
    }

    private int inverterMascara(int mascara) {
        return mascara ^ ((1 << nBits) - 1);
    }

    private int mascara() {
        geraPontosDeCorte(1, nBits - 2);
        int mascara = 0;
        int soma = 0;
        for (int i = 0; i < pontosDeCorte.length + 1; i++) {
            int tc;//tamanho do corte
            if (i == pontosDeCorte.length) tc = nBits - soma;
            else tc = pontosDeCorte[i];
            soma += tc;
            mascara <<= tc;
            if (i % 2 == 0) {
                int aux = (1 << tc) - 1;//quantidade de bits a 1 que cria
                mascara |= aux;//preenche a quantidade deslocada com 1's
            }
        }
        return mascara;
    }

    private void geraFilhos() {
        if (valorAleatorio > probDeRecombinar) {
            ocorreu = false;
            return;
        }
        ocorreu = true;

        int mascara = mascara();

        filho1 = mascara & individuos[indice1];
        filho2 = mascara & individuos[indice2];
        mascara = inverterMascara(mascara);
        filho1 |= (mascara & individuos[indice2]);
        filho2 |= (mascara & individuos[indice1]);
    }

    public int[] getPontosDeCorte() {
        return pontosDeCorte;
    }

    public int getFilho1() {
        return filho1;
    }

    public int getFilho2() {
        return filho2;
    }

    public double getValorAleatorio() {
        return valorAleatorio;
    }

    public void setIndice1(int indice1) {
        this.indice1 = indice1;
    }

    public void setIndice2(int indice2) {
        this.indice2 = indice2;
    }

    public int getIndice1() {
        return indice1;
    }

    public int getIndice2() {
        return indice2;
    }
}
