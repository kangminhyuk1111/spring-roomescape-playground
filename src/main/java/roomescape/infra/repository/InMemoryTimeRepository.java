package roomescape.infra.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.model.Time;
import roomescape.domain.repository.TimeRepository;

@Repository
public class InMemoryTimeRepository implements TimeRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final List<Time> times;

  public InMemoryTimeRepository(final List<Time> times) {
    this.times = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public Time save(final Time time) {
    if (time.getId() == null) {
      Long id = idGenerator.getAndIncrement();
      Time newTime = new Time(id, time.getTime());
      times.add(newTime);
      return newTime;
    }

    times.add(time);

    return time;
  }

  @Override
  public List<Time> findAll() {
    return new ArrayList<>(times);
  }

  @Override
  public void deleteById(final Long id) {
    times.removeIf(time -> time.getId().equals(id));
  }

  @Override
  public Optional<Time> findById(final Long id) {
    return times.stream().filter(time -> time.getId().equals(id)).findFirst();
  }
}
