package com.linuxclub.cdcfan.autoupdater;

/**
 * Created by peace_da on 2015/6/8.
 */
public interface UpdateCheckListener {
    void onCheckUpdateFail();

    void onCheckUpdateSucc(UpdateCheckResult result);
}
