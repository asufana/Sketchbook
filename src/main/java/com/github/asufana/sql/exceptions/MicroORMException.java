package com.github.asufana.sql.exceptions;

import lombok.*;

import org.slf4j.*;

@Getter
public class MicroORMException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(MicroORMException.class);
    
    private final Exception exception;
    private final String message;
    private final String sql;
    
    public MicroORMException(final String message) {
        this(null, message, null);
    }
    
    public MicroORMException(final Exception exception) {
        this(exception, String.format("%s: %s",
                                      exception.getClass().getSimpleName(),
                                      exception.getMessage()), null);
    }
    
    public MicroORMException(final Exception exception, final String sql) {
        this(exception, String.format("%s: %s",
                                      exception.getClass().getSimpleName(),
                                      exception.getMessage()), sql);
    }
    
    public MicroORMException(final Exception exception,
            final String message,
            final String sql) {
        super();
        this.exception = exception;
        this.message = message;
        this.sql = sql;
        
        logger.error("MicroORMException: {}", message);
    }
}
