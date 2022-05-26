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
                Statistic<String> sentenceStatistics = getStringsStatistic(
                        BreakIterator.getSentenceInstance(textLocale));
                Statistic<String> wordStatistics = getStringsStatistic(BreakIterator.getWordInstance(textLocale));
                Statistic<Number> numberStatistics = getNumbersStatistic(NumberFormat.getNumberInstance(textLocale));
                Statistic<Number> currencyStatistics = getNumbersStatistic(NumberFormat.getNumberInstance(textLocale));
                Statistic<Date> dateStatistics = getDatesStatistic();
                bundle = ResourceBundle.getBundle("info.kgeorgiy.ja.barsukov.text.StatisticResourceBundle", reportLocale);
                writer.write(summaryBlock(sentenceStatistics.total, wordStatistics.total, numberStatistics.total,
                        currencyStatistics.total, dateStatistics.total));
                writer.newLine();
                writer.write(statisticToString(sentenceStatistics, "sentence"));
                writer.newLine();
                writer.write(statisticToString(wordStatistics, "word"));
                writer.newLine();
                writer.write(statisticToString(numberStatistics, "number"));
                writer.newLine();
                writer.write(statisticToString(currencyStatistics, "currency"));
                writer.newLine();
                writer.write(statisticToString(dateStatistics, "date"));
            }
        } catch (IOException e) {
            System.out.println("Unable to read entity of text file");
        }
    }

    private <T> String statisticToString(Statistic<T> statistic, String prefix) {
        List<String> shift;
        if (prefix.equals("word") || prefix.equals("sentence")) {
            shift = getShiftedList(format(bundle.getString(prefix + ".distinct"), statistic.total, statistic.distinct),
                    format(bundle.getString(prefix + ".min"), statistic.min),
                    format(bundle.getString(prefix + ".max"), statistic.max),
                    format(bundle.getString(prefix + ".minLength"), statistic.minLength),
                    format(bundle.getString(prefix + ".maxLength"), statistic.maxLength),
                    format(bundle.getString(prefix + ".average"), statistic.average));
        } else {
            shift = getShiftedList(format(bundle.getString(prefix + ".distinct"), statistic.total, statistic.distinct),
                    format(bundle.getString(prefix + ".min"), statistic.min),
                    format(bundle.getString(prefix + ".max"), statistic.max),
                    format(bundle.getString(prefix + ".average"), statistic.average));
        }
        List<String> elements = new ArrayList<>();
        elements.add(bundle.getString(prefix + ".statistics"));
        elements.addAll(shift);
        return String.join("\n", elements);
    }

    private String summaryBlock(int sentences, int words, int numbers, int currencies, int dates) {
        List<String> elements = new ArrayList<>();
        elements.add(format(bundle.getString("ParsedFile"), textFile.toString()));
        elements.add(bundle.getString("summary.statistics"));
        elements.addAll(getShiftedList(
                format(bundle.getString("sentence.total"), sentences),
                format(bundle.getString("word.total"), words),
                format(bundle.getString("number.total"), numbers),
                format(bundle.getString("currency.total"), currencies),
                format(bundle.getString("date.total"), dates)));
        return String.join("\n", elements);
    }

    List<String> getShiftedList(String... params) {
        return Stream.of(params).map(s -> "\t" + s).toList();
    }


    private Statistic<Number> getNumbersStatistic(NumberFormat format) {
        List<Number> parts = splitNumbers(BreakIterator.getWordInstance(textLocale), format);
        Statistic<Number> statistic = getPartialStatistic(parts, Comparator.comparingDouble(Number::doubleValue));
        statistic.average = parts.stream().mapToDouble(Number::doubleValue).average().orElse(0);
        return statistic;
    }

    private Statistic<Date> getDatesStatistic() {
        List<Date> parts = splitDates(BreakIterator.getWordInstance(textLocale), DATA_STYLES);
        Statistic<Date> statistic = getPartialStatistic(parts, Date::compareTo);
        statistic.average = parts.stream().mapToLong(Date::getTime).average().orElse(0);
        return statistic;
    }

    private Statistic<String> getStringsStatistic(BreakIterator breakIterator) {
        List<String> parts = splitStrings(breakIterator);
        Statistic<String> statistic = getPartialStatistic(parts, Collator.getInstance(textLocale));
        statistic.minLength = parts.stream().mapToInt(String::length).min().orElse(0);
        statistic.maxLength = parts.stream().mapToInt(String::length).max().orElse(0);
        statistic.average = parts.stream().mapToInt(String::length).average().orElse(0);
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
            parts.add(text.substring(begin, end).trim());
        }
        return parts;
    }

    // total, distinct, min, max
    private <T> Statistic<T> getPartialStatistic(List<T> parts, Comparator<? super T> comparator) {
        Statistic<T> statistic = new Statistic<>();
        statistic.total = parts.size();
        statistic.distinct = (int) parts.stream().distinct().count();
        statistic.min = parts.stream().min(comparator).orElse(null);
        statistic.max = parts.stream().max(comparator).orElse(null);
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
