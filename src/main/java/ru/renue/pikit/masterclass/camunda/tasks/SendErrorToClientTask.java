package ru.renue.pikit.masterclass.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import ru.renue.pikit.masterclass.camunda.utils.Consts;

/**
 * @author Nevolin Alex.
 */
public class SendErrorToClientTask extends SaveActivityInfoToFileTask {
    @Override
    protected String getContent(DelegateExecution execution) {
        return String.format("%s - %s", execution.getVariable("error"), Consts.ERRORS.get(execution.getVariable("error")) );
    }

    @Override
    protected String getSuffix(DelegateExecution execution) {
        return "Направить ошибку клиенту.txt";
    }
}
