package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.lib.FrontendPluginFactory;
import com.github.eirslett.maven.plugins.frontend.lib.ProxyConfig;
import com.github.eirslett.maven.plugins.frontend.lib.TaskRunnerException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.sonatype.plexus.build.incremental.BuildContext;

import java.io.File;
import java.util.Arrays;

import static com.github.eirslett.maven.plugins.frontend.mojo.MojoUtils.setSLF4jLogger;

/**
 * 百度FIS
 * Created by Zhengweiyi on 2014/12/8.
 */
@Mojo(name="fis",  defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class FisMojo extends AbstractMojo {

    /**
     * The base directory for running all Node commands. (Usually the directory that contains package.json)
     */
    @Parameter(defaultValue = "${basedir}", property = "workingDirectory", required = false)
    private File workingDirectory;

    /**
     * fis arguments. Default is "release".
     */
    @Parameter(defaultValue = "release", property = "arguments", required = true)
    private String arguments;

    /**
     * The directory containing front end files that will be processed by grunt.
     * If this is set then files in the directory will be checked for
     * modifications before running grunt.
     */
    @Parameter(property = "srcdir")
    private File srcdir;

    /**
     * The directory where front end files will be output by grunt. If this is
     * set then they will be refreshed so they correctly show as modified in
     * Eclipse.
     */
    @Parameter(property = "outputdir")
    private File outputdir;

    @Component
    private BuildContext buildContext;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (shouldExecute()) {
            try {
                MojoUtils.setSLF4jLogger(getLog());
                new FrontendPluginFactory(workingDirectory, null, Arrays.asList(arguments.split(" "))).getFisRunner().execute(arguments);
            } catch (TaskRunnerException e) {
                throw new MojoFailureException("Failed to run task", e);
            }

            if (outputdir != null) {
                getLog().info("Refreshing files after grunt: " + outputdir);
                buildContext.refresh(outputdir);
            }
        } else {
            getLog().info("Skipping grunt as no modified files in " + srcdir);
        }
    }

    private boolean shouldExecute() {
        return true;
    }
}
