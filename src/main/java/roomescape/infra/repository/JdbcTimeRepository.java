package roomescape.infra.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.model.Time;
import roomescape.domain.repository.TimeRepository;

@Repository
@Primary
public class JdbcTimeRepository implements TimeRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTimeRepository(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Time save(final Time time) {
    String sql = "INSERT INTO time (time) VALUES (?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, new String[]{"id"});
      ps.setString(1, time.getTime());
      return ps;
    }, keyHolder);

    final Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
    return new Time(generatedId, time.getTime());
  }

  @Override
  public List<Time> findAll() {
    String sql = "SELECT id, time FROM TIME";

    return jdbcTemplate.query(sql, new TimeRowMapper());
  }

  @Override
  public void deleteById(final Long id) {
    String sql = "DELETE FROM TIME WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }

  @Override
  public Optional<Time> findById(final Long id) {
    String sql = "SELECT id, time FROM TIME WHERE id = ?";

    try {
      Time time = jdbcTemplate.queryForObject(sql, new TimeRowMapper(), id);
      return Optional.ofNullable(time);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  private static class TimeRowMapper implements RowMapper<Time> {

    @Override
    public Time mapRow(final ResultSet rs, final int rowNum) throws SQLException {
      return new Time(
          rs.getLong("id"),
          rs.getString("time")
      );
    }
  }
}
