package com.hn.zfz.fzframework.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.hn.zfz.bean.avosmodel.AVNews;

/**
 * Created by huming on 2015/12/22.
 */
public class FzApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(AVNews.class);
        AVOSCloud.initialize(this, "sNlyqqwizrextfdcMsGvSpei", "pRiLwVQRznx75aspm95naJSP");
    }
}
