package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class SaveOrderTask extends SaveActivityInfoToFileTask {

    @Override
    protected String getContent(DelegateExecution execution) {
        return "Заказ сохранен в БД.";
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Зафиксировать заказ.txt";
    }
}
