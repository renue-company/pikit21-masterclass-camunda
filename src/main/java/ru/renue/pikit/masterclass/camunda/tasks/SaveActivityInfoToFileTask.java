package ru.renue.pikit.masterclass.camunda.tasks;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.renue.pikit.masterclass.camunda.services.ApplicationContextProvider;

/**
 * @author Nevolin Alex.
 */
@Slf4j
public abstract class SaveActivityInfoToFileTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Выполнение задачи: {} ", execution.getCurrentActivityName());
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss.SSS"));
        String filepath = getPath(execution) + "/" + now + " - " + getSuffix(execution);
        Path file = Paths.get(filepath);
        Files.write(file, getContent(execution).getBytes(StandardCharsets.UTF_8));
    }

    private String getPath(DelegateExecution execution) {
        return ApplicationContextProvider.getExecutionsPath() + "/" + execution.getBusinessKey();
    }

    protected abstract String getContent(DelegateExecution execution);

    protected abstract String getSuffix(DelegateExecution execution);
}
