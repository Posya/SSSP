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
        NaivePool naivePool = new NaivePool(10, clientProjection, metricRegistry);

        EventStream es = new EventStreamImpl();
        es.consume(naivePool);

        //noinspection ResultOfMethodCallIgnored
        System.in.read();

        metrics.stop();
        Schedulers.shutdown();

        log.info("End");

    }
}
