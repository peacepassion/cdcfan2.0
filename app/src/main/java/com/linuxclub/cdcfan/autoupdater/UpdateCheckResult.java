package com.linuxclub.cdcfan.autoupdater;

import com.google.gson.annotations.SerializedName;

/**
 * Created by peace_da on 2015/6/8.
 */
public class UpdateCheckResult {

    public static final int EMPTY_VERSION_CODE = 0;

    @SerializedName("updateInfo") public UpdateInfo mUpdateInfo;

    private boolean mCheckSucc = false;

    public boolean isCheckSucc() {
        return mCheckSucc;
    }

    public void setCheckSucc(boolean flag) {
        mCheckSucc = flag;
    }

    public int getVersionCode() {
        if (mUpdateInfo != null) {
            return Integer.valueOf(mUpdateInfo.mVersionCode).intValue();
        }
        return EMPTY_VERSION_CODE;
    }

    public boolean isForceUpdate() {
        if (mUpdateInfo != null) {
            return mUpdateInfo.mForceUpdate;
        }
        return false;
    }

    public String getApkUrl() {
        if (mUpdateInfo != null) {
            return mUpdateInfo.mApkUrl;
        }
        return "";
    }

    public String getUpdateTips() {
        if (mUpdateInfo != null) {
            return mUpdateInfo.mUpdateTips.mZhCN;
        }
        return "";
    }

    @Override
    public String toString() {
        return "version code: " + mUpdateInfo.mVersionCode
                + ", force update: " + mUpdateInfo.mForceUpdate
                + ", apk url: " + mUpdateInfo.mApkUrl
                + ", update tips: " + mUpdateInfo.mUpdateTips.mZhCN;
    }

    private static class UpdateInfo {
        @SerializedName("versionCode")
        private String mVersionCode;

        @SerializedName("forceUpdate")
        private boolean mForceUpdate;
        @SerializedName("apkUrl")
        public String mApkUrl;
        @SerializedName("updateTips")
        public UpdateTips mUpdateTips;

        private static class UpdateTips {
            @SerializedName("default")
            public String mDefault;
            @SerializedName("zh_CN")
            public String mZhCN;
        }
    }

}
