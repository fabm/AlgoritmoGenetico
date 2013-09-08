package algoritmoGenetico;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 07/09/13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class StringConcatenador {
    static public StringConcatenador cria() {
        return new StringConcatenador();
    }
    static public StringConcatenador cria(Object preencimento) {
        return new StringConcatenador(preencimento);
    }

    private StringConcatenador(){
        preencimento = ' ';
    }

    private StringConcatenador(Object preenchimento){
        this.preencimento = preenchimento;
    }

    private StringBuilder sb = new StringBuilder();
    private Object preencimento;

    public void novo() {
        sb = new StringBuilder();
    }

    public StringConcatenador acresentaE(Object obj, int tamanho) {
        String str = obj.toString();
        int tamanhoString = str.length();
        if (tamanhoString > tamanho)
            throw new RuntimeException("O tamanho da string é superior ao tamanho alocado");
        for (int i = tamanhoString; i < tamanho; i++) {
            sb.append(preencimento);
        }
        sb.append(obj);
        return this;
    }

    public StringConcatenador acresentaD(Object obj, int tamanho) {
        String str = obj.toString();
        int tamanhoString = str.length();
        if (tamanhoString > tamanho)
            throw new RuntimeException("O tamanho da string é superior ao tamanho alocado");
        sb.append(obj);
        for (int i = 0; i < tamanhoString; i++) {
            sb.append(preencimento);
        }
        return this;
    }

    public StringConcatenador acrescenta(Object obj) {
        sb.append(obj);
        return this;
    }

    public StringConcatenador novaLinha(){
        sb.append('\n');
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public static void main(String args[]) {
        StringConcatenador s = StringConcatenador.cria('+').acresentaE(
                StringConcatenador.cria().
                        acrescenta('i').
                        acrescenta(15).
                        acrescenta(":"), 10).
                acresentaD(5, 2).
                acrescenta("teste");
        System.out.println(s);
    }
}
