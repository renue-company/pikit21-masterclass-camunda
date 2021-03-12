package ru.renue.pikit.masterclass.camunda.tasks;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @author Nevolin Alex.
 */
@Slf4j
public class EmptyTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Выполнение задачи: {} ", delegateExecution.getCurrentActivityName());
    }
}
