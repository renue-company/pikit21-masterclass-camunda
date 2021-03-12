package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class NotifyAboutSuccessOrderTask extends SaveActivityInfoToFileTask {

    @Override
    protected String getContent(DelegateExecution execution) {
        return "Информирование клиента об успешном оформлении заказа.";
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Информировать об успешном приеме заказа.txt";
    }
}
