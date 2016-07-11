package io.techery.github.api.facility;

import io.techery.github.api.tests.BaseTest;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;

import ru.yandex.qatools.allure.annotations.Attachment;

public class LoggingTestListener extends TestListenerAdapter {

    private ResultsLogBuilder logsBuilder;

    public LoggingTestListener() {
        this.logsBuilder = new ResultsLogBuilder(LogsBuffer.instance());
    }

    @Override
    public void beforeConfiguration(ITestResult tr) {
        super.beforeConfiguration(tr);
        if (tr.getMethod().isBeforeSuiteConfiguration()) {
            LogsBuffer.instance().prepareForTestSuite();
            return;
        }
        //
        BaseTest baseTest = (BaseTest) tr.getInstance();
        if (tr.getMethod().isBeforeClassConfiguration()) {
            LogsBuffer.instance().prepareForTestClass(baseTest);
        } else if (tr.getMethod().isAfterClassConfiguration()) {
            LogsBuffer.instance().prepareForTestClassTearDown(baseTest);
        }
    }

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        BaseTest baseTest = (BaseTest) tr.getInstance();
        Method method = tr.getMethod().getConstructorOrMethod().getMethod();
        LogsBuffer.instance().prepareForTestMethod(baseTest, method);
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        dumpResult(tr);
        super.onConfigurationFailure(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        dumpResult(tr);
        super.onTestFailure(tr);
    }

    @Attachment(value = "request-response-actions")
    public String dumpResult(ITestResult tr) {
        String logs = logsBuilder.buildLogsForFail(tr);
        System.out.println(logs);
        return logs;
    }
}
