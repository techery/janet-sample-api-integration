package io.techery.github.api.facility;

import io.techery.github.api.tests.BaseTest;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogsBuffer {

    ///////////////////////////////////////////////////////////////////////////
    // Singleton
    ///////////////////////////////////////////////////////////////////////////

    private static LogsBuffer instance;

    public static final LogsBuffer instance() {
        if (instance == null) {
            synchronized (LogsBuffer.class) {
                if (instance == null) instance = new LogsBuffer();
            }
        }
        return instance;
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    final ThreadLocal<StringBuilder> currentLogger = new ThreadLocal<StringBuilder>();
    final Map<String, StringBuilder> logPool = new ConcurrentHashMap<String, StringBuilder>();
    //
    static final String SUITE_LOG_KEY = "suite_log";
    static final String CONFIG_LOG_KEY = "config_log";
    static final String TEARDOWN_LOG_KEY = "teardown_log";

    private void prepareForKey(String key) {
        StringBuilder stringBuilder;
        if (logPool.containsKey(key)) {
            stringBuilder = logPool.get(key);
        } else {
            stringBuilder = new StringBuilder();
        }
        currentLogger.set(stringBuilder);
        logPool.put(key, stringBuilder);
    }

    public void prepareForTestSuite() {
        prepareForKey(SUITE_LOG_KEY);
    }

    public void prepareForTestClass(BaseTest testInstance) {
        prepareForKey(configKey(testInstance));
    }

    public void prepareForTestMethod(BaseTest testInstance, Method testMethod) {
        StringBuilder stringBuilder = new StringBuilder();
        currentLogger.set(stringBuilder);
        logPool.put(methodKey(testInstance, testMethod), stringBuilder);
    }

    public void prepareForTestClassTearDown(BaseTest testInstance) {
        prepareForKey(teardownKey(testInstance));
    }


    public StringBuilder forThread() {
        return currentLogger.get();
    }

    public StringBuilder forSuite() {
        return logPool.get(SUITE_LOG_KEY);
    }

    public StringBuilder forConfig(BaseTest testInstance) {
        return logPool.get(configKey(testInstance));
    }

    public StringBuilder forMethod(BaseTest testInstance, String methodName) {
        return logPool.get(methodKey(testInstance, methodName));
    }

    public StringBuilder forMethod(String methodName) {
        return logPool.get(methodName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Key builders
    ///////////////////////////////////////////////////////////////////////////

    private String configKey(BaseTest testInstance) {
        return testInstance.getClass().getName() + "_" + CONFIG_LOG_KEY;
    }

    private String teardownKey(BaseTest testInstance) {
        return testInstance.getClass().getName() + "_" + TEARDOWN_LOG_KEY;
    }

    private String methodKey(BaseTest testInstance, Method testMethod) {
        return methodKey(testInstance, testMethod.getName());
    }

    private String methodKey(BaseTest testInstance, String testMethodName) {
        return testInstance.getClass().getName() + "." + testMethodName;
    }
}
