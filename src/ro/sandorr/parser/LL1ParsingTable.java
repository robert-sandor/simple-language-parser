package ro.sandorr.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sando on 1/15/2017.
 */
public class LL1ParsingTable {
    private  final Map<String, Map<String, LL1ParsingTableCell>> table;

    public LL1ParsingTable() {
        table = new HashMap<>();
    }

    public Map<String, Map<String, LL1ParsingTableCell>> getTable() {
        return table;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        table.forEach((rowIndex, row) -> {
            stringBuilder.append(rowIndex).append(" : \t");

            row.forEach((columnIndex, column) -> stringBuilder.append(columnIndex).append(" => ").append(column));

            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }
}
