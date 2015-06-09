package com.linuxclub.cdcfan.autoupdater;

/**
 * Created by peace_da on 2015/6/9.
 */
public interface UpdateListener {

    void onDownloadBegin();

    void onDownloading(int percent);

    void onDownloadSucc();

    void onDownloadError(Throwable err);

}
