import java.time.Duration;
import java.time.Instant;

/**
 * ClientProjection class
 */
class ClientProjection implements EventConsumer {

    private final ProjectionMetrics metrics;

    ClientProjection(ProjectionMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public Event consume(Event event) {
        metrics.latency(Duration.between(event.getCreated(), Instant.now()));
        Sleeper.randSleep(10, 1);
        return event;
    }

}
