package subway.persistence.dao;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import subway.persistence.entity.SectionEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class SectionDaoTest extends DaoTest {

    private static final SectionEntity sectionEntity = SectionEntity.of(1L, 1L, 2L, 5);

    @Test
    void 역과_역의_관계를_저장한다() {

        final SectionEntity actual = sectionDao.insert(sectionEntity);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getId()).isPositive();
            softAssertions.assertThat(actual.getLineId()).isEqualTo(1L);
            softAssertions.assertThat(actual.getUpStationId()).isEqualTo(1L);
            softAssertions.assertThat(actual.getDownStationId()).isEqualTo(2L);
            softAssertions.assertThat(actual.getDistance()).isEqualTo(5);
        });
    }

    @Test
    void 모든_역과_역의_관계를_조회한다() {
        sectionDao.insert(sectionEntity);

        final List<SectionEntity> actual = sectionDao.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual).hasSize(1);
            softAssertions.assertThat(actual.get(0).getId()).isPositive();
            softAssertions.assertThat(actual.get(0).getLineId()).isEqualTo(1L);
            softAssertions.assertThat(actual.get(0).getUpStationId()).isEqualTo(1L);
            softAssertions.assertThat(actual.get(0).getDownStationId()).isEqualTo(2L);
            softAssertions.assertThat(actual.get(0).getDistance()).isEqualTo(5);
        });
    }

    @Test
    void 역과_역의_관계_하나를_조회한다() {
        final SectionEntity insertedSectionEntity = sectionDao.insert(sectionEntity);

        final SectionEntity actual = sectionDao.findById(insertedSectionEntity.getId());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getId()).isPositive();
            softAssertions.assertThat(actual.getLineId()).isEqualTo(1L);
            softAssertions.assertThat(actual.getUpStationId()).isEqualTo(1L);
            softAssertions.assertThat(actual.getDownStationId()).isEqualTo(2L);
            softAssertions.assertThat(actual.getDistance()).isEqualTo(5);
        });
    }

    @Test
    void 역과_역의_관계_하나를_삭제한다() {
        final SectionEntity insertedSectionEntity = sectionDao.insert(sectionEntity);

        final int actual = sectionDao.deleteById(insertedSectionEntity.getId());

        assertThat(actual).isOne();
    }
}
