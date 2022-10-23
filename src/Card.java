import java.lang.Math;

public class Card {
    private Suit m_suit;
    private byte m_value;

    public Card(byte value, Suit suit) throws IllegalArgumentException {
        if (value <= 0 || value > 13) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.m_suit = suit;
        this.m_value = value;
    }

    public byte getM_value() {
        return (byte) Math.min(this.m_value, 10);
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%02d_%s", this.m_value, this.m_suit);
    }
}