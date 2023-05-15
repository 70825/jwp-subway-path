package subway.domain.section;

import subway.domain.Distance;
import subway.domain.Station;

import java.util.Objects;
import java.util.function.Function;

import static subway.domain.Direction.MID;

public class Section {

    private final Station upStation;
    private final Station downStation;
    private final Distance distance;

    private Section(final Station upStation, final Station downStation, final Distance distance) {
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public static Section of(final Station upStation, final Station downStation, final Distance distance) {
        return new Section(upStation, downStation, distance);
    }

    public boolean isEndStation() {
        final Station upStation = getUpStation();
        final Station downStation = getDownStation();

        return upStation.isDownStation() || downStation.isUpStation();
    }

    public Section createUpSection(final Section originSection) {
        final Station upStation = originSection.getUpStation();
        final Distance distance = calculateDistance(originSection, Section::getUpStation);
        final Station newStation = findOtherStation(upStation);
        newStation.changeDirection(MID);
        return Section.of(upStation, newStation, distance);
    }

    public Section createDownSection(final Section originSection) {
        final Station downStation = originSection.getDownStation();
        final Distance distance = calculateDistance(originSection, Section::getDownStation);
        final Station newStation = findOtherStation(downStation);
        newStation.changeDirection(MID);
        return Section.of(newStation, downStation, distance);
    }

    private Distance calculateDistance(final Section originSection,
                                       final Function<Section, Station> getStationFromSection) {
        final Distance originDistance = originSection.getDistance();
        final Station currentStation = getStationFromSection.apply(this);
        final Station originStation = getStationFromSection.apply(originSection);

        if (currentStation.equals(originStation)) {
            return this.distance;
        }

        if (this.distance.getDistance() >= originDistance.getDistance()) {
            throw new IllegalArgumentException("등록되는 역 중간에 다른 역이 존재합니다.");
        }

        return originDistance.minus(this.distance);
    }

    public boolean contains(final Station station) {
        return upStation.equals(station) || downStation.equals(station);
    }

    public Station findOtherStation(final Station station) {
        if (upStation.equals(station)) {
            return downStation;
        }
        return upStation;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Section section = (Section) o;
        return Objects.equals(upStation, section.upStation)
                && Objects.equals(downStation, section.downStation)
                && Objects.equals(distance, section.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upStation, downStation, distance);
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public Distance getDistance() {
        return distance;
    }
}
