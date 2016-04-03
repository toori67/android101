package com.lizzardry.ril.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.concurrent.ConcurrentHashMap;

/**
 * async task 를 관리한다.
 */
public class RetroImageTaskManager {
    private static RetroImageTaskManager instance;
    public static synchronized RetroImageTaskManager getInstance() {
        if (instance == null) {
            instance = new RetroImageTaskManager();
        }
        return instance;
    }

    private ConcurrentHashMap<Integer, AsyncTask> taskMap;
    private RetroImageTaskManager() {
        taskMap = new ConcurrentHashMap<>();
    }

    public void put(Object keyObject, AsyncTask<String, Void, Bitmap> task, String url) {
        int key = getKey(keyObject);
        cancelOldTask(taskMap.putIfAbsent(key, task));
        task.execute(url);
    }

    public void cancel(Object keyObject) {
        int key = getKey(keyObject);
        cancelOldTask(taskMap.remove(key));
    }

    private int getKey(Object keyObject) {
        return keyObject.hashCode();
    }

    private void cancelOldTask(AsyncTask oldTask) {
        if (oldTask != null) {
            if (!AsyncTask.Status.FINISHED.equals(oldTask.getStatus())) {
                oldTask.cancel(true);
            }
        }
    }
}
