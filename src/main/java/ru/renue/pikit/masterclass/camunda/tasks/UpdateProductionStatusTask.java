package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class UpdateProductionStatusTask extends SaveActivityInfoToFileTask{

    @Override
    protected String getContent(DelegateExecution execution) {
        return "Обновление производственного статуса заказа на " + execution.getVariable("productionStatus");
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Обновить статус заказа.txt";
    }
}
