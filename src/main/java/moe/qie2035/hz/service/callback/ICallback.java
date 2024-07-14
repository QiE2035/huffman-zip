package moe.qie2035.hz.service.callback;

public interface ICallback<T> {
    void onProgress(T progressInfo);

    void onFinish();
}