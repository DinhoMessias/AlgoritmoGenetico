package algoritmoGenetico;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		int[][] pontos = Util.carregarPontos("324.txt");
		int numFacilidade;
		int numPopulacao;
		boolean parada = false;
		boolean clone;
		int contLoop = 0;
		double melhorFit = 79256;
		Populacao popInit = new Populacao();
		Populacao pais = new Populacao();
		Cromossomo filho = new Cromossomo();
		long tempoInicial = System.currentTimeMillis();

		System.out.println("Informe o numero de facilidades: ");
		numFacilidade = entrada.nextInt();
		System.out.println("Informe o numero da populacao inicial: ");
		numPopulacao = entrada.nextInt();
		entrada.close();

		// Gerando populacao inicial
		Util.gerarPopulacao(numPopulacao, numFacilidade, pontos, popInit);
		System.out.println("\nMelhor Cromo: \n" + Util.melhorCromo(popInit));
		System.out.println("\nFitness medio: " + Util.avgFit(popInit));

		// Iniciando ciclo do Algoritmo Genetico
		do {
			contLoop++;
			// Selecionando Pais
			pais = Util.selecaoRoletaPorBilhete(popInit);
			// System.out.println("\nPais Selecionados \n" + pais.toString());

			// Gerarando filho por Mascara
			filho = Util.cruzamentoMascara(pais, pontos);
			// System.out.println("\nFilho selecionado\n" + filho.toString());

			// Chamando metodo de mutacao
			Util.mutacao(filho, pontos);

			// Detectar se o filho não é clone de nenhum cromossomo que ja exista na
			// populacao
			clone = Util.detectarClone(popInit, filho);

			// Caso o filho nao seja clone ele entra no metodo de reinsercao
			if (clone == false) {
				Util.reinsercaoMelhorFitPais(popInit, pais, filho);
			}

		} while (((System.currentTimeMillis() - tempoInicial) / 60000) <= 510);

		System.out.println("O algoritmo gastou " + ((System.currentTimeMillis() - tempoInicial) / 60000) + " minutos");
		System.out.println("\nPopulacao: \n" + popInit.toString());
		System.out.println("\nMelhor Solução Encontrada Reinsercao: " + Util.melhorCromo(popInit));
		System.out.println("Fitness Medio Final: " + Util.avgFit(popInit));
		System.out.println("O algoritmo precisou de " + contLoop + " para encontrar a melhor solucao");

	}
}
