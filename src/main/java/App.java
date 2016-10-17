import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.schedulers.Schedulers;

import java.io.IOException;

/**
 * App class
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger("Main class");

    public static void main(String[] args) throws IOException {

        log.info("Start");

        MetricRegistry metricRegistry = new MetricRegistry();
        ProjectionMetrics metrics = new ProjectionMetrics(metricRegistry);

        ClientProjection clientProjection = new ClientProjection(metrics);

        EventStream es = new EventStreamImpl();

        try (NaivePool naivePool = new NaivePool(20, clientProjection, metricRegistry)) {
            es.consume(naivePool);
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
            Schedulers.shutdown();
        }

        metrics.stop();

        log.info("End");

    }
}
