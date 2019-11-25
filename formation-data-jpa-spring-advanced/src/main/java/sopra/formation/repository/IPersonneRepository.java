package sopra.formation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sopra.formation.model.Formateur;
import sopra.formation.model.Personne;
import sopra.formation.model.Stagiaire;

public interface IPersonneRepository extends JpaRepository<Personne, Long>, IPersonneCustomRepository {
	@Query("from Stagiaire")
	List<Stagiaire> findAllStagiaire();

	@Query("from Formateur")
	List<Formateur> findAllFormateur();
	

}
