package net.itxiu.foundation.logger;

import java.util.function.Supplier;

public class AdLogger {

    private final org.slf4j.Logger log ;

    public AdLogger(org.slf4j.Logger log){
        this.log = log;
    }

    public void debug(String format){
        if(log.isDebugEnabled())
            log.debug(format,format);
    }

    public void debug(String format, Supplier<Object[]> argsSupplier){
        if(log.isDebugEnabled())
            log.debug(format,argsSupplier.get());
    }

    public void info(String format){
        if(log.isInfoEnabled())
            log.info(format);
    }

    public void info(String format, Supplier<Object[]> argsSupplier){
        if(log.isInfoEnabled())
            log.info(format,argsSupplier.get());
    }

    public void error(String format, Supplier<Object[]> argsSupplier){
        if(log.isErrorEnabled())
            log.error(format,argsSupplier.get());
    }

    public void error(String format,Throwable t){
        if(log.isErrorEnabled())
            log.error(format,t);
    }

}
