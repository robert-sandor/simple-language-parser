package ro.sandorr.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sando on 1/15/2017.
 */
public class LL1ParsingTableCell {
    public static final LL1ParsingTableCell ACCEPT = new LL1ParsingTableCell(new ArrayList<>(), -1);
    public static final LL1ParsingTableCell POP = new LL1ParsingTableCell(new ArrayList<>(), -2);

    private final List<String> production;
    private final Integer index;

    public LL1ParsingTableCell(List<String> production, Integer index) {
        this.production = production;
        this.index = index;
    }

    public List<String> getProduction() {
        return production;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LL1ParsingTableCell that = (LL1ParsingTableCell) o;

        return (production != null ? production.equals(that.production) : that.production == null) && (index != null ? index.equals(that.index) : that.index == null);
    }

    @Override
    public int hashCode() {
        int result = production != null ? production.hashCode() : 0;
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (this.equals(ACCEPT)) {
            return "\tACC ";
        } else if (this.equals(POP)) {
            return "\tPOP ";
        } else {
            return "\t" + production + " , " + index + "\t";
        }
    }
}
