package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * @author Nevolin Alex.
 */
public class AddClientToBlackListTask extends SaveActivityInfoToFileTask {

    @Override
    protected String getContent(DelegateExecution execution) {
        return "Добавление клиента в черный список.";
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Заблокировать клиента.txt";
    }
}
