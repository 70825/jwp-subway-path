package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.domain.Station;
import subway.persistence.repository.StationRepository;

@Transactional
@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(final StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station saveStation(final String name) {
        final Station station = Station.from(name);

        return stationRepository.insert(station);
    }

    public Station findStationById(final Long id) {
        return stationRepository.findById(id);
    }

    public void deleteStationById(final Long id) {
        stationRepository.deleteById(id);
    }
}
