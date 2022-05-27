package info.kgeorgiy.ja.barsukov.text;

import java.util.ListResourceBundle;

public class StatisticResourceBundle_en_US extends ListResourceBundle {

    private final Object[][] CONTENTS = {
            {"usage", "Usage:"},
            {"ParsedFile", "Parsed file \"{0}\""},
            {"summary.statistics", "Summary statistics"},
            {"sentence.total", "Number of sentences: {0, number, integer}."},
            {"word.total", "Number of words: {0, number, integer}."},
            {"number.total", "Number of numbers: {0, number, integer}"},
            {"currency.total", "Number of currencies: {0, number, integer}."},
            {"date.total", "Number of dates: {0, number, integer}."},
            {"sentence.statistics", "Sentences statistics"},
            {"sentence.distinct", "Different sentences: {0, number, integer} ({1, number, integer} distinct)."},
            {"sentence.min", "Minimal sentence: \"{0}\"."},
            {"sentence.max", "Maximal sentence: \"{0}\"."},
            {"sentence.minLength", "Minimal sentence length: \"{0} (\"{1}\")."},
            {"sentence.maxLength", "Maximal sentence length: {0} (\"{1}\")."},
            {"sentence.average", "Average sentence length: {0, number}."},
            {"word.statistics", "Words statistics"},
            {"word.distinct", "Number of words: {0, number, integer} ({1, number, integer} distinct)."},
            {"word.min", "Maximal word: \"{0}\""},
            {"word.max", "Minimal word: \"{0}\""},
            {"word.minLength", "Minimal word length: {0, number, integer} (\"{0}\")."},
            {"word.maxLength", "Maximal word length: {0, number, integer} (\"{0}\")."},
            {"word.average", "Average word length: {0, number}."},
            {"number.statistics", "Words statistics."},
            {"number.distinct", "Number of numbers: {0, number, integer} ({1, number, integer} distinct)."},
            {"number.min", "Minimal number: {0, number}."},
            {"number.max", "Maximal number: {0, number}."},
            {"number.average", "Average number: {0, number}."},
            {"currency.statistics", "Currencies statistics."},
            {"currency.distinct", "Number of currencies: {0, number, integer} ({1, number, integer} distinct)."},
            {"currency.min", "Minimal currency: {0, number, currency}"},
            {"currency.max", "Maximal currency: {0, number, currency}"},
            {"currency.average", "Average currency: {0, number, currency}"},
            {"date.statistics", "Dates statistics"},
            {"date.distinct", "Number of dates: {0, number, integer} ({1, number, integer} distinct)."},
            {"date.min", "Minimal date: {0, date, medium}."},
            {"date.max", "Maximal date: {0, date, medium}."},
            {"date.average", "Average date: {0, date, medium}."},
            {"-o", "Write output"},
    };

    protected Object[][] getContents() {
        return CONTENTS;
    }
}
