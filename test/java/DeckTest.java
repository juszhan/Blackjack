import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

class DeckTest {

    @Test
    void dealNextCard_dealAllCardsOnePack() {
        HashSet<Card> uniqueCardSet = new HashSet<>(52);

        Deck d = new Deck((byte) 1, (byte) 50);
        d.initialize();

        for (int i = 0; i < 1 * 52; i++) {
            Assertions.assertTrue(uniqueCardSet.add(d.dealNextCard()));
        }

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            d.dealNextCard();
        });
    }

    @Test
    void dealNextCard_dealAllCardsSixPacks() {
        HashMap<Card, Byte> uniqueCardSet = new HashMap<>(52);

        Deck d = new Deck((byte) 6, (byte) 50);
        d.initialize();

        for (int i = 0; i < 6 * 52; i++) {
            final Card nextCard = d.dealNextCard();
            byte seenCount = uniqueCardSet.getOrDefault(nextCard, (byte) 0);
            uniqueCardSet.put(nextCard, ++seenCount);
        }

        for (final byte value : uniqueCardSet.values()) {
            Assertions.assertEquals((byte) 6, value);
        }
    }

    @Test
    void shouldShuffle_false() {
        Deck d = new Deck((byte) 1, (byte) 50);
        d.initialize();

        d.dealNextCard();

        Assertions.assertFalse(d.shouldShuffle());
    }

    @Test
    void shouldShuffle_true() {
        Deck d = new Deck((byte) 1, (byte) 50);
        d.initialize();

        for (int i = 0; i <= 52 / 2; i++)
        {
            d.dealNextCard();
        }

        Assertions.assertTrue(d.shouldShuffle());
    }
}