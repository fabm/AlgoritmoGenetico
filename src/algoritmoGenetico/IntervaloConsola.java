package algoritmoGenetico;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 05/09/13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class IntervaloConsola implements SeparadorIntervalo {
    String inicial;
    String fin;


    @Override
    public String getInicio() {
        return "";
    }

    @Override
    public String getFim() {
        return "";
    }

    @Override
    public String getInicioIntervalo() {
        return inicial;
    }

    @Override
    public String getFimIntervalo() {
        return fin;
    }

    public String getInicial() {
        return inicial;
    }

    public void setInicial(String inicial) {
        this.inicial = inicial;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}
