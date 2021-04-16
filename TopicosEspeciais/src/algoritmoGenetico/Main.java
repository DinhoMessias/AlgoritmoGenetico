package algoritmoGenetico;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		int[][] pontos = Util.carregarPontos("324.txt");
		int numPopulacao;
		int numFacilidade;
		boolean parada = false;
		boolean clone;
		Populacao p1 = new Populacao();
		Populacao pais = new Populacao();
		Cromossomo filho = new Cromossomo();

		System.out.println("Informe o numero de facilidades: ");
		numFacilidade = entrada.nextInt();
		System.out.println("Informe o numero da populacao inicial: ");
		numPopulacao = entrada.nextInt();
		entrada.close();

		// Gerando populacao inicial
		Util.gerarPopulacao(numPopulacao, numFacilidade, pontos, p1);

		// Iniciando ciclo do Algoritmo Genetico
		do {
			// Selecionando Pais
			pais = Util.selecaoRoleta(p1);
			System.out.println("\nPais Selecionados \n" + pais.toString());

			// Gerarando filho por Mascara
			filho = Util.cruzamentoMascara(pais, pontos);
			System.out.println("\nFilho selecionado\n" + filho.toString());

			// Chamando metodo de mutacao
			Util.mutacao(filho, pontos);

			// Detectar se o filho não é clone de nenhum cromossomo que ja exista na
			// populacao
			clone = Util.detectarClone(p1, filho);

			// Acionando condicao de parada
			parada = true;
		} while (parada == true);
	}
}
