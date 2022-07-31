package dev.mayuna.sakuya.utils.logging;

import com.esotericsoftware.minlog.Log;
import dev.mayuna.modularbot.logging.MayuLogger;
import dev.mayuna.sakuya.SakuyaModule;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BotLogger extends Log.Logger implements LoggerFacade {

    @Override
    public void info(String message) {
        getLog().info(message);
    }

    @Override
    public void warn(String message) {
        getLog().warn(message);
    }

    @Override
    public void error(String message) {
        getLog().error(message);

    }

    @Override
    public void debug(String message) {
        getLog().mdebug(message);
    }

    @Override
    public void log(int level, String category, String message, Throwable ex) {
        StringBuilder builder = new StringBuilder(256);

        if (category != null) {
            builder.append('[');
            builder.append(category);
            builder.append("] ");
        }

        builder.append(message);

        if (ex != null) {
            StringWriter writer = new StringWriter(256);
            ex.printStackTrace(new PrintWriter(writer));
            builder.append('\n');
            builder.append(writer.toString().trim());
        }

        String finalMessage = builder.toString();

        switch (level) {
            case Log.LEVEL_ERROR -> error(finalMessage);
            case Log.LEVEL_WARN -> warn(finalMessage);
            case Log.LEVEL_INFO -> info(finalMessage);
            case Log.LEVEL_DEBUG, Log.LEVEL_TRACE -> debug(finalMessage);
        }
    }

    private MayuLogger getLog() {
        return SakuyaModule.getLog();
    }
}
