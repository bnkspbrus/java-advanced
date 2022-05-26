package info.kgeorgiy.ja.barsukov.text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;
import java.util.*;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;

public class TextStatistics {

    private final String FIRST_BLOCK_FORMAT = "%s%n%s\t%s%n\t%s%n\t%s%n\t%s%n\t%s%n";

    private final String COMMON_BLOCK_FORMAT = "%s%n\t%s%n\t%s%n\t%s%n\t%s%n\t%s%n\t%s%n";
    private static final String UNAVAILABLE_LOCALE_APOLOGISE = "Sorry, this locale is unavailable for this system: %s";
    private final Locale textLocale, reportLocale;
    private final Path textFile, reportFile;

    private static final int[] DATA_STYLES = {DateFormat.FULL, DateFormat.LONG, DateFormat.SHORT, DateFormat.DEFAULT};

    private TextStatistics(Locale textLocale, Locale reportLocale, Path textFile, Path reportFile) {
        this.textLocale = textLocale;
        this.reportLocale = reportLocale;
        this.textFile = textFile;
        this.reportFile = reportFile;
    }


    private String text;

    public static void main(String[] args) {
        if (args == null || args.length != 4 || anyMatchNull(args)) {
            printUsage();
            System.exit(1);
        }
        Locale textLocale = Locale.forLanguageTag(args[0]), reportLocale = Locale.forLanguageTag(args[1]);
        if (unavailableLocale(textLocale)) {
            System.out.printf(UNAVAILABLE_LOCALE_APOLOGISE, textLocale);
        }
        if (unavailableLocale(reportLocale)) {
            System.out.printf(UNAVAILABLE_LOCALE_APOLOGISE, textLocale);
        }
        Path textFile = Path.of(args[2]), reportFile = Path.of(args[3]);
        new TextStatistics(textLocale, reportLocale, textFile, reportFile).run();
    }

    private ResourceBundle bundle;

    private void run() {
        try {
            text = Files.readString(textFile);
            try (BufferedWriter writer = Files.newBufferedWriter(reportFile)) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance(textLocale);
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(textLocale);
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, textLocale);
                Statistic<String> sentences = getStringsStatistic(BreakIterator.getSentenceInstance(textLocale));
                Statistic<String> words = getStringsStatistic(BreakIterator.getWordInstance(textLocale));
                Statistic<Number> numbers = getNumbersStatistic(numberFormat);
                Statistic<Number> currencies = getNumbersStatistic(currencyFormat);
                Statistic<Date> dates = getDatesStatistic();
                bundle = ResourceBundle.getBundle("StatisticResourceBundle", reportLocale);
                writer.write(firstBlock(sentences.total, words.total, numbers.total, currencies.total, dates.total));

//                writer.write()
            }
        } catch (IOException e) {
            System.out.println("Unable to read entity of text file");
        }
    }

    private String firstBlock(int sentences, int words, int numbers, int currencies, int dates) {
        return String.format(FIRST_BLOCK_FORMAT, format(bundle.getString("ParsedFile"), textFile.toString()),
                bundle.getString("summaryStatistics"), format(bundle.getString("numberOfSentences"), sentences),
                format(bundle.getString("numberOfWords"), words), format(bundle.getString("numberOfNumbers"), numbers),
                format(bundle.getString("numberOfCurrencies"), currencies),
                format(bundle.getString("numberOfDates"), dates));
    }

    private Statistic<Number> getNumbersStatistic(NumberFormat format) {
        List<Number> parts = splitNumbers(BreakIterator.getWordInstance(textLocale), format);
        Statistic<Number> statistic = getPartialStatistic(parts, Comparator.comparingDouble(Number::doubleValue));
        statistic.average = parts.stream().mapToDouble(Number::doubleValue).average().orElseThrow();
        return statistic;
    }

    private Statistic<Date> getDatesStatistic() {
        List<Date> parts = splitDates(BreakIterator.getWordInstance(textLocale), DATA_STYLES);
        Statistic<Date> statistic = getPartialStatistic(parts, Date::compareTo);
        statistic.average = parts.stream().mapToLong(Date::getTime).average().orElseThrow();
        return statistic;
    }

    private Statistic<String> getStringsStatistic(BreakIterator breakIterator) {
        List<String> parts = splitStrings(breakIterator);
        Statistic<String> statistic = getPartialStatistic(parts, Collator.getInstance(textLocale));
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        statistic.minLength = parts.stream().min(lengthComparator).orElseThrow().length();
        statistic.maxLength = parts.stream().max(lengthComparator).orElseThrow().length();
        statistic.average = parts.stream().mapToInt(String::length).average().orElseThrow();
        return statistic;
    }

    private List<Number> splitNumbers(BreakIterator boundary, NumberFormat format) {
        boundary.setText(text);
        ParsePosition position = new ParsePosition(0);
        final List<Number> parts = new ArrayList<>();
        for (int begin = boundary.first(), end = boundary.next(); end != BreakIterator.DONE; ) {
            position.setIndex(begin);
            begin = end;
            Number number = format.parse(text, position);
            if (number != null) {
                parts.add(number);
                end = boundary.following(position.getIndex());
            } else {
                end = boundary.next();
            }

        }
        return parts;
    }

    private List<Date> splitDates(BreakIterator boundary, int... styles) {
        List<DateFormat> formats = Arrays.stream(styles).mapToObj(
                style -> DateFormat.getDateInstance(style, textLocale)).toList();
        ParsePosition position = new ParsePosition(0);
        final List<Date> parts = new ArrayList<>();
        for (int begin = boundary.first(), end = boundary.next(); end != BreakIterator.DONE; ) {
            position.setIndex(begin);
            begin = end;
            Date date = null;
            for (DateFormat format : formats) {
                if ((date = format.parse(text, position)) != null) {
                    break;
                }
            }
            if (date != null) {
                parts.add(date);
                end = boundary.following(position.getIndex());
            } else {
                end = boundary.next();
            }

        }
        return parts;
    }

    private List<String> splitStrings(final BreakIterator boundary) {
        boundary.setText(text);
        final List<String> parts = new ArrayList<>();
        for (int begin = boundary.first(), end = boundary.next(); end != BreakIterator.DONE; begin = end, end =
                boundary.next()) {
            parts.add(text.substring(begin, end));
        }
        return parts;
    }

    // total, distinct, min, max
    private <T> Statistic<T> getPartialStatistic(List<T> parts, Comparator<? super T> comparator) {
        Statistic<T> statistic = new Statistic<>();
        statistic.total = parts.size();
        Stream<T> stream = parts.stream();
        statistic.distinct = (int) stream.distinct().count();
        statistic.min = stream.min(comparator).orElseThrow();
        statistic.max = stream.max(comparator).orElseThrow();
        return statistic;
    }

    private static boolean unavailableLocale(Locale locale) {
        return !Arrays.asList(Locale.getAvailableLocales()).contains(locale);
    }

    private static class Statistic<T> {
        int total, distinct;
        T min, max;
        int minLength, maxLength;
        double average;
    }

    private static void printUsage() {
        System.err.println("Usage:\njava TextStatistics <text locale> <output locale> <text file> <report file>");
    }

    private static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }
}
