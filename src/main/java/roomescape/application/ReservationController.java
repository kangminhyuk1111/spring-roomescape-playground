package roomescape.application;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.application.dto.CreateReservationRequest;
import roomescape.application.dto.DeleteReservationRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.model.Reservation;

@Controller
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(final ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/reservation")
  public String reservation() {
    return "reservation";
  }

  @GetMapping("/reservations")
  @ResponseBody
  public List<ReservationResponse> reservations() {
    return reservationService.findAll();
  }

  @PostMapping("/reservations")
  @ResponseBody
  public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
    Reservation savedReservation = reservationService.save(request);
    ReservationResponse response = ReservationResponse.from(savedReservation);

    return ResponseEntity
        .created(URI.create("/reservations/" + savedReservation.getId()))
        .body(response);
  }

  @DeleteMapping("/reservations/{reservationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReservation(@PathVariable Long reservationId) {
    final DeleteReservationRequest request = new DeleteReservationRequest(reservationId);
    reservationService.deleteById(request);
  }
}
