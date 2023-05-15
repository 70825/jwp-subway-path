package subway.domain.section.delete.strategy;

import subway.domain.Distance;
import subway.domain.Station;
import subway.domain.section.Section;
import subway.domain.section.Sections;

import java.util.List;

public class MidDeleteSectionStrategy implements DeleteSectionStrategy {

    @Override
    public void deleteSection(final Sections sections, final Station removeStation) {
        final Section upSection = sections.findSection(removeStation, Section::getUpStation);
        final Section downSection = sections.findSection(removeStation, Section::getDownStation);
        sections.deleteAll(List.of(upSection, downSection));

        final Station upStation = upSection.getUpStation();
        final Station downStation = downSection.getDownStation();
        final Distance distance = upSection.getDistance().add(downSection.getDistance());
        sections.addSection(Section.of(upStation, downStation, distance));
    }
}
