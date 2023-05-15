package subway.domain.section.delete.strategy;

import subway.domain.Station;
import subway.domain.section.Sections;

@FunctionalInterface
public interface DeleteSectionStrategy {

    void deleteSection(final Sections sections, final Station removeStation);
}
