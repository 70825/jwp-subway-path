package subway.domain;

import subway.domain.section.Section;
import subway.domain.section.Sections;
import subway.domain.section.add.AddSectionHandler;
import subway.domain.section.add.strategy.AddSectionStrategy;
import subway.domain.section.delete.DeleteSectionHandler;
import subway.domain.section.delete.strategy.DeleteSectionStrategy;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Line {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣0-9]*$");
    private static final Pattern COLOR_PATTERN = Pattern.compile("^bg-[a-z]{3,7}-\\d{2,3}$");
    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 9;

    private final Long id;
    private final String name;
    private final String color;
    private final Sections sections;

    private Line(final Long id, final String name, final String color, final Sections sections) {
        validateLineInfo(name, color);

        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = sections;
    }

    public static Line of(final Long id, final String name, final String color) {
        return new Line(id, name, color, Sections.create());
    }

    public static Line of(final String name, final String color) {
        return new Line(null, name, color, Sections.create());
    }

    private void validateLineInfo(final String name, final String color) {
        validateNameFormat(name);
        validateNameLength(name);
        validateColorFormat(color);
    }

    private void validateNameFormat(final String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("노선 이름은 한글과 숫자만 가능합니다");
        }
    }

    private void validateNameLength(final String name) {
        if (!(MINIMUM_LENGTH <= name.length() && name.length() <= MAXIMUM_LENGTH)) {
            throw new IllegalArgumentException("노선 이름은 2글자 ~ 9글자만 가능합니다");
        }
    }

    private void validateColorFormat(final String color) {
        if (!COLOR_PATTERN.matcher(color).matches()) {
            throw new IllegalArgumentException("노선 색깔은 tailwindcss 형식만 가능합니다");
        }
    }

    public void addSection(final Section section) {
        final AddSectionStrategy addSectionStrategy = AddSectionHandler.bind(sections, section);
        addSectionStrategy.addSection(sections, section);
    }

    public void deleteStation(final Station station) {
        final DeleteSectionStrategy deleteSectionStrategy = DeleteSectionHandler.bind(sections, station);
        deleteSectionStrategy.deleteSection(sections, station);
    }

    public List<Station> findStationsByOrdered() {
        return sections.findStations();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Sections getSections() {
        return sections;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Line line = (Line) o;
        return Objects.equals(id, line.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
