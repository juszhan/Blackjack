public enum Suit {
    CLUB,
    DIAMOND,
    HEART,
    SPADE;

    @Override
    public String toString() {
        switch (this) {
            case CLUB:
                return "♣";
            case HEART:
                return "♥";
            case SPADE:
                return "♠";
            case DIAMOND:
                return "♦";
        }

        throw new NoSuchFieldError("Unexpected Suit type");
    }
}