import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void getValue_oneToTen() {
        for (byte i = 1; i <= 10; i++)
        {
            Card c = new Card(i, Suit.CLUB);
            Assertions.assertEquals(i, c.getM_value());
        }
    }

    @Test
    void getValue_faceCards() {
        Card c11 = new Card((byte) 11, Suit.CLUB);
        Assertions.assertEquals(10, c11.getM_value());

        Card c12 = new Card((byte) 12, Suit.CLUB);
        Assertions.assertEquals(10, c12.getM_value());

        Card c13 = new Card((byte) 13, Suit.CLUB);
        Assertions.assertEquals(10, c13.getM_value());
    }

    @Test
    void getValue_zero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Card c = new Card((byte) 0, Suit.CLUB);
        });
    }

    @Test
    void getValue_negative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Card c = new Card((byte) -1, Suit.CLUB);
        });
    }

    @Test
    void getValue_overThirteen() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Card c = new Card((byte) 14, Suit.CLUB);
        });
    }
}