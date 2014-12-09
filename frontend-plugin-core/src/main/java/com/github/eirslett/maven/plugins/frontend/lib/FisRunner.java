package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.github.eirslett.maven.plugins.frontend.lib.Utils.normalize;
import static com.github.eirslett.maven.plugins.frontend.lib.Utils.prepend;

/**
 * 百度FIS
 * Created by Zhengweiyi on 2014/12/8.
 */
public interface FisRunner {
    public void execute(String args) throws TaskRunnerException;
}

final class DefaultFisRunner extends NodeTaskExecutor implements FisRunner {
    private static final String TASK_NAME = "fis";
    private static final String TASK_LOCATION = "/node_modules/fis/bin/fis";

    private File fisBase;

    public DefaultFisRunner(Platform platform, File workingDirectory, File fisBase) {
        super(TASK_NAME, TASK_LOCATION, workingDirectory, platform, Arrays.asList("--no-color"));
        this.fisBase = fisBase;
    }

    public void execute(String args) throws TaskRunnerException {
        final String absoluteTaskLocation = workingDirectory + normalize(taskLocation);
        final List<String> arguments = getArguments(args);
        logger.info("Running " + taskToString(taskName, arguments) + " in " + fisBase);

        try {
            final int result = new NodeExecutor(workingDirectory, prepend(absoluteTaskLocation, arguments), platform, fisBase).executeAndRedirectOutput(logger);
            if(result != 0){
                throw new TaskRunnerException(taskToString(taskName, arguments) + " failed. (error code "+result+")");
            }
        } catch (ProcessExecutionException e) {
            throw new TaskRunnerException(taskToString(taskName, arguments) + " failed.", e);
        }
    }
}
