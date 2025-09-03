package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.dto.PointToAddressDto;
import org.example.property.AddressProp;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final AddressProp addressProp;

    public List<String> getAllAdress(List<PointToAddressDto> address) {
        try {
            String batchAddressResponse = restClient.post()
                .uri(addressProp.getUrl(), addressProp.getApiKey())
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(address))
                .retrieve()
                .body(String.class);
    
        String barchResultUrl = objectMapper.readTree(batchAddressResponse).get("url").asText();

        Thread.sleep(5000);
        String result = restClient.get()
                .uri(barchResultUrl)
                .retrieve()
                .body(String.class);

            JsonNode jsonNode = objectMapper.readTree(result);

            ArrayNode tree = (ArrayNode) jsonNode;

            List<String> adressList = new ArrayList<>();
         for(JsonNode node : tree) {
            adressList.add(node.get("formatted").asText());
         }

         return adressList;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
