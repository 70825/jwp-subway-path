package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.domain.Distance;
import subway.domain.Line;
import subway.domain.Station;
import subway.domain.section.Section;
import subway.persistence.repository.LineRepository;
import subway.persistence.repository.SectionRepository;
import subway.persistence.repository.StationRepository;

@Service
@Transactional
public class LineCommandService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final SectionRepository sectionRepository;

    public LineCommandService(final LineRepository lineRepository,
                              final StationRepository stationRepository,
                              final SectionRepository sectionRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.sectionRepository = sectionRepository;
    }

    public Line saveLine(final String name, final String color) {
        final Line line = Line.of(name, color);

        return lineRepository.insert(line);
    }

    public Line findLineById(final Long id) {
        final Line line = lineRepository.findById(id);
        return sectionRepository.findAllSectionByLine(line);
    }

    public void deleteLineById(final Long id) {
        lineRepository.deleteById(id);
    }

    public void saveSection(final Long lineId,
                            final Long upStationId,
                            final Long downStationId,
                            final int distance) {
        final Line line = lineRepository.findById(lineId);
        final Station upStation = stationRepository.findById(upStationId);
        final Station downStation = stationRepository.findById(downStationId);
        final Section section = Section.of(upStation, downStation, Distance.from(distance));

        final Line newLine = sectionRepository.findAllSectionByLine(line);
        newLine.addSection(section);
        sectionRepository.insert(newLine);
    }

    public void deleteSection(final Long lineId, final Long stationId) {
        final Line line = sectionRepository.findAllSectionByLine(lineRepository.findById(lineId));
        final Station station = stationRepository.findById(stationId);
        line.deleteStation(station);
        sectionRepository.insert(line);
    }
}
