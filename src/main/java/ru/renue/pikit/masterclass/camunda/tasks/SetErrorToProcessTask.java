package ru.renue.pikit.masterclass.camunda.tasks;

import lombok.Setter;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;

/**
 * @author Nevolin Alex.
 */
@Setter
public class SetErrorToProcessTask extends EmptyTask {

    private Expression error;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        super.execute(execution);
        String error = (String) this.error.getValue(execution);
        execution.setVariable("error", error);
    }
}
