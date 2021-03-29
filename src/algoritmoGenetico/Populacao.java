package algoritmoGenetico;

import java.util.ArrayList;
import java.util.Iterator;

public class Populacao {

	private ArrayList<Cromossomo> pop;

	public Populacao() {
		this.pop = new ArrayList<Cromossomo>();
	}

	public void addSolucao(Cromossomo s1) {
		this.pop.add(s1);
	}

	public ArrayList<Cromossomo> getPop() {
		return pop;
	}

	public void setPop(ArrayList<Cromossomo> pop) {
		this.pop = pop;
	}

	@Override
	public String toString() {

		String saida = "";

		for (Cromossomo solucao : pop) {
			saida += solucao.toString() + "\n";
		}

		return saida;
	}

}
