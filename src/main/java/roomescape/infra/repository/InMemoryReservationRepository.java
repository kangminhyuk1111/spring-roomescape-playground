package roomescape.infra.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.model.Reservation;
import roomescape.domain.repository.ReservationRepository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final List<Reservation> reservations;

  public InMemoryReservationRepository(final List<Reservation> reservations) {
    this.reservations = reservations;
    createDefaultReservations(reservations);
  }

  @Override
  public Long nextId() {
    return idGenerator.getAndIncrement();
  }

  @Override
  public List<Reservation> findAll() {
    return reservations;
  }

  private Reservation createReservation(String name, LocalDate date, LocalTime time) {
    return new Reservation(nextId(), name, date, time);
  }

  private void createDefaultReservations(final List<Reservation> reservations) {
    reservations.add(createReservation("kang", LocalDate.now(), LocalTime.now().withNano(0)));
    reservations.add(createReservation("min", LocalDate.now(), LocalTime.now().withNano(0)));
    reservations.add(createReservation("hyuk", LocalDate.now(), LocalTime.now().withNano(0)));
  }
}
