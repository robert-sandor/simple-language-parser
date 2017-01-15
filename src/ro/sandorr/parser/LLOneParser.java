package ro.sandorr.parser;

import ro.sandorr.grammar.Grammar;
import ro.sandorr.grammar.Production;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sando on 1/7/2017.
 */
public class LLOneParser {

    public static final String DOLLAR = "$";

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

//        grammar.getTerminals().forEach(map::remove);
//        map.remove(Grammar.EPSILON);

        return map;
    }

    public static Map<String, Set<String>> follow(final Grammar grammar, final Map<String, Set<String>> firstResult) {
        final Map<String, Set<String>> followResult = new HashMap<>();

        grammar.getNonTerminals().forEach(nonTerminal -> followResult.put(nonTerminal, new HashSet<>()));
        followResult.get(grammar.getStart()).add(Grammar.EPSILON);

        final boolean[] changed = {true};
        while (changed[0]) {
            changed[0] = false;
            grammar.getNonTerminals().forEach(nonTerminal -> grammar.getProductions().stream()
                    .filter(production -> production.getRightHand().contains(nonTerminal))
                    .forEach(production -> {
                        final List<String> rhs = production.getRightHand();
                        final List<String> sequence = rhs.subList(rhs.indexOf(nonTerminal) + 1, rhs.size());


                        final Set<String> firstOfSequence = firstSequence(firstResult, sequence);
                        final Set<String> firstOfSequenceWithoutEpsilon = firstOfSequence.stream()
                                .filter(token -> !token.equals(Grammar.EPSILON)).collect(Collectors.toSet());

                        if (!followResult.get(nonTerminal).containsAll(firstOfSequenceWithoutEpsilon)) {
                            followResult.get(nonTerminal).addAll(firstOfSequenceWithoutEpsilon);
                            changed[0] = true;
                        }

                        if (firstOfSequence.contains(Grammar.EPSILON)) {
                            if (!followResult.get(nonTerminal).containsAll(followResult.get(production.getLeftHand().get(0)))) {
                                followResult.get(nonTerminal).addAll(followResult.get(production.getLeftHand().get(0)));
                                changed[0] = true;
                            }
                        }
                    }));
        }

        return followResult;
    }

    public static Set<String> firstSequence(final Map<String, Set<String>> first, final List<String> sequence) {
        Set<String> result = new HashSet<>();
        result.add(Grammar.EPSILON);
        for (String token : sequence) {
            result.addAll(first.get(token));
            if (!first.get(token).contains(Grammar.EPSILON)) {
                result.remove(Grammar.EPSILON);
                break;
            }
        }
        return result;
    }
}
