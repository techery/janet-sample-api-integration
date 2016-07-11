package io.techery.github.api.facility;

import io.techery.janet.ActionHolder;
import io.techery.janet.JanetException;
import io.techery.janet.converter.ConverterException;

public class Log {

    public static void logCurl(String curlCommand) {
        LogsBuffer.instance().forThread().append("  ").append(curlCommand).append('\n');
    }

    public static void logHttp(String message) {
        if (message.isEmpty()) message = "Body ::";
        if (message.contains("-->") && !message.contains("END")) LogsBuffer.instance().forThread().append("  |\n");
        LogsBuffer.instance().forThread().append("  ").append(message).append('\n');
        if (message.contains("-->") && message.contains("END")) LogsBuffer.instance().forThread().append("  |\n");
    }

    public static void logActionStart(ActionHolder holder) {
        LogsBuffer.instance().forThread()
                .append("----> ").append(getActionName(holder))
                .append("\n\\\n");
    }

    public static void logActionSuccess(ActionHolder holder) {
        LogsBuffer.instance().forThread()
                .append("/\n")
                .append("<---- ").append(getActionName(holder))
                .append("\n");
    }

    public static void logActionFail(ActionHolder holder, JanetException e) {
        LogsBuffer.instance().forThread().append("/\n")
                .append("<---x ").append(getActionName(holder)).append(" :: ").append(getExceptionMessage(e))
                .append("\n");
    }

    private static  <A> String getActionName(ActionHolder<A> holder) {
        return holder.action().getClass().getSimpleName();
    }

    private static String getExceptionMessage(JanetException e) {
        StringBuilder message = new StringBuilder(e.getMessage());
        if (e.getCause() instanceof ConverterException) {
            message.append(" :: ").append(e.getCause().getCause().getMessage());
        }
        return message.toString();
    }
}
