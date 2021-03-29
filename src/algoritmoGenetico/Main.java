package algoritmoGenetico;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		int numPopulacao;
		int numFacilidade;
		int[][] pontos = Util.carregarPontos("324.txt");
		Populacao p1 = new Populacao();
		Populacao pais = new Populacao();
		System.out.println("Informe o numero de facilidades: ");
		numFacilidade = entrada.nextInt();
		System.out.println("Informe o numero da populacao inicial: ");
		numPopulacao = entrada.nextInt();

		for (int i = 0; i <= numPopulacao; i++) {
			int[] solucaoAleatoria = Util.gerarSolucao(numFacilidade, pontos.length);
			int[] tetzAndBart = Util.tetzBart(solucaoAleatoria, pontos.length, pontos);
			double y = Util.avaliacao(pontos, tetzAndBart);
			Cromossomo c1 = new Cromossomo(tetzAndBart, y);
			p1.addSolucao(c1);
			System.out.println(c1.toString());
		}

		pais = Util.selecaoRoleta(p1);
		System.out.println("\nPais Selecionados \n" + pais.toString());
	}
}
