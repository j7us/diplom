package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.graphhopper.GHResponse;
import com.graphhopper.ResponsePath;
import com.graphhopper.api.GraphHopperWeb;
import com.graphhopper.util.PointList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.property.GrapphonerProp;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.GeometryTransformer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoordinatesSender {
    private final GrapphonerProp grapphonerProp;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final VehicleLocaltionService vehicleLocaltionService;

    public void sendCoordinates(UUID vehicleId) {
        CompletableFuture.supplyAsync(this::generateCoordinates)
                .thenApply(points -> restClient.get()
                        .uri(grapphonerProp.getUrl(), points)
                        .retrieve()
                        .body(String.class))
                .thenAccept(resp -> sendCoordinates(vehicleId, resp));
    }

    private void sendCoordinates(UUID vehicleId, String responseGeo) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseGeo);

            ArrayNode paths = (ArrayNode) jsonNode.path("paths");
            JsonNode path = paths.get(0);

            LineString lineString = objectMapper.treeToValue(path.path("points"), LineString.class);

            log.info("Количество точек: {}",lineString.getNumPoints());
            for (int i = 0; i < lineString.getNumPoints(); i++) {
                vehicleLocaltionService.addLocation(vehicleId, lineString.getPointN(i));
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Map<String, String> generateCoordinates() {
        return Map.of(
                "point1",
                generateCoordinate(grapphonerProp.getMinLatitude(), grapphonerProp.getMaxLatitude()) + "," +
                        generateCoordinate(grapphonerProp.getMinLongitude(), grapphonerProp.getMaxLongitude()),
                "point2",
                generateCoordinate(grapphonerProp.getMinLatitude(), grapphonerProp.getMaxLatitude()) + "," +
                        generateCoordinate(grapphonerProp.getMinLongitude(), grapphonerProp.getMaxLongitude()),
                "apiKey", grapphonerProp.getKey()
                );
    }

    private String generateCoordinate(BigDecimal min, BigDecimal max) {
        double rnd = ThreadLocalRandom.current().nextDouble();  // [0,1)
        BigDecimal value = min.add(max.subtract(min).multiply(BigDecimal.valueOf(rnd)));
        value = value.setScale(6, RoundingMode.DOWN);
        return value.toString();
    }
}
