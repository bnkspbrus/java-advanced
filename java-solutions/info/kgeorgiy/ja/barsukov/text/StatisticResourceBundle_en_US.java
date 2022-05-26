package info.kgeorgiy.ja.barsukov.text;

import java.util.ListResourceBundle;

public class StatisticResourceBundle_en_US extends ListResourceBundle {

    private final Object[][] CONTENTS = {
            {"usage", "Usage:"},
            {"ParsedFile", "Parsed file \"{0}\""},
            {"summaryStatistics", "Summary statistics"},
            {"numberOfSentences", "Number of sentences: {0, number, integer}."},
            {"numberOfWords", "Number of words: {0, number, integer}"},
            {"numberOfCurrencies", "Number of currencies: {0, number, integer}"},
            {"numberOfDates", "Number of dates: {0, number, integer}"},
            {"sentencesStatistics", "Sentences statistics"},
            {"differentSentences", "Different sentences: {0, number, integer} ({1, number, integer} distinct)."},
            {"minimalSentence", "Minimal sentence: \"{0}\"."},
            {"maximalSentence", "Maximal sentence: \"{0}\"."},
            {"minimalSentenceLength", "Minimal sentence length: \"{0} (\"{1}\")."},
            {"maximalSentenceLength", "Maximal sentence length: {0} (\"{1}\")."},
            {"averageSentenceLength", "Average sentence length: {0, number}."},
            {"wordsStatistics", "Words statistics"},
            {"numberOfWords", "Number of words: {0, number, integer} ({1, number, integer} distinct)."},
            {"minimalWord", "Maximal word: \"{0}\""},
            {"maximalWord", "Minimal word: \"{0}\""},
            {"minimalWordLength", "Minimal word length: {0, number, integer} (\"{0}\")."},
            {"maximalWordLength", "Maximal word length: {0, number, integer} (\"{0}\")."},
            {"averageWordLength", "Average word length: {0, number}."},
            {"numbersStatistics", "Words statistics."},
            {"numberOfNumbers", "Number of numbers: {0, number, integer} ({1, number, integer} distinct)."},
            {"minimalNumber", "Minimal number: {0, number}."},
            {"maximalNumber", "Maximal number: {0, number}."},
            {"averageNumber", "Average number: {0, number}."},
            {"currenciesStatistics", "Currencies statistics."},
            {"numberOfCurrencies", "Число сумм: {0, number, integer} ({1, number, integer} различных)."},
            {"minimalCurrency", "Минимальная сумма: {0, number, currency}"},
            {"maximalCurrency", "Максимальная сумма: {0, number, currency}"},
            {"averageCurrency", "Средняя сумма: {0, number, currency}"},
            {"datesStatistics", "Статистика по датам"},
            {"numberOfDates", "Число дат: {0, number, integer} ({1, number, integer} различных)."},
            {"maximalDate", "Минимальная дата: {0, date, medium}."},
            {"maximalDate", "Максимальная дата: {0, date, medium}."},
            {"averageDate", "Средняя дата: {0, date, medium}.."},
            {"-o", "Write output"},
    };

    protected Object[][] getContents() {
        return CONTENTS;
    }
}
