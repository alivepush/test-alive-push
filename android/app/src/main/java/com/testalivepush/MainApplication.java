package com.testalivepush;

import android.app.Application;
import android.util.Log;

import com.alivepush.BundleManager;
import com.facebook.react.JSCConfig;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.cxxbridge.JSBundleLoader;
import com.facebook.react.cxxbridge.JavaScriptExecutor;
import com.rnziparchive.RNZipArchivePackage;
import com.RNFetchBlob.RNFetchBlobPackage;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.alivepush.RNAlivePushPackage;
import com.alivepush.RNAlivePushModule;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            return RNAlivePushModule.getJSBundleFile(MainApplication.this);
        }

//        @Nullable
//        @Override
//        protected String getBundleAssetName() {
//            String bundleAssetName = RNAlivePushModule.getBundleAssetName(MainApplication.this);
//            if (bundleAssetName != null) {
//                return bundleAssetName;
//            }
//            return super.getBundleAssetName();
//        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new RNZipArchivePackage(),
                    new RNFetchBlobPackage(),
                    new RNDeviceInfo(),
                    new RNAlivePushPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        BundleManager.init(mReactNativeHost.getReactInstanceManager());
    }
}
