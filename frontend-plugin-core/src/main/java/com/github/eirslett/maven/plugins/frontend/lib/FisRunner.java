package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;
import java.util.List;

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

    public DefaultFisRunner(Platform platform, File workingDirectory, List<String> additionalArguments) {
        super(TASK_NAME, TASK_LOCATION, workingDirectory, platform, additionalArguments);
    }
}
