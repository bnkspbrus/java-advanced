package info.kgeorgiy.ja.barsukov.text;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatisticTests {

    private static final String RUSSIAN_TAG = "ru-Ru";

    private static final Locale RUSSIAN_LOCALE = Locale.forLanguageTag(RUSSIAN_TAG);

    @Test
    public void test01_smallRussianText() {
        TextStatistics textStatistics = new TextStatistics(RUSSIAN_LOCALE, RUSSIAN_LOCALE);
        TextStatistics.Statistics<String> sentenceStatistics = textStatistics.getSentenceStatistics(RUSSIAN_TEXT);
        TextStatistics.Statistics<String> wordStatistics = textStatistics.getWordStatistics(RUSSIAN_TEXT);
        assertEquals(6, sentenceStatistics.total);
        assertEquals(56, wordStatistics.total);
        assertEquals(6, sentenceStatistics.distinct);
        assertEquals(51, wordStatistics.distinct);
        assertEquals("Студенты\n" + "Разработайте класс StudentDB, осуществляющий поиск по базе данных студентов.",
                sentenceStatistics.max);
        assertEquals("этом", wordStatistics.max);
        assertEquals("При выполнении задания следует обратить внимание на:\n" +
                "применение лямбда-выражений и потоков;\n" +
                "избавление от повторяющегося кода.", sentenceStatistics.maxLength);
        assertEquals("лямбда-выражений", wordStatistics.maxLength);
    }

    @Test
    public void test02_bigEnglishText() {
        TextStatistics textStatistics = new TextStatistics(Locale.ENGLISH, RUSSIAN_LOCALE);
        TextStatistics.Statistics<String> sentenceStatistics = textStatistics.getSentenceStatistics(ENGLISH_TEXT);
        TextStatistics.Statistics<String> wordStatistics = textStatistics.getWordStatistics(ENGLISH_TEXT);
        assertEquals(242, wordStatistics.total);
        assertEquals(10, sentenceStatistics.total);
        assertEquals(10, sentenceStatistics.distinct);
        assertEquals(122, wordStatistics.distinct);
    }

    private static String ENGLISH_TEXT = "Get Useful Text Statistics. Research the structure of your text.\n" +
            "This, the standard version of our text analyser, shows you summary statistics about your text to help " +
            "you understand its complexity and readability. It's perfect for use by students, translators, writers " +
            "and anyone wanting to understand their text statistically.\n" + "\n" + "Total Word Count\n" + "Total " + "" +
            "Word Count (Excluding Common Words)\n" + "Number of Different Words\n" + "Different Words (Excluding " + "" +
            "Common Words)\n" + "Number of Paragraphs\n" + "Number of Sentences\n" + "Words per Sentence\n" + "Number" +
            " of Characters (all)\n" + "Number of Characters (a-z)\n" + "Characters per Word\n" + "Syllables\n" + "" +
            "Syllables per Word\n" + "Our advanced text analyser gives a much more detailed analysis of text with " +
            "many more statistics.\n" + "\n" + "Test Your Readability. Discover how understandable your text is.\n" +
            "Use these readability statistics to help you assess the complexity of a text and how hard it is to read " +
            "and" +
            " understand. These industry-standard tests are designed to give you a statistical analysis of the " +
            "difficulty" +
            " of your text, allowing you to see if it's going to engage with your desired audience.\n" + "\n" + "Hard" +
            " Words" +
            " (three or more syllables and doesn't contain a hyphen)\n" + "Long Words (more than 6 characters)\n" +
            "Lexical Density\n" + "Gunning Fog Readability Index\n" + "Coleman-Liau Index (part of our advanced text " +
            "analyser)\n"
            + "Flesch-Kincaid Grade Level (part of our advanced text analyser)\n" + "Flesch Reading Ease (part of our" +
            " advanced" +
            " text analyser)\n" + "ARI (Automated Readability Index) (part of our advanced text analyser)\n" + "SMOG " +
            "Grade " +
            "(part of our advanced text analyser)\n" + "Laesbarhedsindex (LIX) (part of our advanced text analyser)";

    private static String RUSSIAN_TEXT = "Домашнее задание 3. Студенты\n" + "Разработайте класс StudentDB, " +
            "осуществляющий поиск по базе данных студентов.\n" + "Класс StudentDB должен реализовывать интерфейс " +
            "StudentQuery (простой вариант) или GroupQuery (сложный вариант).\n" + "Каждый метод должен состоять из " +
            "ровно одного оператора. При этом длинные операторы надо разбивать на несколько строк.\n" + "При " +
            "выполнении задания следует обратить внимание на:\n" + "применение лямбда-выражений и потоков;\n" +
            "избавление от повторяющегося кода.";
}
