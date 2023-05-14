package subway.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.application.LineCommandService;
import subway.application.LineQueryService;
import subway.application.dto.CreationLineDto;
import subway.ui.dto.request.CreationLineRequest;
import subway.ui.dto.response.CreationLineResponse;
import subway.ui.dto.response.ReadLineResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lines")
public class LineController {

    private final LineQueryService lineQueryService;
    private final LineCommandService lineCommandService;

    public LineController(final LineQueryService lineQueryService, final LineCommandService lineCommandService) {
        this.lineQueryService = lineQueryService;
        this.lineCommandService = lineCommandService;
    }

    @PostMapping
    public ResponseEntity<CreationLineResponse> createLine(@RequestBody final CreationLineRequest request) {
        final CreationLineDto lineDto = lineCommandService.saveLine(request);
        return ResponseEntity.created(URI.create("/lines/" + lineDto.getId())).body(CreationLineResponse.from(lineDto));
    }

    @GetMapping
    public ResponseEntity<List<ReadLineResponse>> findAllLines() {
        return ResponseEntity.ok(lineQueryService.findAllLine());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadLineResponse> findLineById(@PathVariable final Long id) {
        return ResponseEntity.ok(lineQueryService.findLineById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable final Long id) {
        lineCommandService.deleteLineById(id);
        return ResponseEntity.noContent().build();
    }
}
