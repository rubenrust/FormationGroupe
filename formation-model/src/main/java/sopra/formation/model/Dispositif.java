package sopra.formation.model;

public enum Dispositif {
	CPRO("Contrat de professionnalisation"), POEI("Préparation Opérationnelle à l'Emploi Individuelle");

	private Dispositif(String label) {
		this.label = label;
	}

	private final String label;

	public String getLabel() {
		return label;
	}

}
