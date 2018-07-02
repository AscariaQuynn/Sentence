package cz.example.sentence.repository;

import cz.example.sentence.model.Word;
import cz.example.sentence.model.WordCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordsRepository extends CrudRepository<Word, Integer> {

    Word findOneByName(String name);

    @Query(value = "from Word where wordCategory = :wordCategory order by rand()")
	List<Word> findOneByWordCategory(@Param("wordCategory") WordCategory wordCategory);
}
