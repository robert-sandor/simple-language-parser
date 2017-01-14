package ro.sandorr;

import ro.sandorr.grammar.Grammar;
import ro.sandorr.grammar.GrammarBuilder;
import ro.sandorr.parser.LLOneParser;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path grammarFile = Paths.get("grammar.txt");
        try {
            Grammar grammar = GrammarBuilder.fromFile(grammarFile);
            System.out.println(LLOneParser.first(grammar));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
