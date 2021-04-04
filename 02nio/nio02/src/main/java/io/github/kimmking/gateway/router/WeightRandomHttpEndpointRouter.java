package io.github.kimmking.gateway.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightRandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> urls) {
        if (urls.size() != 3) {
            throw new RuntimeException();
        }
        int[] weights = new int[]{2, 3, 5};
        List<String> _urls = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            for (int j = 0; j < weights[i]; j++) {
                _urls.add(urls.get(i));
            }
        }
        int size = _urls.size();
        Random random = new Random(System.currentTimeMillis());
        return _urls.get(random.nextInt(size));
    }
}
