package com.github.asufana.sql.exceptions;

import lombok.*;

import org.slf4j.*;

@Getter
public class MicroORMException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(MicroORMException.class);
    
    private final Exception exception;
    private final String message;
    
    public MicroORMException(final Exception exception) {
        this(exception, String.format("%s: %s",
                                      exception.getClass().getSimpleName(),
                                      exception.getMessage()));
    }
    
    public MicroORMException(final Exception exception, final String message) {
        super();
        this.exception = exception;
        this.message = message;
        
        logger.error("SqlException: {}", message);
    }
}
