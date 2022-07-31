package dev.mayuna.sakuya.utils.logging;

public interface LoggerFacade {

    void info(String message);
    void warn(String message);
    void error(String message);
    void debug(String message);

}
