package ro.sandorr.parser;

import ro.sandorr.grammar.Grammar;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sando on 1/7/2017.
 */
public class LLOneParser {
    public static Map<String, Set<String>> first(final Grammar grammar) {
        final Map<String, Set<String>> map = new HashMap<>();

        Set<String> epsValues = new HashSet<>();
        epsValues.add(Grammar.EPSILON);
        map.put(Grammar.EPSILON, epsValues);

        grammar.getTerminals().forEach(terminal -> {
            Set<String> values = new HashSet<>();
            values.add(terminal);
            map.put(terminal, values);
        });

        grammar.getNonTerminals().forEach(nonTerminal -> {
            final Set<String> values = new HashSet<>();
            grammar.getProductions().stream()
                    .filter(production -> production.getLeftHand().contains(nonTerminal))
                    .forEach(production -> {
                        String first = production.getRightHand().get(0);
                        if (grammar.getTerminals().contains(first) || Objects.equals(first, Grammar.EPSILON)) {
                            values.add(first);
                        }
                    });
            map.put(nonTerminal, values);
        });

        final boolean[] changed = {true};
        while (changed[0]) {
            changed[0] = false;
            grammar.getNonTerminals().forEach(nonTerminal -> grammar.getProductions().stream()
                    .filter(production -> production.getLeftHand().contains(nonTerminal))
                    .forEach(production -> {
                        if (production.getRightHand().stream().noneMatch(token -> map.get(token).isEmpty())) {
                            Set<String> values = new HashSet<>();

                            for (String token : production.getRightHand()) {
                                values.addAll(map.get(token));
                                if (!map.get(token).contains(Grammar.EPSILON)) {
                                    values.remove(Grammar.EPSILON);
                                    break;
                                }
                            }

                            if (!map.get(nonTerminal).containsAll(values)) {
                                map.get(nonTerminal).addAll(values);
                                changed[0] = true;
                            }
                        }
                    }));
        }

        grammar.getTerminals().forEach(map::remove);
        map.remove(Grammar.EPSILON);

        return map;
    }
}
