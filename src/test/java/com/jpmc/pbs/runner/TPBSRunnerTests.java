package com.jpmc.pbs.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/test/resources/features"},
    plugin = {"pretty", "html:reports/cucumber-report.html"},
    glue= {"com.jpmc.pbs.definitions"})
public class TPBSRunnerTests {}
