package roomescape.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import roomescape.domain.model.Reservation;
import roomescape.domain.model.Time;
import roomescape.domain.repository.ReservationRepository;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcReservationRepository(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Reservation> findById(final Long id) {
    String sql = """
        SELECT r.id, r.name, r.date, r.time_id, t.time as time_value 
        FROM reservation r 
        JOIN time t ON r.time_id = t.id 
        WHERE r.id = ?
        """;

    try {
      Reservation reservation = jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), id);
      return Optional.ofNullable(reservation);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Reservation> findAll() {
    String sql = """
        SELECT r.id, r.name, r.date, r.time_id, t.time as time_value
        FROM reservation r 
        JOIN time t ON r.time_id = t.id 
        ORDER BY r.id
        """;

    return jdbcTemplate.query(sql, new ReservationRowMapper());
  }

  @Override
  public Reservation save(final Reservation reservation) {
    String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate().toString()); // LocalDate -> String
      ps.setLong(3, reservation.getTime().getId()); // Time 객체에서 ID 추출
      return ps;
    }, keyHolder);

    Long generatedId = keyHolder.getKey().longValue();

    return new Reservation(generatedId, reservation.getName(), reservation.getDate(), reservation.getTime());
  }

  @Override
  public void deleteById(final Long id) {
    String sql = "DELETE FROM reservation WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }

  private static class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
      Time time = new Time(
          rs.getLong("time_id"),
          rs.getString("time_value")
      );

      return new Reservation(
          rs.getLong("id"),
          rs.getString("name"),
          rs.getDate("date").toLocalDate(),
          time
      );
    }
  }
}
