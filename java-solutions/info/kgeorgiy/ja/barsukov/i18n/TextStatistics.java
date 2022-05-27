package info.kgeorgiy.ja.barsukov.i18n;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;

public class TextStatistics {
    private static final String UNAVAILABLE_LOCALE_APOLOGISE = "Sorry, this locale is unavailable for this system: %s";
    private final Locale textLocale, reportLocale;
    private Path textFile;

    private static final int[] DATA_STYLES = {
            DateFormat.FULL,
            DateFormat.LONG,
            DateFormat.SHORT,
            DateFormat.DEFAULT,
            DateFormat.MEDIUM,
            DateFormat.DATE_FIELD,
    };

    /**
     * Constructor for {@link TextStatistics}.
     *
     * @param textLocale   locale of text file to statistics.
     * @param reportLocale locale of retort file for statistics.
     */
    public TextStatistics(Locale textLocale, Locale reportLocale) {
        this.textLocale = textLocale;
        this.reportLocale = reportLocale;
        Locale.setDefault(reportLocale);
    }

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
        new TextStatistics(textLocale, reportLocale).writeStatisticsToFile(textFile, reportFile);
    }

    private ResourceBundle bundle;

    /**
     * Writes statistics in {@code reportFile} specified in class constructor
     * {@link TextStatistics#TextStatistics(Locale, Locale)}.
     *
     * @param textFile   path to text file to parsing.
     * @param reportFile path to report file for statistics.
     */
    private void writeStatisticsToFile(Path textFile, Path reportFile) {
        try {
            this.textFile = textFile;
            String text = Files.readString(textFile);
            Statistics<String> sentenceStatistics = getSentenceStatistics(text);
            Statistics<String> wordStatistics = getWordStatistics(text);
            Statistics<Number> numberStatistics = getNumberStatistics(text);
            Statistics<Number> currencyStatistics = getCurrencyStatistics(text);
            Statistics<Date> dateStatistics = getDateStatistics(text);

            bundle = ResourceBundle.getBundle("info.kgeorgiy.ja.barsukov.text.StatisticResourceBundle", reportLocale);
            Files.writeString(reportFile, String.join("\n",
                    summaryToString(sentenceStatistics.total, wordStatistics.total, numberStatistics.total,
                            currencyStatistics.total, dateStatistics.total),
                    statisticToString(sentenceStatistics, "sentence"), statisticToString(wordStatistics, "word"),
                    statisticToString(numberStatistics, "number"), statisticToString(currencyStatistics, "currency"),
                    statisticToString(dateStatistics, "date")));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Returns sentence statistics for {@code text} by {@code textLocale} specified
     * {@link TextStatistics#TextStatistics(Locale, Locale)}.
     *
     * @param text string representation of text.
     * @return {@code Statistics<Number>} {@link Statistics} with data statistics.
     */
    public Statistics<String> getSentenceStatistics(String text) {

        return getStringsStatistic(text, BreakIterator.getSentenceInstance(textLocale));
    }

    /**
     * Returns word statistics for {@code text} by {@code textLocale} specified in
     * {@link TextStatistics#TextStatistics(Locale, Locale)}.
     *
     * @param text string representation of text.
     * @return {@code Statistics<String>} {@link Statistics} with data statistics.
     */
    public Statistics<String> getWordStatistics(String text) {

        return getStringsStatistic(text, BreakIterator.getWordInstance(textLocale));
    }

    /**
     * Returns number statistics for {@code text} by {@code textLocale} specified in
     * {@link TextStatistics#TextStatistics(Locale, Locale)}.
     *
     * @param text string representation of text.
     * @return {@code Statistics<Number>} {@link Statistics} with data statistics.
     */
    public Statistics<Number> getNumberStatistics(String text) {

        return getNumberStatistics(text, NumberFormat.getNumberInstance(textLocale));
    }

    /**
     * Returns currency statistics for {@code text} by {@code textLocale} specified in
     * {@link TextStatistics#TextStatistics(Locale, Locale)}.
     *
     * @param text string representation of text.
     * @return {@code Statistics<Number>} {@link Statistics} with data statistics.
     */
    public Statistics<Number> getCurrencyStatistics(String text) {

        return getNumberStatistics(text, NumberFormat.getCurrencyInstance(textLocale));
    }

    /**
     * Returns date statistics for {@code text} by {@code textLocale} specified in
     * {@link TextStatistics#TextStatistics(Locale, Locale)}.
     *
     * @param text string representation of text.
     * @return {@code Statistics<Date>} {@link Statistics} with data statistics.
     */
    public Statistics<Date> getDateStatistics(String text) {

        List<Date> parts = splitDates(text, BreakIterator.getWordInstance(textLocale), DATA_STYLES);
        Statistics<Date> statistic = getPartialStatistic(parts, Date::compareTo);
        statistic.average = parts.stream().mapToLong(Date::getTime).average().orElse(0);
        return statistic;
    }


    private <T> String statisticToString(Statistics<T> statistic, String prefix) {
        List<String> shift;
        if (prefix.equals("word") || prefix.equals("sentence")) {
            shift = getShiftedList(format(bundle.getString(prefix + ".distinct"), statistic.total, statistic.distinct),
                    format(bundle.getString(prefix + ".min"), statistic.min),
                    format(bundle.getString(prefix + ".max"), statistic.max),
                    format(bundle.getString(prefix + ".minLength"), statistic.minLength.length(), statistic.minLength),
                    format(bundle.getString(prefix + ".maxLength"), statistic.maxLength.length(), statistic.maxLength),
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

    private String summaryToString(int sentences, int words, int numbers, int currencies, int dates) {
        List<String> elements = new ArrayList<>();
        elements.add(format(bundle.getString("ParsedFile"), textFile.toString()));
        elements.add(bundle.getString("summary.statistics"));
        elements.addAll(getShiftedList(format(bundle.getString("sentence.total"), sentences),
                format(bundle.getString("word.total"), words), format(bundle.getString("number.total"), numbers),
                format(bundle.getString("currency.total"), currencies), format(bundle.getString("date.total"), dates)));
        return String.join("\n", elements);
    }

    List<String> getShiftedList(String... params) {
        return Stream.of(params).map(s -> "\t" + s).toList();
    }


    private Statistics<Number> getNumberStatistics(String text, NumberFormat format) {
        List<Number> parts = splitNumbers(text, BreakIterator.getWordInstance(textLocale), format);
        Statistics<Number> statistic = getPartialStatistic(parts, Comparator.comparingDouble(Number::doubleValue));
        statistic.average = parts.stream().mapToDouble(Number::doubleValue).average().orElse(0);
        return statistic;
    }

    private Statistics<String> getStringsStatistic(String text, BreakIterator breakIterator) {
        List<String> parts = splitStrings(text, breakIterator).stream().filter(
                        part -> part.codePoints().anyMatch(Character::isLetter))
                .collect(Collectors.toList());
        Statistics<String> statistic = getPartialStatistic(parts, Collator.getInstance(textLocale));
        statistic.minLength = parts.stream().min(Comparator.comparingInt(String::length)).orElse(null);
        statistic.maxLength = parts.stream().max(Comparator.comparingInt(String::length)).orElse(null);
        statistic.average = parts.stream().mapToInt(String::length).average().orElse(0);
        return statistic;
    }

    private List<Number> splitNumbers(String text, BreakIterator boundary, NumberFormat format) {
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

    private List<Date> splitDates(String text, BreakIterator boundary, int... styles) {
        boundary.setText(text);
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

    private List<String> splitStrings(String text, final BreakIterator boundary) {
        boundary.setText(text);
        final List<String> parts = new ArrayList<>();
        for (int begin = boundary.first(), end = boundary.next(); end != BreakIterator.DONE; begin = end, end =
                boundary.next()) {
            parts.add(text.substring(begin, end).trim());
        }
        return parts;
    }

    // total, distinct, min, max
    private <T> Statistics<T> getPartialStatistic(List<T> parts, Comparator<? super T> comparator) {
        Statistics<T> statistic = new Statistics<>();
        statistic.total = parts.size();
        statistic.distinct = (int) parts.stream().distinct().count();
        statistic.min = parts.stream().min(comparator).orElse(null);
        statistic.max = parts.stream().max(comparator).orElse(null);
        return statistic;
    }

    private static boolean unavailableLocale(Locale locale) {
        return !Arrays.asList(Locale.getAvailableLocales()).contains(locale);
    }

    /**
     * Simple class that contains statistics. Used by methods get*Statistics as a return value.
     *
     * @param <T> type of data for statistics.
     */
    public static class Statistics<T> {
        int total, distinct;
        T min, max;
        String maxLength, minLength;

        double average;
    }

    private static void printUsage() {
        System.err.println("Usage:\njava TextStatistics <text locale> <output locale> <text file> <report file>");
    }

    private static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }
}
