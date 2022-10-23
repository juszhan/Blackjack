import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private byte m_numPacks;
    private byte m_shuffleThreshold;

    private boolean m_isInitialized;
    private int m_cursor;
    private ArrayList<Card> m_cards;

    public Deck() {
        this.m_numPacks = 6;
        this.m_shuffleThreshold = 50;
        this.m_isInitialized = false;
        this.m_cursor = 0;
    }

    public Deck(byte numPacks, byte shuffleThreshold) throws IllegalArgumentException {
        if (numPacks <= 0) {
            throw new IllegalArgumentException("Invalid numPacks");
        }

        if (shuffleThreshold <= 0 || shuffleThreshold >= 100)
        {
            throw new IllegalArgumentException("Invalid shuffleThreshold");
        }

        this.m_numPacks = numPacks;
        this.m_shuffleThreshold = shuffleThreshold;
        this.m_isInitialized = false;
        this.m_cursor = 0;
    }

    public void initialize() {
        final int totalCardCount = this.m_numPacks * 52;
        this.m_cards = new ArrayList<>(totalCardCount);

        for (byte _packIndex = 0; _packIndex < this.m_numPacks; _packIndex++) {
            for (Suit suit : Suit.values()) {
                for (byte value = 1; value <= 13; value++) {
                    this.m_cards.add(new Card(value, suit));
                }
            }
        }

        this.m_isInitialized = true;
        this.shuffle();
    }

    public Card dealNextCard() throws IllegalStateException, ArrayIndexOutOfBoundsException {
        if (!this.m_isInitialized) {
            throw new IllegalStateException("Not initialized");
        }

        if (this.m_cursor >= this.m_cards.size()) {
            throw new ArrayIndexOutOfBoundsException("No more cards to deal");
        }

        Card nextCard = this.m_cards.get(this.m_cursor);
        this.m_cursor++;
        return nextCard;
    }

    public boolean shouldShuffle() throws IllegalStateException {
        if (!this.m_isInitialized) {
            throw new IllegalStateException("Not initialized");
        }

        return this.m_cursor >= (this.m_cards.size() * (this.m_shuffleThreshold / 100.0));
    }

    public void shuffle() throws IllegalStateException {
        if (!this.m_isInitialized) {
            throw new IllegalStateException("Not initialized");
        }

        this.m_cursor = 0;
        Collections.shuffle(this.m_cards);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "numPacks: %d, cursor: %d",
                this.m_numPacks,
                this.m_cursor));

        if (this.m_isInitialized) {
            sb.append(String.format(
                    ", cards(%d): %s",
                    this.m_cards.size(),
                    this.m_cards.toString()));
        } else {
            sb.append(", cards: <Not Initialized>");
        }

        return sb.toString();
    }
}
