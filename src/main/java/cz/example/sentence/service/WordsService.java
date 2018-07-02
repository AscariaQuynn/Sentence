package cz.example.sentence.service;

import cz.example.sentence.model.Word;
import cz.example.sentence.model.WordCategory;
import cz.example.sentence.repository.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordsService {

	@Autowired
	private WordsRepository wordsRepository;

	@Value("${sentence.words.forbiddenWords}")
	private String[] forbiddenWords;

	public Word save(Word word) {
		if(isForbidden(word)) {
			throw new IllegalArgumentException("Trying to create forbidden word: " + word.getName());
		}

		return wordsRepository.save(word);
	}

	public Iterable<Word> findAll() {
		return wordsRepository.findAll();
	}

	public Word findOneByName(String name) {
		return wordsRepository.findOneByName(name);
	}

	public Word findOneByWordCategory(WordCategory wordCategory) {
		List<Word> words = wordsRepository.findOneByWordCategory(wordCategory);
		return words.size() > 0 ? words.get(0) : null;
	}

	public void delete(int id) {
		wordsRepository.delete(id);
	}

	public boolean isForbidden(Word word) {
		String wordName = word.getName();
		if(wordName != null && forbiddenWords != null) {
			for(String forbiddenWord : forbiddenWords) {
				if(forbiddenWord.equalsIgnoreCase(wordName)) {
					return true;
				}
			}
		}
		return false;
	}
}
