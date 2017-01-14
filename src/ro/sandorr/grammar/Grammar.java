package ro.sandorr.grammar;

import java.util.List;
import java.util.Set;

/**
 * Created by sando on 1/7/2017.
 */
public class Grammar {
    public static final String EPSILON = "eps";

    private final Set<String> terminals;
    private final Set<String> nonTerminals;
    private final List<Production> productions;
    private final String start;

    public Grammar(Set<String> terminals, Set<String> nonTerminals, List<Production> productions, String start) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.productions = productions;
        this.start = start;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public String getStart() {
        return start;
    }

    @Override
    public String toString() {
        return String.format("Grammar : \n%s\n%s\n%s\n%s\n",
                terminals, nonTerminals, start, productions);
    }
}
