package ro.sandorr;

import ro.sandorr.grammar.Grammar;
import ro.sandorr.grammar.GrammarBuilder;
import ro.sandorr.parser.LL1ParsingTable;
import ro.sandorr.parser.LLOneParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Path grammarFile = Paths.get("language-grammar.txt");
        try {
            Grammar grammar = GrammarBuilder.fromFile(grammarFile);
            Map<String, Set<String>> first = LLOneParser.first(grammar);
            Map<String, Set<String>> follow = LLOneParser.follow(grammar, first);
            LL1ParsingTable parsingTable = LLOneParser.constructTable(grammar, first, follow);

            List<String> input = readFromFile(Paths.get("input.txt"));
            Map<String, String> pif = readPif(Paths.get("pif.txt"));
            List<String> processedInput = input.stream().map(pif::get).collect(Collectors.toList());
            System.out.println(processedInput);

            List<String> productionString = LLOneParser.parse(grammar, parsingTable, processedInput);

            System.out.println(first);
            System.out.println(follow);
            System.out.println(parsingTable);
            System.out.println(productionString);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> readPif(Path path) {
        try {
            Map<String, String> pif = new HashMap<>();
            Files.lines(path).forEach(line -> {
                final String[] parts = line.split(" ");
                pif.put(parts[1].trim(), parts[0].trim());
            });
            return pif;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static List<String> readFromFile(final Path input) {
        try {
            return Files.lines(input).map(String::trim).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
