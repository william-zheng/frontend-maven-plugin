package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;
import java.util.List;

public final class FrontendPluginFactory {
    private static final Platform defaultPlatform = Platform.guess();
    private final File workingDirectory;
    private final ProxyConfig proxy;
    private final List<String> additionalArguments;

    public FrontendPluginFactory(File workingDirectory){
        this(workingDirectory, null);
    }
    public FrontendPluginFactory(File workingDirectory, ProxyConfig proxy){
        this(workingDirectory, proxy, null);
    }
    public FrontendPluginFactory(File workingDirectory, ProxyConfig proxy, List<String> additionalArguments){
        this.workingDirectory = workingDirectory;
        this.proxy = proxy;
        this.additionalArguments = additionalArguments;
    }


    public NodeAndNPMInstaller getNodeAndNPMInstaller(){
        return new DefaultNodeAndNPMInstaller(
                workingDirectory,
                defaultPlatform,
                new DefaultArchiveExtractor(),
                new DefaultFileDownloader(proxy));
    }

    public NpmRunner getNpmRunner() {
        return new DefaultNpmRunner(defaultPlatform, workingDirectory, proxy);
    }

    public GruntRunner getGruntRunner(){
        return new DefaultGruntRunner(defaultPlatform, workingDirectory);
    }

    public KarmaRunner getKarmaRunner(){
        return new DefaultKarmaRunner(defaultPlatform, workingDirectory);
    }

    public GulpRunner getGulpRunner(){
        return new DefaultGulpRunner(defaultPlatform, workingDirectory);
    }

    public FisRunner getFisRunner() {
        return new DefaultFisRunner(defaultPlatform, workingDirectory, additionalArguments);
    }
}
