package algoritmoGenetico;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 02/09/13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public class Mutacao {
    private int[] individuos;
    private int indiceI1;
    private int indiceI2;
    private int geneI1;
    private int geneI2;
    private int nBits;
    private int filho1;
    private int filho2;

    static public Mutacao cria(int[] individuos, int nBits){
        return new Mutacao(individuos,nBits);
    }

    public Mutacao(int[] individuos, int nBits) {
        this.individuos = individuos;
        this.nBits = nBits;
        gera();
    }

    public int getIndiceI1() {
        return indiceI1;
    }

    public int getIndiceI2() {
        return indiceI2;
    }

    public int getGeneI1() {
        return geneI1;
    }

    public int getGeneI2() {
        return geneI2;
    }

    public int getFilho1() {
        return filho1;
    }

    public int getFilho2() {
        return filho2;
    }

    private void gera() {
        do {
            indiceI1 = (int) (Math.random() * individuos.length);
            indiceI2 = (int) (Math.random() * individuos.length);
        } while (indiceI1 == indiceI2);
        geneI1 = (int) (Math.random() * nBits);
        geneI2 = (int) (Math.random() * nBits);

        //guarda o bit em causa na posição menos significativas
        int bit1 = (individuos[indiceI1] >> geneI1) & 1;
        int bit2 = (individuos[indiceI2] >> geneI2) & 1;
        //coloca a 0 o bit a ser substituido
        filho1 = individuos[indiceI1] & (((1 << nBits) - 1) ^ (1 << geneI1));
        filho2 = individuos[indiceI2] & (((1 << nBits) - 1) ^ (1 << geneI2));
        // substitui o bit correspondentes aos dois individuos
        filho1 |= bit2 << geneI1;
        filho2 |= bit1 << geneI2;
    }
}
