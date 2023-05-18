package subway.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.domain.Distance;
import subway.domain.Line;
import subway.domain.SectionWeightEdge;
import subway.domain.Station;
import subway.domain.section.Section;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathServiceImpl implements PathService {

    @Override
    public List<Section> getSectionsByShortestPath(final Station sourceStation,
                                                   final Station targetStation,
                                                   final List<Line> lines) {
        return getShortestPath(sourceStation, targetStation, lines).getEdgeList().stream()
                .map(edge -> Section.of(edge.getSource(), edge.getTarget(), Distance.from(edge.getDistance())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Station> getStationsByShortestPath(final Station sourceStation,
                                                   final Station targetStation,
                                                   final List<Line> lines) {
        return getShortestPath(sourceStation, targetStation, lines).getVertexList();
    }

    @Override
    public Distance getDistanceByShortestPath(final Station sourceStation,
                                              final Station targetStation,
                                              final List<Line> lines) {

        final double shortestLength = getShortestPath(sourceStation, targetStation, lines).getWeight();

        return Distance.from(shortestLength);
    }

    private GraphPath<Station, SectionWeightEdge> getShortestPath(
            final Station startStation,
            final Station endStation,
            final List<Line> lines) {
        final WeightedMultigraph<Station, SectionWeightEdge> graph = new WeightedMultigraph<>(SectionWeightEdge.class);

        for (Line line : lines) {
            updateAllStation(graph, line);
            updateAllSection(graph, line);
        }

        return new DijkstraShortestPath<>(graph).getPath(startStation, endStation);
    }

    private void updateAllStation(final WeightedMultigraph<Station, SectionWeightEdge> graph,
                                  final Line line) {
        final List<Station> stations = line.findStationsByOrdered();

        for (Station station : stations) {
            graph.addVertex(station);
        }
    }

    private void updateAllSection(final WeightedMultigraph<Station, SectionWeightEdge> graph,
                                  final Line line) {
        final List<Section> sections = line.getSections().getSections();

        for (Section section : sections) {
            graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance().getDistance());
        }
    }
}
