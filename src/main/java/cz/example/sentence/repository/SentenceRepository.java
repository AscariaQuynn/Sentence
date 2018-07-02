package cz.example.sentence.repository;

import cz.example.sentence.model.Sentence;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SentenceRepository extends CrudRepository<Sentence, Integer> {

	@Modifying
	@Query(value = "UPDATE Sentence SET displayCount = displayCount + 1 WHERE id = :id")
	void incrementCounter(@Param("id") int id);

	@Modifying
	@Query(value = "UPDATE Sentence SET displayCount = displayCount + 1 WHERE id in (:ids)")
	void incrementCounter(@Param("ids") List<Integer> ids);
}
