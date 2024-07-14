package moe.qie2035.hz.service.processor;

import moe.qie2035.hz.service.callback.ICallback;

public class Processor<T> extends Thread {
    protected final ICallback<T> callback;

    public Processor(final ICallback<T> callback) {
        this.callback = callback;
        setDaemon(true);
    }

    protected void onProgress(T progressInfo) {
        if (callback == null) return;
        callback.onProgress(progressInfo);
    }

    protected void onFinished() {
        if (callback == null) return;
        callback.onFinish();
    }
}
