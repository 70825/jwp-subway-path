package subway.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import static subway.domain.Direction.DOWN;
import static subway.domain.Direction.MID;
import static subway.domain.Direction.NONE;
import static subway.domain.Direction.UP;

public class Station {

    private static final Pattern PATTERN = Pattern.compile("^[가-힣0-9]*$");
    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 9;

    private final Long id;
    private final String name;
    private Direction direction;

    private Station(final Long id, final String name) {
        validate(name);

        this.id = id;
        this.name = name;
        this.direction = NONE;
    }

    public static Station from(final String name) {
        return new Station(null, name);
    }

    public static Station of(final Long id, final String name) {
        return new Station(id, name);
    }

    private void validate(final String name) {
        validateFormat(name);
        validateLength(name);
    }

    private void validateLength(final String name) {
        if (!(MINIMUM_LENGTH <= name.length() && name.length() <= MAXIMUM_LENGTH)) {
            throw new IllegalArgumentException("역 이름은 2글자 ~ 9글자만 가능합니다.");
        }
    }

    private void validateFormat(final String name) {
        if (!PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("역 이름은 한글과 숫자만 가능합니다.");
        }
    }

    public void changeDirection(final Direction direction) {
        this.direction = direction;
    }

    public boolean isEndStation() {
        return direction.matches(UP) || direction.matches(DOWN);
    }

    public boolean isMidStation() {
        return direction.matches(MID);
    }

    public boolean isUpStation() {
        return direction.matches(UP);
    }

    public boolean isDownStation() {
        return direction.matches(DOWN);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Station station = (Station) o;
        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }
}
