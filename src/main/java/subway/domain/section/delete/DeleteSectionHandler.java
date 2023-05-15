package subway.domain.section.delete;

import subway.domain.Station;
import subway.domain.section.Sections;
import subway.domain.section.delete.strategy.DeleteSectionStrategy;
import subway.domain.section.delete.strategy.EndDeleteSectionStrategy;
import subway.domain.section.delete.strategy.MidDeleteSectionStrategy;

import java.util.Arrays;
import java.util.function.Predicate;

public enum DeleteSectionHandler {

    END(Station::isMidStation, new EndDeleteSectionStrategy()),
    MID(Station::isEndStation, new MidDeleteSectionStrategy());

    private final Predicate<Station> isStrategy;
    private final DeleteSectionStrategy deleteSectionStrategy;

    DeleteSectionHandler(final Predicate<Station> isStrategy, final DeleteSectionStrategy deleteSectionStrategy) {
        this.isStrategy = isStrategy;
        this.deleteSectionStrategy = deleteSectionStrategy;
    }

    public static DeleteSectionStrategy bind(final Sections sections, final Station removeStation) {
        return Arrays.stream(DeleteSectionHandler.values())
                .filter(deleteSectionHandler -> deleteSectionHandler.isStrategy.test(removeStation))
                .map(deleteSectionHandler -> deleteSectionHandler.deleteSectionStrategy)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("역을 제거할 수 없습니다."));
    }
}
