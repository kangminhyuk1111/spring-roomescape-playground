package roomescape.application.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.dto.CreateTimeRequest;
import roomescape.application.dto.DeleteTimeRequest;
import roomescape.application.dto.TimeResponse;
import roomescape.application.service.TimeService;

@RestController
public class RestTimeController {

  private final TimeService timeService;

  public RestTimeController(final TimeService timeService) {
    this.timeService = timeService;
  }

  @PostMapping("/times")
  public ResponseEntity<TimeResponse> createTime(@RequestBody CreateTimeRequest request) {
    final TimeResponse timeResponse = timeService.createTime(request);

    return ResponseEntity.created(URI.create("/times/" + timeResponse.id())).body(timeResponse);
  }

  @GetMapping("/times")
  public List<TimeResponse> findAll() {
    return timeService.findAll();
  }

  @DeleteMapping("/times/{timeId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReservation(@PathVariable Long timeId) {
    final DeleteTimeRequest request = new DeleteTimeRequest(timeId);
    timeService.deleteById(request);
  }
}
