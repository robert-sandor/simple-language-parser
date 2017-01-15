package ro.sandorr;

import ro.sandorr.grammar.Grammar;
import ro.sandorr.grammar.GrammarBuilder;
import ro.sandorr.parser.LL1ParsingTable;
import ro.sandorr.parser.LLOneParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Path grammarFile = Paths.get("grammar.txt");
        try {
            Grammar grammar = GrammarBuilder.fromFile(grammarFile);
            Map<String, Set<String>> first = LLOneParser.first(grammar);
            Map<String, Set<String>> follow = LLOneParser.follow(grammar, first);
            LL1ParsingTable parsingTable = LLOneParser.constructTable(grammar, first, follow);

            String[] inputArray = {"a", "*", "(", "a", "+", "b", ")"};
            List<String> input = Arrays.asList(inputArray);

            try {
                List<String> productionString = LLOneParser.parse(grammar, parsingTable, input);

                System.out.println(first);
                System.out.println(follow);
                System.out.println(parsingTable);
                System.out.println(productionString);
            } catch (RuntimeException exc) {
                System.err.println("Unparseable input given!");
            }


        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
