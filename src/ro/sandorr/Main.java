package ro.sandorr;

import ro.sandorr.grammar.Grammar;
import ro.sandorr.grammar.GrammarBuilder;
import ro.sandorr.parser.LLOneParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Path grammarFile = Paths.get("grammar.txt");
        try {
            Grammar grammar = GrammarBuilder.fromFile(grammarFile);
            Map<String, Set<String>> first = LLOneParser.first(grammar);
            System.out.println(first);
            System.out.println(LLOneParser.follow(grammar, first));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
