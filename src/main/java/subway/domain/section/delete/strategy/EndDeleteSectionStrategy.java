package subway.domain.section.delete.strategy;

import subway.domain.Station;
import subway.domain.section.Section;
import subway.domain.section.Sections;

public class EndDeleteSectionStrategy implements DeleteSectionStrategy {

    @Override
    public void deleteSection(final Sections sections, final Station removeStation) {
        final Section endSection = sections.findEndSection(removeStation);
        sections.delete(endSection);
        final Station otherStation = endSection.findOtherStation(removeStation);
        otherStation.changeDirection(removeStation.getDirection());
    }
}
