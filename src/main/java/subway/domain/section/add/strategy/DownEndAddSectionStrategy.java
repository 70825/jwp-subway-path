package subway.domain.section.add.strategy;

import subway.domain.Station;
import subway.domain.section.Section;
import subway.domain.section.Sections;

import static subway.domain.Direction.DOWN;
import static subway.domain.Direction.MID;

public class DownEndAddSectionStrategy implements AddSectionStrategy {

    @Override
    public void addSection(final Sections sections, final Section insertSection) {
        changeDirection(insertSection);
        sections.addSection(insertSection);
    }

    public void changeDirection(final Section section) {
        final Station upStation = section.getUpStation();
        final Station downStation = section.getDownStation();

        upStation.changeDirection(MID);
        downStation.changeDirection(DOWN);
    }
}
