package com.example.demo.multi.springBoot.mojo;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2019/1/3 14:35
 * @modefied:
 */
@Mojo(name = "printPc", defaultPhase = LifecyclePhase.COMPILE)
public class GreetingMojo extends AbstractMojo {

    @Parameter(property = "printTip", defaultValue = "true")
    private boolean printTip;
    @Parameter(property = "printPc.projectVersion", defaultValue = "${project.version}")
    private String projectVersion;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (this.printTip) {
            getLog().info("执行Greeting插件。。。");
        }
        getLog().info( getPluginContext().toString() );
        if (StringUtils.isNotBlank(this.projectVersion)){
            getLog().info("projectVersion = " + projectVersion);
        }
        if (this.printTip) {
            getLog().info("--------------------------");
        }
    }
}
