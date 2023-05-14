package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.domain.Line;
import subway.persistence.repository.LineRepository;
import subway.persistence.repository.SectionRepository;
import subway.ui.dto.response.ReadLineResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LineQueryService {

    private final LineRepository lineRepository;
    private final SectionRepository sectionRepository;

    public LineQueryService(final LineRepository lineRepository, final SectionRepository sectionRepository) {
        this.lineRepository = lineRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<ReadLineResponse> findAllLine() {
        final List<Line> persistLines = lineRepository.findAll();

        for (Line persistLine : persistLines) {
            sectionRepository.findAllByLine(persistLine);
        }

        return persistLines.stream()
                .map(ReadLineResponse::of)
                .collect(Collectors.toList());
    }

    public ReadLineResponse findLineById(final Long id) {
        final Line line = lineRepository.findById(id);
        sectionRepository.findAllByLine(line);

        return ReadLineResponse.of(line);
    }
}
