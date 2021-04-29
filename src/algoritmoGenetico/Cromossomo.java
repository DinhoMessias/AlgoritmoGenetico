package algoritmoGenetico;

public class Cromossomo {

	private int[] pontos;
	private double fitness;
	private double aptidao;

	public Cromossomo(int[] pontos, double fitness) {
		this.pontos = pontos;
		this.fitness = fitness;
	}

	public Cromossomo(int numPontos) {
		this.pontos = new int[numPontos];
	}

	public Cromossomo() {
	}

	public int[] getPontos() {
		return pontos;
	}

	public void setPontos(int[] pontos) {
		this.pontos = pontos;
	}

	public int getPonto(int index) {
		return this.pontos[index];
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {

		this.fitness = fitness;
	}

	public double getAptidao() {
		return aptidao;
	}

	public void setAptidao(double aptidao) {
		this.aptidao = aptidao;
	}

	public void calcAptidao(double fitTotal) {
		this.aptidao = (this.fitness * 100) / fitTotal;
	}

	public static String exibeVet(int[] vet) {

		String saida = "| ";
		for (int i = 0; i < vet.length; i++) {
			saida += vet[i] + " | ";
		}
		// System.out.println(saida);
		return saida;
	}

	@Override
	public String toString() {
		String saida = exibeVet(this.pontos) + " = " + this.fitness;
		return saida;
	}

}
