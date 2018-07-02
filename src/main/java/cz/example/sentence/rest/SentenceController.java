package cz.example.sentence.rest;

import cz.example.sentence.model.Sentence;
import cz.example.sentence.model.YodaSentence;
import cz.example.sentence.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentenceController {

	public static final String MAPPING_BASE = "/rest/sentence";

	@Autowired
	public SentenceService sentenceService;

	@RequestMapping(value = MAPPING_BASE, method = RequestMethod.POST)
	public Sentence generate() {
		return sentenceService.generate();
	}

	@RequestMapping(value = MAPPING_BASE, method = RequestMethod.GET)
	public Iterable<Sentence> findAll() {
		Iterable<Sentence> all = sentenceService.findAll();
		sentenceService.incrementCounter(all);
		return all;
	}

	@RequestMapping(value = MAPPING_BASE + "/{id}", method = RequestMethod.GET)
	public Sentence findOne(@PathVariable("id") int id) {
		Sentence one = sentenceService.findOne(id);
		sentenceService.incrementCounter(one.getId());
		return one;
	}

	@RequestMapping(value = MAPPING_BASE + "/{id}/yodaTalk", method = RequestMethod.GET)
	public YodaSentence findOneYodaTalk(@PathVariable("id") int id) {
		YodaSentence oneYodaTalk = sentenceService.findOneYodaTalk(id);
		sentenceService.incrementCounter(oneYodaTalk.getId());
		return oneYodaTalk;
	}

	@RequestMapping(value = MAPPING_BASE + "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id) {
		sentenceService.delete(id);
	}
}
