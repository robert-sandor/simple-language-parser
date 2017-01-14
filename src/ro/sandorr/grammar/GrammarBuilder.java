package ro.sandorr.grammar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by sando on 1/7/2017.
 */
public class GrammarBuilder {
    public static Grammar fromFile(final Path filePath) {
        Set<String> terminals = new HashSet<>();
        Set<String> nonTerminals = new HashSet<>();
        List<Production> productions = new ArrayList<>();
        List<String> start = new ArrayList<>();
        start.add("");

        try {
            Files.lines(filePath)
                    .forEach(line -> {
                        if (terminals.isEmpty()) {
                            terminals.addAll(Arrays.asList(line.split(" ")));
                        } else if (nonTerminals.isEmpty()) {
                            nonTerminals.addAll(Arrays.asList(line.split(" ")));
                        } else if (start.get(0).isEmpty()) {
                            start.add(0, line);
                        } else {
                            final String[] parts = line.split("->");
                            if (parts.length == 2) {
                                productions.add(new Production(
                                        Arrays.asList(parts[0].trim().split(" ")),
                                        Arrays.asList(parts[1].trim().split(" "))
                                ));
                            }
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new Grammar(terminals, nonTerminals, productions, start.get(0));
    }
}
