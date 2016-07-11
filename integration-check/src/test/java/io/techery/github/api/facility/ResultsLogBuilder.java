package io.techery.github.api.facility;

import io.techery.github.api.tests.BaseTest;

import org.testng.ITestResult;

public class ResultsLogBuilder {

    private final LogsBuffer logsBuffer;

    public ResultsLogBuilder(LogsBuffer logsBuffer) {
        this.logsBuffer = logsBuffer;
    }

    public String buildLogsForFail(ITestResult tr) {
        StringBuilder builder = new StringBuilder();
        // Suite logs
        StringBuilder suiteLog = logsBuffer.forSuite();
        if (suiteLog != null && suiteLog.length() > 0) {
            builder.append(">>> Suite Check >>>").append("\n|\n");
            builder.append(suiteLog).append("|\n");
            builder.append("<<< Suite Check <<<\n\n");
        }
        // Config logs
        BaseTest instance = (BaseTest) tr.getInstance();

        if (instance == null) {
            return builder.toString();
        }

        StringBuilder configLog = logsBuffer.forConfig(instance);

        if (configLog != null && configLog.length() > 0) {
            builder.append(">>> Config >>>").append("\n|\n");
            builder.append(configLog).append("|\n");
            builder.append("<<< Config <<<\n\n");
        }
        // Tests depended upon
        String[] methodsDependedUpon = tr.getMethod().getMethodsDependedUpon();
        if (methodsDependedUpon.length > 0) {
            builder.append(">>> Depended upon >>>").append("\n|\n");
            for (String method : methodsDependedUpon) {
                builder.append(logsBuffer.forMethod(method));
            }
            builder.append("|\n");
            builder.append("<<< Depended upon <<<").append("\n\n");
        }

        // Real test method
        StringBuilder testBuilder = logsBuffer.forMethod(instance, tr.getMethod().getMethodName());

        if (testBuilder != null && testBuilder.length() > 0) {
            builder.append(">>> Test Job >>>").append("\n|\n");
            builder.append(testBuilder).append("|\n");
            builder.append("<<< Test Job <<<").append("\n\n");
        }
        //
        return builder.toString();
    }
}
