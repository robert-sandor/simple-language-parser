package ro.sandorr.grammar;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sando on 1/7/2017.
 */
public class Production {
    private final List<String> leftHand;
    private final List<String> rightHand;

    public Production(final List<String> leftHand, final List<String> rightHand) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public List<String> getLeftHand() {
        return leftHand;
    }

    public List<String> getRightHand() {
        return rightHand;
    }

    @Override
    public String toString() {
        return leftHand.stream().collect(Collectors.joining(" ")) +
                " -> " +
                rightHand.stream().collect(Collectors.joining(" "));
    }
}
