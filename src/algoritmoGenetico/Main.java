package algoritmoGenetico;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		int[][] pontos = Util.carregarPontos("324.txt");
		int numPopulacao;
		int numFacilidade;
		Boolean parada = false;
		Populacao p1 = new Populacao();
		Populacao pais = new Populacao();
		Cromossomo filho = new Cromossomo();

		System.out.println("Informe o numero de facilidades: ");
		numFacilidade = entrada.nextInt();
		System.out.println("Informe o numero da populacao inicial: ");
		numPopulacao = entrada.nextInt();
		entrada.close();

		//Iniciando ciclo do Algoritmo Genetico
		do {
			// Gerando populacao inicial
			Util.gerarPopulacao(numPopulacao, numFacilidade, pontos, p1);

			// Selecionando Pais
			pais = Util.selecaoRoleta(p1);
			System.out.println("\nPais Selecionados \n" + pais.toString());

			// Gerarando filho por Mascara
			filho = Util.cruzamentoMascara(pais, pontos);
			System.out.println("\nFilho selecionado\n" + filho.toString());

			// Acionando condicao de parada
			parada = true;
		} while (parada == true);

	}

}
