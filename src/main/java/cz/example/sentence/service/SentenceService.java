package cz.example.sentence.service;

import cz.example.sentence.SentenceException;
import cz.example.sentence.model.Sentence;
import cz.example.sentence.model.Word;
import cz.example.sentence.model.WordCategory;
import cz.example.sentence.model.YodaSentence;
import cz.example.sentence.repository.SentenceRepository;
import cz.example.sentence.utils.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SentenceService {

	private static final char SEPARATOR_CHAR = ' ';

	@Autowired
	private SentenceRepository sentenceRepository;

	@Autowired
	private WordsService wordsService;

	public Sentence generate() {
		Iterable<Sentence> all = findAll();
		int i = 0;
		for(Sentence s : all) {
			i++;
		}
		Sentence sentence = new Sentence();
		sentence.setId(i + 1);
		sentence.setCreated(DateTime.now());
		sentence.setText(getSentence());
		sentence.setDisplayCount(0);
		return sentenceRepository.save(sentence);
	}

	public Iterable<Sentence> findAll() {
		return sentenceRepository.findAll();
	}

	public Sentence findOne(int id) {
		return sentenceRepository.findOne(id);
	}

	public YodaSentence findOneYodaTalk(int id) {
		Sentence sentence = findOne(id);
		YodaSentence yodaSentence = new YodaSentence();
		yodaSentence.setId(sentence.getId());
		yodaSentence.setText(StringUtils.yodaTalk(sentence.getText()));
		return yodaSentence;
	}

	public void delete(int id) {
		sentenceRepository.delete(id);
	}

	@Transactional
	public void incrementCounter(Iterable<Sentence> all) {
		List<Integer> allInts = new ArrayList<>();
		for(Sentence one : all) {
			allInts.add(one.getId());
		}
		if(allInts.size() > 0) {
			sentenceRepository.incrementCounter(allInts);
		}
	}


	public String getSentence() {
		Word noun = wordsService.findOneByWordCategory(WordCategory.NOUN);
		Word verb = wordsService.findOneByWordCategory(WordCategory.VERB);
		Word adjective = wordsService.findOneByWordCategory(WordCategory.ADJECTIVE);

		if(noun == null || verb == null || adjective == null) {
			throw new SentenceException("You must define at least one of each word category type.");
		}

		return new StringBuilder(noun.getName())
			.append(' ')
			.append(verb.getName())
			.append(' ')
			.append(adjective.getName())
			.toString();
	}

	@Transactional
	public void incrementCounter(int id) {
		sentenceRepository.incrementCounter(id);
	}
}
