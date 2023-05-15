package subway.domain.section.add.strategy;

import subway.domain.Station;
import subway.domain.section.Section;
import subway.domain.section.Sections;

import static subway.domain.Direction.MID;
import static subway.domain.Direction.UP;

public class UpEndAddSectionStrategy implements AddSectionStrategy {

    @Override
    public void addSection(final Sections sections, final Section section) {
        changeDirection(section);
        sections.addSection(0, section);
    }

    private void changeDirection(final Section section) {
        final Station upStation = section.getUpStation();
        final Station downStation = section.getDownStation();

        upStation.changeDirection(UP);
        downStation.changeDirection(MID);
    }
}
