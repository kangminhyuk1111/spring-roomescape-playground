package roomescape.infra.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.model.Reservation;
import roomescape.domain.repository.ReservationRepository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final List<Reservation> reservations;

  public InMemoryReservationRepository() {
    this.reservations = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public Optional<Reservation> findById(final Long id) {
    return reservations.stream().filter(reservation -> reservation.getId().equals(id)).findFirst();
  }

  @Override
  public List<Reservation> findAll() {
    return new ArrayList<>(reservations);
  }

  @Override
  public Reservation save(final Reservation reservation) {
    if (reservation.getId() == null) {
      Long id = idGenerator.getAndIncrement();
      Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
      reservations.add(newReservation);
      return newReservation;
    }

    reservations.add(reservation);

    return reservation;
  }

  @Override
  public void deleteById(final Long reservationId) {
    reservations.removeIf(reservation -> reservation.getId().equals(reservationId));
  }
}
