package subway.domain.section;

import subway.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sections {

    private final List<Section> sections;

    private Sections(final List<Section> sections) {
        this.sections = sections;
    }

    public static Sections create() {
        return new Sections(new ArrayList<>());
    }

    public void addSection(final Section section) {
        addSection(size(), section);
    }

    public void addSection(final int index, final Section section) {
        validateRegistration(section);
        sections.add(index, section);
    }

    private void validateRegistration(final Section section) {
        if (sections.contains(section)) {
            throw new IllegalArgumentException("이미 등록된 경로입니다.");
        }
    }

    public int findIndex(final Section section) {
        return sections.indexOf(section);
    }

    public void removeSectionByIndex(final int index) {
        sections.remove(index);
    }

    public int size() {
        return sections.size();
    }

    public boolean isInitial(final Section ignore) {
        return sections.size() == 0;
    }

    public boolean isUpEndSection(final Section insertSection) {
        final Station upEndStation = sections.get(0).getUpStation();

        return upEndStation.equals(insertSection.getDownStation());
    }

    public boolean isDownEndSection(final Section insertSection) {
        final Station downEndStation = sections.get(size() - 1).getDownStation();

        return downEndStation.equals(insertSection.getUpStation());
    }

    public boolean isMidSection(final Section insertSection) {
        final boolean isEndStation = isUpEndSection(insertSection) || isDownEndSection(insertSection);

        return !isEndStation;
    }

    public Section findOriginSection(final Section findSection) {
        return sections.stream()
                .filter(section -> isEqualStation(section, findSection, Section::getUpStation)
                        || isEqualStation(section, findSection, Section::getDownStation))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("노선에 저장된 역이 없습니다"));
    }

    public boolean isEqualStation(final Section section,
                                  final Section findSection,
                                  final Function<Section, Station> getStation) {
        final Station station = getStation.apply(section);
        final Station findStation = getStation.apply(findSection);

        return station.equals(findStation);
    }

    public void delete(final Section section) {
        sections.remove(section);
    }

    public void deleteAll(final List<Section> removeSections) {
        sections.removeAll(removeSections);
    }

    public Section findEndSection(final Station station) {
        return sections.stream()
                .filter(Section::isEndStation)
                .filter(section -> section.contains(station))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    public Section findSection(final Station station,
                               final Function<Section, Station> function) {
        return sections.stream()
                .filter(section -> function.apply(section).equals(station))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    public List<Station> findStations() {
        if (sections.size() == 0) {
            return new ArrayList<>();
        }

        final List<Station> stations = new ArrayList<>();
        stations.add(findStartStation());
        stations.addAll(sections.stream()
                .map(Section::getDownStation)
                .collect(Collectors.toList()));

        return stations;
    }

    public Station findStartStation() {
        return sections.stream()
                .map(Section::getUpStation)
                .filter(Station::isUpStation)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("상행 종점역을 찾을 수 없습니다."));
    }

    public List<Section> getSections() {
        return sections.stream().collect(Collectors.toUnmodifiableList());
    }
}
