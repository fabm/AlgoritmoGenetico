package algoritmoGenetico;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 30/08/13
 * Time: 10:59
 * To change this template use File | Settings | File Templates.
 */
public class Testes {
    public static int doisElevadoA(int n){
        return 1<<n;
    }

    public static int[] roleta(int tamanho){
        int[] prob = new int[tamanho];
        int soma = 0;
        for (int i = 0; i < prob.length; i++) {
            int r = (int) Math.round(Math.random() * 100);
            prob[i] = r;
            soma += r;
        }
        int[] roleta = new int[tamanho];
        for(int i = 0;i < roleta.length;i++){
            roleta[i]=prob[i]*100/soma;
        }
        return roleta;
    }

    public static void dezRoletas(){
        for(int i=0;i<100;i++){
            System.out.println("--inicio--");
            int[] roleta = Testes.roleta(10);
            for(int j=0;j<10;j++){
                System.out.println(roleta[j]);
            }
        }
    }

    public static int[] mascara(int[] pc){
        int mascara1=0;
        int mascara2=0;
        int pcanterior = 0;
        int soma=0;
        for(int i=0;i<4;i++){
            int tc=0;
            //if(i==4)tc = 13-;
            //int tc = pc[i]-pcanterior;//tamanho do corte
            pcanterior = pc[i];
            int aux=(1<<tc)-1;
            System.out.println(tc);
            mascara1<<=tc;
            mascara2<<=tc;
            if(i%2==0){
                mascara1|=aux;
            }else
                mascara2|=aux;
        }
        return new int[]{mascara1,mascara2};
    }

    public static void ateSairXOu10000Tentativas(int x){
        int r= 0;
        for (int i = 0; i < 10000; i++) {
            r = (int) Math.round(Math.random() * 100);
            if(r==x){
                System.out.println("saiu 100 à "+i+"ª tentativa");
                break;
            }
        }
    }

    public static void main(String str[]){
    }
}
