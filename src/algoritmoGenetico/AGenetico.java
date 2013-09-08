package algoritmoGenetico;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: francisco
 * Date: 30/08/13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class AGenetico {
    private int iteracoes = 2;
    private int[] individuos;
    private double maxProbRecombinacao;
    private int nBits;
    private int qtPontosDeCorte = 3;
    private int elitismo = 2;

    public AGenetico(int nBits) {
        this.nBits = nBits;
        simulaEntrada();
    }

    private void pedidoAoUtilizador() {
        iteracoes = insereInteiro("Número de iterações", "Número de iterações inválido");
        individuos = new int[insereInteiro("Número de individuos da população", "Número de individuos inválido")];
        maxProbRecombinacao = 100.0 / insereProbabilidadeReproducao();
    }

    private double fAvaliacao(int x, int tamDom, int limEsq) {
        return (limEsq + ((double) (x * tamDom) / ((1 << nBits) - 1)));
    }

    private int insereProbabilidadeReproducao() {
        while (true) {
            int i = insereInteiro("Probabilidade de reprodução de 50 a 80", "Probabilidade de reprodução inválido");
            if (i >= 50 && i <= 80) {
                return i;
            }
            System.out.println("O número não se encontra entre 50 e 80");
        }
    }

    public double[][] avaliacaoEQualidade() {
        double[] avaliacao = new double[individuos.length];
        double[] qualidade = new double[individuos.length];
        for (int i = 0; i < avaliacao.length; i++) {
            double ava = fAvaliacao(individuos[i], 5, 1);
            avaliacao[i] = ava;
            qualidade[i] = q(ava);
        }
        return new double[][]{
                avaliacao, qualidade
        };
    }


    private double q(double x) {
        double resultado = x - 3;
        resultado *= resultado;
        return resultado;
    }


    private double[] roleta(int tamanho) {
        double[] roleta = new double[tamanho];
        for (int i = 0; i < tamanho; i++) {
            roleta[i] = Math.random();
        }
        return roleta;
    }

    private double[] criaSegmentos() {
        double[] roleta = roleta(individuos.length);

        double[] segmentos = new double[individuos.length];
        double soma = 0.0;
        for (int i = 0; i < individuos.length; i++) {
            soma += roleta[i];
            segmentos[i] = soma;
        }

        for (int i = 0; i < segmentos.length; i++) {
            segmentos[i] = segmentos[i] / soma;
        }
        return segmentos;
    }

    private int[] geraSeleccao(double[] qualidade, double[] segmentos, double[] roleta) {
        int[] sel = new int[individuos.length];
        sel[0] = 0;

        ArrayList<Integer> resto = new ArrayList<Integer>(qualidade.length);

        Integer maior = 0;
        for (int i = 0; i < qualidade.length; i++) {
            resto.add(i);
            if (qualidade[maior] < qualidade[i]) {
                maior = i;
            }
        }
        sel[0] = maior;
        resto.remove(maior);

        int k = 0;
        maior = null;
        while (resto.size() > qualidade.length - elitismo) {
            k++;
            for (Integer ind : resto) {
                if (maior == null) {
                    maior = ind;
                } else if (qualidade[maior] < qualidade[ind]) {
                    maior = ind;
                }
            }
            sel[k] = maior;
            resto.remove(maior);
        }

        for (int i = elitismo; i < sel.length; i++) {
            for (int j = 0; j < segmentos.length; j++) {
                if (segmentos[j] > roleta[i - elitismo]) {
                    sel[i] = j;
                    break;
                }
            }
        }
        return sel;
    }

    private int insereInteiro(String label, String erro) {
        Scanner in = new Scanner(System.in);
        System.out.println(label);
        String it = in.nextLine();
        while (true) {
            try {
                return Integer.parseInt(it);
            } catch (NumberFormatException nfe) {
                System.out.println(erro);
            }
        }
    }


    private Recombinacao[] geraRecombinacao(int[] sel) {

        Recombinacao[] recombinacoes = new Recombinacao[(individuos.length - elitismo) / 2];

        int j = 0;
        for (int i = elitismo; i < individuos.length; i += 2) {
            Recombinacao rec = new Recombinacao(individuos, sel[i], sel[i + 1], nBits, qtPontosDeCorte,
                    maxProbRecombinacao);
            recombinacoes[j] = rec;
            j++;
        }
        return recombinacoes;
    }


    public void simulaEntrada() {
        individuos = new int[14];
        int max = 1 << nBits;
        for (int i = 0; i < individuos.length; i++) {
            individuos[i] = (int) (Math.random() * max);
        }
    }


    public void imprimeIndividuos() {
        System.out.println("Individuos decimal binário");
        for (int k = 0; k < individuos.length; k++) {
            System.out.printf("%6d      %04d   %s\n", k + 1, individuos[k],
                    Individuo.cria(individuos[k], nBits).getStringBinarioSemPontosDeCorte());
        }
    }

    public void geraResultadosConsola() {
        maxProbRecombinacao = 0.75;

        for (int i = 0; i < iteracoes; i++) {

            if (i > 0) System.out.println();
            System.out.printf("%dª iteração\n", i + 1);

            System.out.println("--------------");

            imprimeIndividuos();
            System.out.println();

            double[][] res = avaliacaoEQualidade();
            double[] avaliacao = res[0];
            double[] qualidade = res[1];

            double[] segmentos = criaSegmentos();
            double[] roleta = roleta(individuos.length - elitismo);

            int[] selecao = geraSeleccao(qualidade, segmentos, roleta);
            Recombinacao[] recombinacoes = geraRecombinacao(selecao);

            System.out.println("Individuo Avaliação Qualidade Probabilidade Segmentos");
            for (int j = 0; j < avaliacao.length; j++) {

                double delta = (j == 0) ? segmentos[j] : segmentos[j] - segmentos[j - 1];
                System.out.printf("%-9d %3.4f    %3.4f    %3.4f        %3.4f    \n",
                        (j + 1), avaliacao[j], qualidade[j], delta, segmentos[j]);
            }

            IntervaloConsola ic = new IntervaloConsola();
            ic.setFin("");
            ic.setInicial("|");

            System.out.println("\nRecombinação");
            for (int j = 0; j < recombinacoes.length; j++) {
                Recombinacao recombinacao = recombinacoes[j];
                if (recombinacao.ocorreu()) {
                    ArrayList<Individuo> individosRecombinados = new ArrayList<Individuo>(4);


                    individosRecombinados.add(new Individuo(individuos[recombinacao.getIndice1()], nBits));
                    individosRecombinados.add(new Individuo(recombinacao.getFilho1(), nBits));
                    individosRecombinados.add(new Individuo(individuos[recombinacao.getIndice2()], nBits));
                    individosRecombinados.add(new Individuo(recombinacao.getFilho2(), nBits));

                    for (int k = 0; k < 4; k++) {
                        StringBuilder sb = new StringBuilder();
                        individosRecombinados.get(k).setPontosDeCorte(recombinacao.getPontosDeCorte());

                        individosRecombinados.get(k).setSeparadorIntervalo(ic);
                        sb.append('i');
                        if (k % 2 == 1) {
                            sb.append('\'');
                            if (k / 2 == 0)
                                sb.append(String.format("%-3d", j / 2 + 1 + elitismo));
                            else
                                sb.append(String.format("%-3d", j / 2 + 2 + elitismo));
                        } else if (k / 2 == 0) sb.append(String.format("%-4d", recombinacao.getIndice1() + 1));
                        else sb.append(String.format("%-4d", recombinacao.getIndice2() + 1));
                        sb.append(" ");
                        sb.append(individosRecombinados.get(k).getStringBinario());
                        System.out.println(sb);
                    }
                }
            }


            System.out.println("\n  Pares  Valor aleatório[0;1] Pontos de corte Filhos");
            for (int j = 0; j < elitismo; j++) {
                System.out.printf("%-3d             -              -              i'%d\n",
                        selecao[j] + 1, j + 1);
            }

            for (int j = 0; j < recombinacoes.length; j++) {
                Recombinacao recombinacao = recombinacoes[j];
                StringBuilder sb = null;
                if (recombinacao.ocorreu()) {
                    sb = new StringBuilder();
                    int[] ptsCorte = recombinacao.getPontosDeCorte();
                    for (int k = 0; k < ptsCorte.length - 1; k++) {
                        sb.append(ptsCorte[k]);
                        sb.append(';');
                    }
                    sb.append(ptsCorte[ptsCorte.length - 1]);
                }
                System.out.printf("%-9s       %3.4f       %6s           %-13s\n",
                        String.format("%d e %d", recombinacao.getIndice1() + 1, recombinacao.getIndice2() + 1),
                        recombinacao.getValorAleatorio(), sb != null ? sb.toString() : "-   ",
                        String.format("i'%d e i'%d", j * 2 + elitismo + 1, j * 2 + elitismo + 2));
            }

            int[] elite = new int[elitismo];
            for (int j = 0; j < elitismo; j++) {
                elite[j] = individuos[selecao[j]];
            }
            for (int j = 0; j < elitismo; j++) {
                Recombinacao recombinacao = recombinacoes[j];
                if (recombinacao.ocorreu()) {
                    individuos[j * 2 + elitismo] = recombinacao.getFilho1();
                    individuos[j * 2 + elitismo + 1] = recombinacao.getFilho2();
                } else {
                    individuos[j * 2 + elitismo] = individuos[recombinacao.getIndice1()];
                    individuos[j * 2 + elitismo + 1] = individuos[recombinacao.getIndice2()];
                }
            }
            for (int j = 0; j < elitismo; j++) {
                individuos[j] = elite[j];
            }
            System.out.println();
            imprimeIndividuos();

            Mutacao mutacao = Mutacao.cria(individuos, nBits);
            System.out.println("\nMutação");


            StringConcatenador sc = StringConcatenador.cria();
            ic.setInicial("(");
            ic.setFin(")");
            for (int j = 0; j < 4; j++) {
                StringConcatenador sc1 = StringConcatenador.cria().acrescenta('i');
                Individuo ind = null;
                switch (j) {
                    case 0:
                        sc1.acrescenta(mutacao.getIndiceI1() + 1);
                        sc1.acrescenta(' ');
                        sc1.acrescenta(':');
                        sc.acresentaE(sc1, 6);
                        ind = Individuo.cria(individuos[mutacao.getIndiceI1()], nBits);
                        ind.setPontosDeCorte(nBits-mutacao.getGeneI1()-1);
                        break;
                    case 1:
                        sc1.acrescenta(mutacao.getIndiceI1() + 1);
                        sc1.acrescenta('\'');
                        sc1.acrescenta(':');
                        sc.acresentaE(sc1, 6);
                        ind = Individuo.cria(mutacao.getFilho1(), nBits);
                        ind.setPontosDeCorte(nBits-mutacao.getGeneI1()-1);
                        individuos[mutacao.getIndiceI1()]=mutacao.getFilho1();
                        break;
                    case 2:
                        sc1.acrescenta(mutacao.getIndiceI2() + 1);
                        sc1.acrescenta(' ');
                        sc1.acrescenta(':');
                        sc.acresentaE(sc1, 6);
                        ind = Individuo.cria(individuos[mutacao.getIndiceI2()], nBits);
                        ind.setPontosDeCorte(nBits-mutacao.getGeneI2()-1);
                        break;
                    case 3:
                        sc1.acrescenta(mutacao.getIndiceI2() + 1);
                        sc1.acrescenta('\'');
                        sc1.acrescenta(':');
                        sc.acresentaE(sc1, 6);
                        ind = Individuo.cria(mutacao.getFilho2(), nBits);
                        ind.setPontosDeCorte(nBits-mutacao.getGeneI2()-1);
                        individuos[mutacao.getIndiceI2()]=mutacao.getFilho2();
                        break;
                }
                ind.setSeparadorIntervalo(ic);
                sc.acrescenta(ind.getStringBinario());
                sc.novaLinha();
            }
            System.out.println(sc);

            imprimeIndividuos();
        }
    }

    public static void main(String st[]) {
        AGenetico ag = new AGenetico(13);
        ag.geraResultadosConsola();
    }
}
