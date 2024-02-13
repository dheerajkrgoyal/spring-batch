package com.dheeraj.springbatchpractice.partition.listener;

import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

public class OutputFileListener implements StepExecutionListener {
    private String outputKeyName = "outputFile";

    private String inputKeyName = "fileName";

    private String path = "target/output/";

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        String inputName = stepExecution.getStepName().replace(":", "-");
        if (executionContext.containsKey(inputKeyName)) {
            inputName = executionContext.getString(inputKeyName);
        }
        if (!executionContext.containsKey(outputKeyName)) {
            executionContext.putString(outputKeyName, path + FilenameUtils.getBaseName(inputName) + ".csv");
        }
    }
}
