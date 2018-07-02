package cz.example.sentence.rest;

import cz.example.sentence.model.Word;
import cz.example.sentence.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordsController {

	public static final String MAPPING_BASE = "/rest/words";

	@Autowired
	public WordsService wordsService;

	@RequestMapping(value = MAPPING_BASE, method = RequestMethod.PUT)
	public Word save(Word word) {
		return wordsService.save(word);
	}

	@RequestMapping(value = MAPPING_BASE)
	public Iterable<Word> findAll() {
		return wordsService.findAll();
	}

	@RequestMapping(value = MAPPING_BASE + "/{name}", method = RequestMethod.GET)
	public Word findOneByName(@PathVariable("name") String name) {
		return wordsService.findOneByName(name);
	}

	@RequestMapping(value = MAPPING_BASE + "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id) {
		wordsService.delete(id);
	}

}
