package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class RequestAcquiringTask extends SaveActivityInfoToFileTask {

    @Override
    protected String getContent(DelegateExecution execution) {
        return "Направлен запрос на эквайринг. В ответе ожидается ссылка, на которую будет перенаправлен клиент для совершения оплаты.";
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Запросить эквайринг.txt";
    }
}
