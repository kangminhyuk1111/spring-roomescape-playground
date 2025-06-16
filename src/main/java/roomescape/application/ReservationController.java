package roomescape.application;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.application.dto.ReservationResponse;

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
    return reservationService.findAllReservations();
  }
}
