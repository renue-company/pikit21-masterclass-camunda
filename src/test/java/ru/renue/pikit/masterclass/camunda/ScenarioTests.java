package ru.renue.pikit.masterclass.camunda;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * @author Nevolin Alex.
 */
@Slf4j
public class ScenarioTests {

    @Test
    public void successScenario() {
        String bk = sendRequest("http://localhost:8080/api/orderMsg?isPaymentOnSite=true");
        log.info("Business Key: {} ", bk);
        String link = "https://acquiring.example/" + UUID.randomUUID().toString().substring(24, 36);

        sendRequest(String.format("http://localhost:8080/api/acquiringSuccessMsg?" +
            "acquiringLink=%s&businessKey=%s", link, bk));

        sendRequest(String.format("http://localhost:8080/api/receivedPaymentInfo?" +
            "paymentStatus=SUCCESS&businessKey=%s&paymentValue=420.4&paymentCurrency=RUB", bk));

        sendRequest(String.format("http://localhost:8080/api/receivedOrderToDelivery?businessKey=%s", bk));


        sendRequest(String.format("http://localhost:8080/api/receivedOrderDelivered?businessKey=%s&orderDeliveredDateTime=12.03.2021T12:30:00Z", bk));
    }

    @Test
    public void failScenario1() {
        String bk = sendRequest("http://localhost:8080/api/orderMsg?isPaymentOnSite=true");
        log.info("Business Key: {} ", bk);

        sendRequest("http://localhost:8080/api/receivedAcquiringError?errCode=ACQ-001&businessKey=" + bk +
            "&errMsg=%D0%9E%D0%B1%D1%89%D0%B0%D1%8F%20%D0%BE%D1%88%D0%B8%D0%B1%D0%BA%D0%B0%20%D0%BE%D0%B1%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D0%B8%20%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81%D0%B0.%20%D0%A1%D0%B5%D1%80%D0%B2%D0%B8%D1%81%20%D0%BE%D0%BF%D0%BB%D0%B0%D1%82%D1%8B%20%D0%BD%D0%B5%D0%B4%D0%BE%D1%81%D1%82%D1%83%D0%BF%D0%B5%D0%BD.");
    }

    @Test
    public void failScenario2() {
        String bk = sendRequest("http://localhost:8080/api/orderMsg?isPaymentOnSite=true");
        log.info("Business Key: {} ", bk);
        String link = "https://acquiring.example/" + UUID.randomUUID().toString().substring(24, 36);

        sendRequest(String.format("http://localhost:8080/api/acquiringSuccessMsg?" +
            "acquiringLink=%s&businessKey=%s", link, bk));

        sendRequest(String.format("http://localhost:8080/api/receivedPaymentInfo?" +
            "paymentStatus=CANCELED&businessKey=%s&paymentValue=560.40&paymentCurrency=RUB", bk));
    }

    @SneakyThrows
    private String sendRequest(String uri) {
        log.info("Sending HTTP PUT to : {}", uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.noBody())
            .uri(URI.create(uri))
            .build();

        HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode());
        return response.body();
    }
}
