package info.kgeorgiy.ja.barsukov.text;

import java.util.ListResourceBundle;

public class StatisticResourceBundle_ru_RU extends ListResourceBundle {

    private final Object[][] CONTENTS = {
            {"usage", "Использование:"},
            {"ParsedFile", "Анализируемый файл \"{0}\""},
            {"summaryStatistics", "Сводная статистика"},
            {"numberOfSentences", "число предложений: {0, number, integer}."},
            {"numberOfWords", "число слов: {0, number, integer}"},
            {"numberOfCurrencies", "число сумм: {0, number, integer}"},
            {"numberOfDates", "число дат: {0, number, integer}"},
            {"sentencesStatistics", "Статистика по предложениям"},
            {"differentSentences", "Число предложений: {0, number, integer} ({1, number, integer} различных)."},
            {"minimalSentence", "Минимальное предложение: \"{0}\"."},
            {"maximalSentence", "Максимальное предложение: \"{0}\"."},
            {"minimalSentenceLength", "Максимальное предложение: \"{0} (\"{1}\")."},
            {"maximalSentenceLength", "Минимальная длина предложения: {0} (\"{1}\")."},
            {"maximalSentenceLength", "Максимальная длина предложения: {0} (\"{1}\")."},
            {"averageSentenceLength", "Средняя длина предложения: {0, number}."},
            {"wordsStatistics", "Статистика по словам"},
            {"numberOfWords", "Число слов: {0, number, integer} ({1, number, integer} различных)."},
            {"minimalWord", "Минимальное слово: \"{0}\""},
            {"maximalWord", "Максимальное слово: \"{0}\""},
            {"minimalWordLength", "Минимальная длина слова: {0, number, integer} (\"{0}\")."},
            {"maximalWordLength", "Минимальная длина слова: {0, number, integer} (\"{0}\")."},
            {"averageWordLength", "Средняя длина слова: {0, number}."},
            {"numbersStatistics", "Статистика по числам."},
            {"numberOfNumbers", "Число чисел: {0, number, integer} ({1, number, integer} различных)."},
            {"minimalNumber", "Минимальное число: {0, number}."},
            {"maximalNumber", "Максимальное число: {0, number}."},
            {"averageNumber", "Среднее число: {0, number}."},
            {"currenciesStatistics", "Статистика по суммам денег"},
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
