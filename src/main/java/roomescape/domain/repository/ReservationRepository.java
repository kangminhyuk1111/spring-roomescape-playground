package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.model.Reservation;

public interface ReservationRepository {

  Long nextId();

  Reservation findById(Long id);

  List<Reservation> findAll();

  Reservation save(Reservation reservation);
}
