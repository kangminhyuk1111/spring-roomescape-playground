package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.CreateReservationRequest;
import roomescape.application.dto.DeleteReservationRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;
import roomescape.domain.model.Reservation;
import roomescape.domain.model.Time;
import roomescape.domain.repository.ReservationRepository;
import roomescape.domain.repository.TimeRepository;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final TimeRepository timeRepository;

  public ReservationService(final ReservationRepository reservationRepository,
      final TimeRepository timeRepository) {
    this.reservationRepository = reservationRepository;
    this.timeRepository = timeRepository;
  }

  public List<ReservationResponse> findAll() {
    return reservationRepository.findAll().stream().map(ReservationResponse::from).toList();
  }

  public Reservation save(final CreateReservationRequest createReservationRequest) {
    final Time time = timeRepository.findById(createReservationRequest.time())
        .orElseThrow(() -> new ApplicationException(CustomErrorCode.TIME_NOT_FOUND));

    final Reservation reservation = createReservationRequest.to(time);

    return reservationRepository.save(reservation);
  }

  public void deleteById(final DeleteReservationRequest request) {
    final Reservation reservation = reservationRepository.findById(request.reservationId())
        .orElseThrow(() -> new ApplicationException(CustomErrorCode.RESERVATION_ID_NOT_FOUND));

    reservationRepository.deleteById(reservation.getId());
  }
}
