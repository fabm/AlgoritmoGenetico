package algoritmoGenetico;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 31/08/13
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public class Individuo {

    static public Individuo cria(int ind, int bits){
        return new Individuo(ind,bits);
    }

    public Individuo(int ind, int bits) {
        this.ind = ind;
        this.bits = bits;
    }

    private int ind;
    private int bits;
    private int[] pontosDeCorte;
    private SeparadorIntervalo separadorIntervalo = null;

    public void setSeparadorIntervalo(SeparadorIntervalo separadorIntervalo) {
        this.separadorIntervalo = separadorIntervalo;
    }

    public void setPontosDeCorte(int... pontosDeCorte) {
        this.pontosDeCorte = pontosDeCorte;
    }

    public String getStringBinarioSemPontosDeCorte(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits; i++) {
            sb.append((char) ('0' + ((ind >> (bits - i - 1)) & 1)));
        }
        return  sb.toString();
    }

    public String getStringBinario() {
        if (separadorIntervalo == null)
            throw new RuntimeException("não foi definido o objecto SeparadorIntervalo");

        int[] pci = new int[pontosDeCorte.length];

        for (int i = 0; i < pci.length; i++) {
            pci[i] = 0;
            for (int j = 0; j < pci.length - i; j++) {
                pci[i] += pontosDeCorte[j];
            }
            pci[i] = bits - pci[i];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(separadorIntervalo.getInicio());
        for (int i = 0; i < bits; i++) {
            boolean corte = false;
            for (int j = 0; j < pci.length; j++) {
                if (bits-pci[j] == i) {
                    sb.append(separadorIntervalo.getInicioIntervalo());
                    corte = true;
                    break;
                }
            }
            sb.append((char) ('0' + ((ind >> (bits - i - 1)) & 1)));
            if (corte)
                sb.append(separadorIntervalo.getFimIntervalo());

        }
        //TODO teste 1
        sb.append(separadorIntervalo.getFim());
        return sb.toString();
    }


    public static void main(String[] args) {
        Individuo ind = new Individuo(666, 13);
        ind.setPontosDeCorte(13);
        ind.setSeparadorIntervalo(new IntervaloConsola());
        System.out.println(ind.getStringBinario());
        System.out.println(ind.getStringBinario());
    }
}
