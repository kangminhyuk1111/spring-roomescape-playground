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
import roomescape.domain.model.Reservation;
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
    String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";

    try {
      Reservation reservation = jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), id);
      return Optional.ofNullable(reservation);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Reservation> findAll() {
    String sql = "SELECT id, name, date, time FROM reservation ORDER BY id";

    return jdbcTemplate.query(sql, new ReservationRowMapper());
  }

  @Override
  public Reservation save(final Reservation reservation) {
    String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, new String[]{"id"});
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate().toString());
      ps.setString(3, reservation.getTime().toString());
      return ps;
    }, keyHolder);

    final Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
    return new Reservation(generatedId, reservation.getName(), reservation.getDate(),
        reservation.getTime());
  }

  @Override
  public void deleteById(final Long id) {
    String sql = "DELETE FROM reservation WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }

  private static class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Reservation(
          rs.getLong("id"),
          rs.getString("name"),
          rs.getDate("date").toLocalDate(),
          rs.getTime("time").toLocalTime()
      );
    }
  }
}
