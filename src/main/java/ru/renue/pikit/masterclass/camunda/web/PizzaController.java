package ru.renue.pikit.masterclass.camunda.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.jni.Local;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.renue.pikit.masterclass.camunda.services.ApplicationContextProvider;

/**
 * @author Nevolin Alex.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PizzaController {

    private final AtomicInteger sequence = new AtomicInteger(0);

    private final RuntimeService runtimeService;


    @PutMapping(value = "/orderMsg")
    public String orderMsg(@RequestParam Boolean isPaymentOnSite) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));

        String businessKey = String.format("%s-%04d", now, sequence.incrementAndGet());

        createBusinessKeyDir(businessKey);

        Map<String, Object> variables = new HashMap<>();
        variables.put("isPaymentOnSite", isPaymentOnSite);
        runtimeService.startProcessInstanceByMessage("orderMsg", businessKey, variables);
        return businessKey;
    }

    @PutMapping(value = "/acquiringSuccessMsg")
    public ResponseEntity<?> acquiringSuccessMsg(@RequestParam String acquiringLink, @RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("acquiringLink", acquiringLink);
        messageEventReceived("acquiringSuccessMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedAcquiringError")
    public ResponseEntity<?> receivedAcquiringError(@RequestParam String errCode, @RequestParam String errMsg, @RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("errCode", errCode);
        variables.put("errMsg", errMsg);
        messageEventReceived("acquiringErrorMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedPaymentInfo")
    public ResponseEntity<?> receivedPaymentInfo(@RequestParam String paymentStatus,
                                                 @RequestParam Float paymentValue,
                                                 @RequestParam String paymentCurrency,
                                                 @RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("paymentStatus", paymentStatus);
        variables.put("paymentValue", paymentValue);
        variables.put("paymentCurrency", paymentCurrency);
        messageEventReceived("paymentMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedOrderToDelivery")
    public ResponseEntity<?> receivedOrderToDelivery(@RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        messageEventReceived("orderToDeliveryMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedOrderDelivered")
    public ResponseEntity<?> receivedOrderDelivered(@RequestParam String businessKey, @RequestParam String orderDeliveredDateTime) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderDeliveredDateTime", orderDeliveredDateTime);
        messageEventReceived("orderDeliveredMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedCustomerPaid")
    public ResponseEntity<?> receivedCustomerPaid(@RequestParam String paymentStatus,
                                                 @RequestParam Float paymentValue,
                                                 @RequestParam String paymentCurrency,
                                                 @RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("paymentStatus", paymentStatus);
        variables.put("paymentValue", paymentValue);
        variables.put("paymentCurrency", paymentCurrency);
        messageEventReceived("customerPaidMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedCustomerNotPay")
    public ResponseEntity<?> receivedCustomerNotPay(@RequestParam String paymentStatus,
                                                  @RequestParam Float paymentValue,
                                                  @RequestParam String paymentCurrency,
                                                  @RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("paymentStatus", paymentStatus);
        variables.put("paymentValue", paymentValue);
        variables.put("paymentCurrency", paymentCurrency);
        messageEventReceived("customerNotPaidMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/receivedProductionStatus")
    public ResponseEntity<?> receivedProductionStatus(@RequestParam String productionStatus,
                                                    @RequestParam String productionStatusDateTime,
                                                    @RequestParam String businessKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("productionStatus", productionStatus);
        variables.put("productionStatusDateTime", productionStatusDateTime);
        messageEventReceived("productionStatusMsg", businessKey, variables);
        return ResponseEntity.ok().build();
    }

    private void messageEventReceived(String msg, String businessKey, Map<String, Object> vars) {
        Execution execution = runtimeService.createExecutionQuery()
            .processInstanceBusinessKey(businessKey)
            .messageEventSubscriptionName(msg)
            .singleResult();

        runtimeService.messageEventReceived(msg,
            execution.getId(),
            vars);

    }

    @SneakyThrows
    private void createBusinessKeyDir(String businessKey) {
        String executionsPath = ApplicationContextProvider.getExecutionsPath();

        {
            Path executionPath = Paths.get(executionsPath);
            if (!Files.exists(executionPath)) {
                Files.createDirectory(executionPath);
            }
        }

        {
            String businessKeyDir = executionsPath + "/" + businessKey;
            Path businessKeyDirPath = Paths.get(businessKeyDir);
            if (!Files.exists(businessKeyDirPath)) {
                Files.createDirectory(businessKeyDirPath);
            }
        }
    }
}
