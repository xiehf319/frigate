package cn.cici.frigate.mqtt.future;

import cn.cici.frigate.mqtt.exception.SocketRuntimeException;
import cn.cici.frigate.mqtt.exception.SocketTimeoutException;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xiehf
 * @date 2019/6/16 17:30
 * @concat 370693739@qq.com
 **/
@Slf4j
@Data
public class InvokeFuture {

    protected Object result;

    protected AtomicBoolean done = new AtomicBoolean(false);

    protected AtomicBoolean success = new AtomicBoolean(false);

    protected Semaphore semaphore = new Semaphore(0);

    protected Throwable cause;

    protected Channel channel;

    protected Object attachment;

    protected List<InvokeFutureListener> listeners = new ArrayList<>();

    public InvokeFuture() {
    }

    public void addListener(InvokeFutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener can not be null.");
        }
        notifyListeners();
        listeners.add(listener);
    }

    private void notifyListeners() {
        if(isDone()) {
            for (InvokeFutureListener listener : listeners) {
                try {
                    listener.operationComplete(this);
                } catch (Exception e) {
                    log.error("failed to notify listeners when operation completed.", e);
                }
            }
        }
    }

    private void notifyListener(InvokeFutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener can not be null.");
        }
        if (isDone()) {
            try {
                listener.operationComplete(this);
            } catch (Exception e) {
                log.error("failed to notify listener when operation completed.", e);
            }
        }
    }


    public boolean isDone() {
        return done.get();
    }

    public Object getResult() throws SocketRuntimeException {
        if (!isDone()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (cause != null) {
            throw new SocketRuntimeException(cause);
        }
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
        done.set(true);
        success.set(true);
        semaphore.release(Integer.MAX_VALUE - semaphore.availablePermits());
        notifyListeners();
    }

    public Object getResult(long timeout, TimeUnit timeUnit) {
        if (!isDone()) {
            try {
                if (!semaphore.tryAcquire(timeout, timeUnit)) {
                    setCause(new SocketTimeoutException("time out."));
                }
            } catch (InterruptedException ex){
                throw new SocketTimeoutException(ex);
            }
        }
        if (cause != null) {
            throw new SocketRuntimeException(cause);
        }
        return this.result;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
        done.set(true);
        success.set(false);

        semaphore.release(Integer.MAX_VALUE - semaphore.availablePermits());
        notifyListeners();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }
}
