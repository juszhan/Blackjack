import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void getHandState_allStates() {
        Hand h = new Hand();
        Assertions.assertEquals(HandState.UNKNOWN, h.getHandState());

        h.add(new Card((byte) 1, Suit.CLUB));
        Assertions.assertEquals(HandState.VALID, h.getHandState());

        h.add(new Card((byte) 10, Suit.CLUB));
        Assertions.assertEquals(HandState.TWENTYONE, h.getHandState());

        h.add(new Card((byte) 13, Suit.CLUB));
        Assertions.assertEquals(HandState.TWENTYONE, h.getHandState());

        h.add(new Card((byte) 1, Suit.CLUB));
        Assertions.assertEquals(HandState.BUST, h.getHandState());
    }

    @Test
    void getBestValue_emptyHand() {
        Hand h = new Hand();

        Assertions.assertThrows(RuntimeException.class, () -> {
            h.getBestValue();
        });
    }

    @Test
    void getBestValue_bust() {
        Hand h = new Hand();

        h.add(new Card((byte) 10, Suit.CLUB));
        h.add(new Card((byte) 10, Suit.CLUB));
        h.add(new Card((byte) 10, Suit.CLUB));

        Assertions.assertThrows(RuntimeException.class, () -> {
            h.getBestValue();
        });
    }

    @Test
    void getBestValue_onePossibleValue() {
        Hand h = new Hand();

        h.add(new Card((byte) 2, Suit.CLUB));
        Assertions.assertEquals(2, h.getBestValue());

        h.add(new Card((byte) 5, Suit.CLUB));
        Assertions.assertEquals(7, h.getBestValue());

        h.add(new Card((byte) 13, Suit.CLUB));
        Assertions.assertEquals(17, h.getBestValue());
    }

    @Test
    void getBestValue_multiplePossibleValues() {
        Hand h = new Hand();

        for (int i = 0; i < 21; i++)
        {
            h.add(new Card((byte) 1, Suit.CLUB));
            Assertions.assertEquals(i <= 10 ? 11 + i : i + 1, h.getBestValue());
        }
    }

    @Test
    void clear_shouldMakeUnknownState() {
        Hand h = new Hand();

        h.add(new Card((byte) 1, Suit.CLUB));
        Assertions.assertEquals(HandState.VALID, h.getHandState());

        h.clear();
        Assertions.assertEquals(HandState.UNKNOWN, h.getHandState());
    }
}