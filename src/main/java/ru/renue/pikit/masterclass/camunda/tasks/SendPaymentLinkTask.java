package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class SendPaymentLinkTask extends SaveActivityInfoToFileTask {

    @Override
    protected String getContent(DelegateExecution execution) {
        return String.format("Ссылка на оплату %s направлена в клиентский компонент." +
            " В результате клиент должен быть перенаправлен на страницу оплаты.", execution.getVariable("acquiringLink"));
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Направить ссылку на оплату.txt";
    }
}
