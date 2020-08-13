package gamestore.interceptor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatsService {
    private AtomicInteger requestCount = new AtomicInteger(0);
    private LocalDateTime startedOn = LocalDateTime.now();

    public void increaseRequestCount() {
        requestCount.incrementAndGet();
    }

    public int getRequestCount() {
        return requestCount.get();
    }

    public LocalDateTime getStartedOn() {
        return startedOn;
    }
}
