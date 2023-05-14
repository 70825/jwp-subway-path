package subway.persistence.entity;

import subway.domain.Distance;

public class SectionEntity {

    private final Long id;
    private final Long lineId;
    private final Long upStationId;
    private final Long downStationId;
    private final int distance;

    public SectionEntity(final Long id, final Long lineId, final Long upStationId, final Long downStationId, final int distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public static SectionEntity of(final Long lineId, final Long upStationId, final Long downStationId, final int distance) {
        return new SectionEntity(null, lineId, upStationId, downStationId, distance);
    }

    public static SectionEntity of(final Long id, final Long lineId, final Long upStationId, final Long downStationId, final int distance) {
        return new SectionEntity(id, lineId, upStationId, downStationId, distance);
    }

    public boolean matchesDownStationId(Long stationId) {
        return downStationId.equals(stationId);
    }

    public boolean matchesUpStationId(Long stationId) {
        return upStationId.equals(stationId);
    }

    public boolean matchesByUpAndDownStationId(Long upStationId, Long downStationId) {
        return upStationId.equals(upStationId) && downStationId.equals(downStationId);
    }

    public Long getId() {
        return id;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }
}
