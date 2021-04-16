package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class Util {

	public static int[][] carregarPontos(String caminho) {
		FileReader arq;
		BufferedReader buff;
		String saida = "";

		try {
			arq = new FileReader(caminho);
			buff = new BufferedReader(arq);
			int pontos[][] = new int[Integer.parseInt(buff.readLine())][2];
			int lin = 0;
			String ponto = "";
			while (buff.ready()) {
				saida = buff.readLine();
				int col = 0;
				for (int i = 0; i < saida.length(); i++) {
					if (saida.charAt(i) != ' ') {
						ponto += saida.charAt(i);
					}
					if (saida.charAt(i) == ' ' || i == saida.length() - 1) {
						int p = Integer.parseInt(ponto);
						ponto = "";
						pontos[lin][col] = p;
						col++;
					}
				}
				lin++;
			}
			buff.close();
			arq.close();
			return pontos;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro na abertura do arquivo!");
			return null;
		}

	}

	public static int[] gerarSolucao(int numFacilidade, int numPontos) {
		int[] vet = new int[numFacilidade];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = (int) (Math.random() * numPontos);
		}
		return vet;
	}

	public static void gerarPopulacao(int numPopulacao, int numFacilidade, int[][] pontos, Populacao p1) {
		for (int i = 0; i <= numPopulacao; i++) {
			int[] solucaoAleatoria = gerarSolucao(numFacilidade, pontos.length);
			int[] tetzAndBart = tetzBart(solucaoAleatoria, pontos.length, pontos);
			double y = Util.avaliacao(pontos, tetzAndBart);
			Cromossomo c1 = new Cromossomo(tetzAndBart, y);
			p1.addSolucao(c1);
			System.out.println(c1.toString());
		}
	}

	public static double calcularDistancia(int[][] pontos, int pt1, int pt2) {
		double dist;
		int x = (int) Math.pow((pontos[pt1][0] - pontos[pt2][0]), 2);
		int y = (int) Math.pow((pontos[pt1][1] - pontos[pt2][1]), 2);
		dist = Math.sqrt(x + y);
		return dist;
	}

	public static double avaliacao(int[][] pontos, int[] vetSolucao) {
		double fitness = 0;
		double menorDistancia;
		/*
		 * O primeiro for pega o ponto e no segundo for ele calcula qual a facilidade
		 * mais proxima dele para fazer a ligação e no final da pesquisa pelas
		 * facilidades ele pega a menor distancia e soma ela ao fitness da solucao
		 */
		for (int i = 0; i < pontos.length; i++) {
			menorDistancia = Double.MAX_VALUE;
			for (int j = 0; j < vetSolucao.length; j++) {
				double dist = 0;
				dist = calcularDistancia(pontos, vetSolucao[j], i);
				if (dist < menorDistancia) {
					menorDistancia = dist;
				}
			}
			fitness += menorDistancia;
		}
		return fitness;
	}

	public static void exibeVet(int[] vet) {

		String saida = "| ";
		for (int i = 0; i < vet.length; i++) {
			saida += vet[i] + " | ";
		}
		System.out.println(saida);
	}

	public static int[] tetzBart(int[] vetSolucao, int numPontos, int[][] pontos) {
		double fitnessProvisorio = avaliacao(pontos, vetSolucao);
		double fitTetzBart;

		for (int i = 0; i < vetSolucao.length; i++) {
			int pos = vetSolucao[i];
			for (int j = 0; j < numPontos; j++) {
				vetSolucao[i] = j;
				fitTetzBart = avaliacao(pontos, vetSolucao);
				if (fitTetzBart < fitnessProvisorio) {
					pos = j;
					fitnessProvisorio = fitTetzBart;
				}
			}
			vetSolucao[i] = pos;
		}
		return vetSolucao;
	}

	public static Populacao selecaoRoleta(Populacao p1) {
		Populacao pais = new Populacao();
		double sumFit = 0;
		int numSelecionado = 0;

		// calculando fitness total para distribuir as porcentagens de aptidao
		for (Cromossomo c1 : p1.getPop()) {
			sumFit += c1.getFitness();
		}
		// calculando porcentagem individual de cada cromossomo
		for (Cromossomo c1 : p1.getPop()) {
			c1.calcAptidao(sumFit);
		}
		while (numSelecionado != 2) {
			double sumAptd = 0;
			double numRoleta = Math.random() * 100;
			for (Cromossomo c2 : p1.getPop()) {
				sumAptd += c2.getAptidao();
				if (sumAptd >= numRoleta && !pais.getPop().contains(c2)) {
					pais.addSolucao(c2);
					numSelecionado++;
					break;
					// garantindo que o algoritmo nao selecione o mesmo cromossomo
				} else if (sumAptd >= numRoleta && pais.getPop().contains(c2)) {
					break;
				}
			}
		}
		return pais;
	}

	public static Cromossomo cruzamentoMascara(Populacao pais, int[][] pontos) {
		Cromossomo filho01 = new Cromossomo(pais.getPop().get(0).getPontos().length);
		Cromossomo filho02 = new Cromossomo(pais.getPop().get(0).getPontos().length);
		Random gerador = new Random();
		int mascara;

		for (int i = 0; i < filho01.getPontos().length; i++) {
			mascara = gerador.nextInt(2);
			if (mascara == 0) {
				filho01.getPontos()[i] = pais.getPop().get(0).getPonto(i);
				filho02.getPontos()[i] = pais.getPop().get(1).getPonto(i);
			} else {
				filho01.getPontos()[i] = pais.getPop().get(1).getPonto(i);
				filho02.getPontos()[i] = pais.getPop().get(0).getPonto(i);
			}
		}

		filho01.setFitness(avaliacao(pontos, filho01.getPontos()));
		filho02.setFitness(avaliacao(pontos, filho02.getPontos()));

		System.out.println("Filho 01: " + filho01.toString());
		System.out.println("Filho 02: " + filho02.toString());

		if (filho01.getFitness() < filho02.getFitness()) {
			return filho01;
		} else {
			return filho02;
		}
	}

	public static void mutacao(Cromossomo filho, int[][] pontos) {
		Random random = new Random();
		
		// Calcular porcentagem para saber se o cromossomo vai sofre mutacao
		int percTotal = random.nextInt(101);

		// Porcentagem de chance de acontecer uma mutaçao
		final int percentChanceMut = 1;

		// Numero de genes que vai sofre a mutacao
		int numGeneMut = (int) (0.01 * filho.getPontos().length);

		// garantindo que pelo menos 1 gene possa sofrer a mutacao
		if (numGeneMut <= 0) {
			numGeneMut = 1;
		}

		// Se porcentagem total for igual ou menor que a chance de acontecer mutacao o
		// algoritmo entra no ciclo para mudar a quantidade de genes definidos
		if (percTotal <= percentChanceMut) {
			for (int i = 0; i < numGeneMut; i++) {
				filho.getPontos()[random.nextInt(filho.getPontos().length)] = 
						random.nextInt(pontos.length);
			}
			//recalculando o fitness caso o cromossomo sofra mutação
			filho.setFitness(avaliacao(pontos, filho.getPontos()));
		}
	}

	public static boolean detectarClone(Populacao p1, Cromossomo filho) {
		for (int i = 0; i < p1.getPop().size(); i++) {
			if (p1.getPop().get(i).getFitness() == filho.getFitness()) {
				return true;
			}
		}
		return false;
	}
}
