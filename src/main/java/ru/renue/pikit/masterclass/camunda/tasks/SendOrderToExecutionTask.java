package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class SendOrderToExecutionTask extends SaveActivityInfoToFileTask {

    @Override
    protected String getContent(DelegateExecution execution) {
        return "Заказ передан в производство.";
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Передать заказ в работу.txt";
    }
}
