package info.kgeorgiy.ja.barsukov.i18n;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Date;
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
        assertEquals("""
                При выполнении задания следует обратить внимание на:
                применение лямбда-выражений и потоков;
                избавление от повторяющегося кода.""", sentenceStatistics.maxLength);
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

    @Test
    public void test03_dates() {
        TextStatistics textStatistics = new TextStatistics(RUSSIAN_LOCALE, RUSSIAN_LOCALE);
        TextStatistics.Statistics<Date> dateStatistics = textStatistics.getDateStatistics(RUSSIAN_TEXT_WITH_DATES);
        assertEquals(4, dateStatistics.total);
        assertEquals(2, dateStatistics.distinct);
    }

    @Test
    public void test04_numbers() {
        TextStatistics textStatistics = new TextStatistics(Locale.ENGLISH, Locale.ENGLISH);
        TextStatistics.Statistics<Number> numberStatistics = textStatistics.getNumberStatistics(
                ENGLISH_TEXT_WITH_NUMBERS);
        assertEquals(20, numberStatistics.total);
        assertEquals(20, numberStatistics.max.intValue());
        assertEquals(3, numberStatistics.min.intValue());
    }

    private static final String RUSSIAN_TEXT_WITH_CURRENCY =
            "Центральный банк России установил официальный курс доллара США на 28-30 мая в размере ₽66,4, евро — ₽69," +
                    "44. Об этом сообщается на сайте регулятора. По данным на 16:00 мск, на Московской бирже " +
                    "американская валюта торгуется на отметке ₽66,04 (-1,87% к уровню закрытия предыдущей торговой " +
                    "сессии), европейская — ₽68,42 (+0,62%).";

    private static final String ENGLISH_TEXT_WITH_NUMBERS = """
            Three (3)

            Example: "He decided to get three bags of onions when he went shopping."

            Four (4)

            Example: "In preparation for the party, Janice bought four cartons of ice cream at the store."

            Five (5)

            Example: "Joe picked up five boxes of cereal from the breakfast aisle."

            Six (6)

            Example: "A pound of beef costs a lot of money at my favorite grocery store."

            Seven (7)

            Example: "Seven of the 10 aisles at my local grocery store contain pasta."

            Eight (8)

            Example: "I bought eight cookies for the price of four at the store."

            Nine (9)

            Example: "There were only nine loafs of bread left at the grocery store."

            Ten (10)

            Example: "Ten pineapples sure is a lot, don't you think?"

            After ten, eleven (11), twelve (12), thirteen (13), fourteen (14), fifteen (15), sixteen (16), seventeen (17)
            , eighteen (18), nineteen (19), and twenty (20) follow. These numbers are seen less in grocery stores, as most
             prices are 10 dollars or less; it is however worth knowing these numbers, generally and, in terms of grocery
              shopping, for when the bill must be paid.""";

    private static final String RUSSIAN_TEXT_WITH_DATES = """
            12.11.22 В научно-технической документации встречается цифровое оформление даты с указанием времени дня. Запись
             делается в следующей последовательности: часы, минуты или часы, минуты, секунды. Между элементами времени ставится
              двоеточие: 12:30 (12 часов и 30 минут).\s

            В финансовых документах даты пишутся словами и цифрами. В соответствии с последними рекомендациями ГОСТ в реквизите
             документа допустимо написание числа без «0». Сравните: прежняя редакция — «09 апреля 2019 г.», новая редакция — «
             9 апреля 2019 г.». При сокращении слова «год» обязательно ставится пробел и точка. \s

            Словесное написание даты уместно только в специальных документах, например в доверенности: девятое апреля две
             тысячи девятнадцатого года.\s

            Пишем частичное представление: месяц, год\s
            При написании даты, которая состоит из месяца и года, полугодия и года следует воспользоваться следующими правилами
             написания дат:\s

            в марте 2019 г.;\s
            в марте — апреле 2019 г.;\s
             второе полугодие 2018 г., II полугодие, во 2-м полугодии;\s
            во II квартале 2018 г.\s
            Обратите внимание, что для записи полугодия допустимы варианты. Слово «год» во всех подобных выражениях сокращается.\s

            Частичное представление: год, век, тысячелетие\s
            Даты, которые представляют собой временной промежуток (года, века или тысячелетия), записываются в соответствии
             с правилами оформления дат. Рассмотрим примеры:\s

             Некалендарные даты (например учебный или отчетный год), начинающиеся в одном, а заканчивающиеся в другом году,
              оформляются с использованием косой черты: в 2017/2018 учебном году.\s

            В остальных случаях, если период ограничен несколькими годами или десятилетиями, то между числами ставится тире
             
             без пробела. А слово «годы» сокращается (гг.). Например: в 2013—2018 гг.\s

            """;
    private static final String ENGLISH_TEXT = """
            Get Useful Text Statistics. Research the structure of your text.
            This, the standard version of our text analyser, shows you summary statistics about your text to help you understand
             its complexity and readability. It's perfect for use by students, translators, writers and anyone wanting to understand
              their text statistically.

            Total Word Count
            Total Word Count (Excluding Common Words)
            Number of Different Words
            Different Words (Excluding Common Words)
            Number of Paragraphs
            Number of Sentences
            Words per Sentence
            Number of Characters (all)
            Number of Characters (a-z)
            Characters per Word
            Syllables
            Syllables per Word
            Our advanced text analyser gives a much more detailed analysis of text with many more statistics.

            Test Your Readability. Discover how understandable your text is.
            Use these readability statistics to help you assess the complexity of a text and how hard it is to read and
             understand. These industry-standard tests are designed to give you a statistical analysis of the difficulty
              of your text, allowing you to see if it's going to engage with your desired audience.

            Hard Words (three or more syllables and doesn't contain a hyphen)
            Long Words (more than 6 characters)
            Lexical Density
            Gunning Fog Readability Index
            Coleman-Liau Index (part of our advanced text analyser)
            Flesch-Kincaid Grade Level (part of our advanced text analyser)
            Flesch Reading Ease (part of our advanced text analyser)
            ARI (Automated Readability Index) (part of our advanced text analyser)
            SMOG Grade (part of our advanced text analyser)
            Laesbarhedsindex (LIX) (part of our advanced text analyser).""";

    private static final String RUSSIAN_TEXT = """
            Домашнее задание 3. Студенты
            Разработайте класс StudentDB, осуществляющий поиск по базе данных студентов.
            Класс StudentDB должен реализовывать интерфейс StudentQuery (простой вариант) или GroupQuery (сложный вариант).
            Каждый метод должен состоять из ровно одного оператора. При этом длинные операторы надо разбивать на несколько строк.
            При выполнении задания следует обратить внимание на:
            применение лямбда-выражений и потоков;
            избавление от повторяющегося кода.""";
}
