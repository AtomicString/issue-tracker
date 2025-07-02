package me.atomicstring.tracker.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public class MetricsService {
	private final MeterRegistry registry;

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    public void countIssueCreated() {
        registry.counter("issues.created").increment();
    }

    public void countCommentPosted() {
        registry.counter("comments.created").increment();
    }

    public Timer.Sample startRequestTimer() {
        return Timer.start(registry);
    }

    public void stopRequestTimer(Timer.Sample sample, String route) {
        sample.stop(Timer.builder("http.requests.latency")
            .tag("route", route)
            .register(registry));
    }
}
