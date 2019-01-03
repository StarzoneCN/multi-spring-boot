package com.example.demo.multi.springBoot.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2019/1/3 14:35
 * @modefied:
 */
@Mojo(name = "printPc", defaultPhase = LifecyclePhase.COMPILE)
public class GreetingMojo extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("执行Greeting插件。。。");
        getLog().info( getPluginContext().toString() );
        getLog().info("--------------------------");
    }
}
