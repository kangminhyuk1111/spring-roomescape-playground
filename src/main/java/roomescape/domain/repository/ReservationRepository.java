package roomescape.domain.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.model.Reservation;

public interface ReservationRepository {
  Optional<Reservation> findById(Long id);

  List<Reservation> findAll();

  Reservation save(Reservation reservation);

  void deleteById(Long id);
}
