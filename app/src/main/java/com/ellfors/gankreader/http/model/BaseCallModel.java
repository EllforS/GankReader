package com.ellfors.gankreader.http.model;


/**
 * 统一处理回调结果实体类
 */
public class BaseCallModel<T>
{
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
