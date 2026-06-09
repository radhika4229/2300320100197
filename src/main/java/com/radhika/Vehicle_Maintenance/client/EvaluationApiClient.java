package com.radhika.Vehicle_Maintenance.client;

import com.radhika.Vehicle_Maintenance.dto.DepotDto;
import com.radhika.Vehicle_Maintenance.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationApiClient {

    private final WebClient evaluationWebClient;

    @Value("${evaluation.depots-path}")
    private String depotsPath;

    public List<DepotDto> fetchDepots() {
        log.info("Fetching depots from external API: {}", depotsPath);
        try {
            List<DepotDto> depots = evaluationWebClient.get()
                    .uri(depotsPath)
                    .retrieve()
                    .bodyToFlux(DepotDto.class)
                    .collectList()
                    .block();

            log.info("Successfully fetched {} depots", depots != null ? depots.size() : 0);
            return depots != null ? depots : List.of();

        } catch (WebClientResponseException ex) {
            log.error("External API returned error: status={}, body={}",
                    ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new ExternalServiceException(
                    "Depot API returned error: " + ex.getStatusCode());

        } catch (Exception ex) {
            log.error("Failed to reach external depot API", ex);
            throw new ExternalServiceException(
                    "Unable to reach depot API: " + ex.getMessage());
        }
    }
}