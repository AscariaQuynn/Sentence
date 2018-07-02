package cz.example.sentence;

import cz.example.sentence.model.Sentence;
import cz.example.sentence.model.Word;
import cz.example.sentence.model.WordCategory;
import cz.example.sentence.model.YodaSentence;
import cz.example.sentence.service.SentenceService;
import cz.example.sentence.service.WordsService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SentenceWebApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SentenceTest {

    @Autowired
    private WordsService wordsService;

    @Autowired
    private SentenceService sentenceService;

    private DateTime now = DateTime.now();

    @Test
    public void testGenerateSentence() {
        wordsService.save(getWord1Mock());
        wordsService.save(getWord2Mock());
        wordsService.save(getWord3Mock());

        Sentence mockedSentence = getSentenceMock();
        Sentence savedSentence = sentenceService.generate();

        Assert.assertNotNull(savedSentence.getId());
        Assert.assertEquals(savedSentence.getId(), mockedSentence.getId());
        Assert.assertEquals(savedSentence.getText(), mockedSentence.getText());
    }

    /**
     * Tries to create sentence and transforms it into yoda talk.
     */
    @Test
    public void testGenerateSentenceAndYoda() {
        wordsService.save(getWord1Mock());
        wordsService.save(getWord2Mock());
        wordsService.save(getWord3Mock());

        YodaSentence mockedYodaSentence = getYodaSentenceMock();
        Sentence savedSentence = sentenceService.generate();
        YodaSentence oneYodaTalk = sentenceService.findOneYodaTalk(savedSentence.getId());

        Assert.assertNotNull(oneYodaTalk.getId());
        Assert.assertEquals(oneYodaTalk.getId(), savedSentence.getId());
        Assert.assertEquals(oneYodaTalk.getId(), mockedYodaSentence.getId());
        Assert.assertEquals(oneYodaTalk.getText(), mockedYodaSentence.getText());
    }

    /**
     * Tries to create sentence without words so it should fail.
     */
    @Test(expected = SentenceException.class)
    public void testGenerateSentenceFail() {
        sentenceService.generate();
    }

    private Sentence getSentenceMock() {
        Sentence sentence = new Sentence();
        sentence.setId(1);
        sentence.setText(getWord1Mock().getName() + " " + getWord2Mock().getName() + " " + getWord3Mock().getName());
        sentence.setDisplayCount(0);
        sentence.setCreated(now);
        return sentence;
    }

    private YodaSentence getYodaSentenceMock() {
        YodaSentence yodaSentence = new YodaSentence();
        yodaSentence.setId(1);
        yodaSentence.setText(getWord3Mock().getName() + " " + getWord1Mock().getName() + " " + getWord2Mock().getName());
        return yodaSentence;
    }

    private Word getWord1Mock() {
        Word word = new Word();
        word.setId(1);
        word.setName("Light");
        word.setWordCategory(WordCategory.NOUN);
        return word;
    }

    private Word getWord2Mock() {
        Word word = new Word();
        word.setId(2);
        word.setName("is");
        word.setWordCategory(WordCategory.VERB);
        return word;
    }

    private Word getWord3Mock() {
        Word word = new Word();
        word.setId(3);
        word.setName("Good");
        word.setWordCategory(WordCategory.ADJECTIVE);
        return word;
    }

}
