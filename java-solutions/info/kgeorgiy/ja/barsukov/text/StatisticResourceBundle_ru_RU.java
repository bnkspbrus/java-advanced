package info.kgeorgiy.ja.barsukov.text;

import java.util.ListResourceBundle;

public class StatisticResourceBundle_ru_RU extends ListResourceBundle {

    private final Object[][] CONTENTS = {
            {"usage", "Использование:"},
            {"ParsedFile", "Анализируемый файл \"{0}\""},
            {"summary.statistics", "Сводная статистика"},
            {"sentence.total", "Число предложений: {0, number, integer}."},
            {"word.total", "Число слов: {0, number, integer}."},
            {"number.total", "Число чисел: {0, number, integer}"},
            {"currency.total", "Число сумм: {0, number, integer}."},
            {"date.total", "Число дат: {0, number, integer}."},
            {"sentence.statistics", "Статистика по предложениям"},
            {"sentence.distinct", "Число предложений: {0, number, integer} ({1, number, integer} различных)."},
            {"sentence.min", "Минимальное предложение: \"{0}\"."},
            {"sentence.max", "Максимальное предложение: \"{0}\"."},
            {"sentence.minLength", "Минимальная длина предложения: \"{0} (\"{1}\")."},
            {"sentence.maxLength", "Максимальная длина предложения: {0} (\"{1}\")."},
            {"sentence.average", "Средняя длина предложения: {0, number}."},
            {"word.statistics", "Статистика по словам"},
            {"word.distinct", "Число слов: {0, number, integer} ({1, number, integer} различных)."},
            {"word.min", "Минимальное слово: \"{0}\""},
            {"word.max", "Максимальное слово: \"{0}\""},
            {"word.minLength", "Минимальная длина слова: {0, number, integer} (\"{0}\")."},
            {"word.maxLength", "Максимальная длина слова: {0, number, integer} (\"{0}\")."},
            {"word.average", "Средняя длина слова: {0, number}."},
            {"number.statistics", "Статистика по числам"},
            {"number.distinct", "Число чисел: {0, number, integer} ({1, number, integer} различных)."},
            {"number.min", "Минимальное число: {0, number}."},
            {"number.max", "Максимальное число: {0, number}."},
            {"number.average", "Среднее число: {0, number}."},
            {"currency.statistics", "Статистика по суммам денег"},
            {"currency.distinct", "Число сумм: {0, number, integer} ({1, number, integer} различных)."},
            {"currency.min", "Минимальная сумма: {0, number, currency}"},
            {"currency.max", "Максимальная сумма: {0, number, currency}"},
            {"currency.average", "Средняя сумма: {0, number, currency}"},
            {"date.statistics", "Статистика по датам"},
            {"date.distinct", "Число дат: {0, number, integer} ({1, number, integer} различных)."},
            {"date.min", "Минимальная дата: {0, date, medium}."},
            {"date.max", "Максимальная дата: {0, date, medium}."},
            {"date.average", " Средняя дата: {0, date, medium}.."},
            {"-o", "Write output"},
    };

    protected Object[][] getContents() {
        return CONTENTS;
    }
}
