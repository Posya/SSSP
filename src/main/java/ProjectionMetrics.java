import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * ProjectionMetrics class
 */
class ProjectionMetrics {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Histogram latencyHist;
    private final Slf4jReporter reporter;

    ProjectionMetrics(MetricRegistry metricRegistry) {
        reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(log)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        latencyHist = metricRegistry.histogram(MetricRegistry.name(ProjectionMetrics.class, "latency"));
    }

    void stop() {
        log.info("Stopping reporter");
        reporter.close();
    }

    void latency(Duration duration) {
        latencyHist.update(duration.toMillis());
    }
}
