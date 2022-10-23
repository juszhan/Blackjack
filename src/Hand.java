import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class Hand {
    private LinkedList<Card> m_dealtCards;

    // Invariant: sorted in ascending order
    private ArrayList<Byte> m_possibleValues;

    public Hand() {
        this.m_dealtCards = new LinkedList<>();
        this.m_possibleValues = new ArrayList<>();
        this.m_possibleValues.add((byte) 0);
    }

    public void add(Card card) {
        if (card.getM_value() == 1) {
            // Ace can be 1 or 11
            final int updateBoundary = this.m_possibleValues.size();
            for (int i = 0; i < updateBoundary; i++)
            {
                final byte updateValue = (byte) (this.m_possibleValues.get(i) + 11);
                this.m_possibleValues.add(updateValue);
            }

            for (int i = 0; i < updateBoundary; i++) {
                final byte updateValue = (byte) (this.m_possibleValues.get(i) + 1);
                this.m_possibleValues.set(i, updateValue);
            }

            Collections.sort(this.m_possibleValues);
        } else {
            for (int i = 0; i < this.m_possibleValues.size(); i++) {
                final byte updateValue = (byte) (this.m_possibleValues.get(i) + card.getM_value());
                this.m_possibleValues.set(i, updateValue);
            }
        }

        this.m_dealtCards.add(card);
        this.pruneBustedPossibleValuesExceptFirst();
    }

    public HandState getHandState() {
        HandState handState = HandState.UNKNOWN;

        for (byte val : this.m_possibleValues) {
            if (val > 21 && handState == HandState.UNKNOWN) {
                return HandState.BUST;
            } else if (val == 21) {
                return HandState.TWENTYONE;
            } else if (val == 0) {
                return HandState.UNKNOWN;
            } else {
                handState = HandState.VALID;
            }
        }

        return handState;
    }

    // Must have a non-bust value!
    public byte getBestValue() {
        ListIterator<Byte> iter = this.m_possibleValues.listIterator(this.m_possibleValues.size());
        while (iter.hasPrevious()) {
            byte value = iter.previous();
            if (value > 0 && value <= 21) {
                return value;
            }
        }

        throw new RuntimeException("No valid hand value");
    }

    public void clear() {
        this.m_dealtCards.clear();
        this.m_possibleValues.clear();
        this.m_possibleValues.add((byte) 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.m_dealtCards.toString());
        sb.append("\n");
        sb.append(this.m_possibleValues.toString());

        return sb.toString();
    }

    private void pruneBustedPossibleValuesExceptFirst() {
        // Only prune if the min value is valid
        if (!this.m_possibleValues.isEmpty() && this.m_possibleValues.get(0) <= 21) {
            while (this.m_possibleValues.get(this.m_possibleValues.size() - 1) > 21) {
                this.m_possibleValues.remove(this.m_possibleValues.size() - 1);
            }
        }
    }
}
