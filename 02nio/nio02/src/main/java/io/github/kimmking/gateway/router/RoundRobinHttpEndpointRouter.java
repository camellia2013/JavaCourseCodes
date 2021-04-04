package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinHttpEndpointRouter implements HttpEndpointRouter {
    private final static AtomicInteger next = new AtomicInteger(0);

    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        return urls.get(next.getAndIncrement()%size);
    }
}
