package subway.domain;

public enum Direction {

    UP,
    DOWN,
    MID,
    NONE;

    public boolean matches(final Direction direction) {
        return this == direction;
    }
}
