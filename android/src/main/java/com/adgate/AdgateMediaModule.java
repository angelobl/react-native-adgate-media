package com.adgate;


import com.adgatemedia.sdk.classes.AdGateMedia;
import com.adgatemedia.sdk.model.Conversion;
import com.adgatemedia.sdk.network.OnConversionsReceived;
import com.adgatemedia.sdk.network.OnOfferWallLoadFailed;
import com.adgatemedia.sdk.network.OnOfferWallLoadSuccess;
import com.adgatemedia.sdk.network.OnVideoLoadFailed;
import com.adgatemedia.sdk.network.OnVideoLoadSuccess;

import java.util.HashMap;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class AdgateMediaModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public AdgateMediaModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    public void launchOfferwall(final String wallCode,final String userId) {
        final HashMap<String, String> subids = new HashMap<String, String>();
        subids.put("s2", "my sub id");

        //AdGateMedia.getInstance().setDebugMode(true);
        AdGateMedia.getInstance().loadOfferWall(getCurrentActivity(),
                wallCode,
                   userId,
                   subids,
                new OnOfferWallLoadSuccess() {
                    @Override
                    public void onOfferWallLoadSuccess() {
                        
                        AdGateMedia.getInstance().showOfferWall(getCurrentActivity(),
                                new AdGateMedia.OnOfferWallClosed() {
                                    @Override
                                    public void onOfferWallClosed() {
                                        
                                    }
                                });
                    }
                },
                new OnOfferWallLoadFailed() {
                    @Override
                    public void onOfferWallLoadFailed(String reason) {
                        
                        
                    }
                });
    }

    @Override
    public String getName() {
        return "AdgateMedia";
    }

    @ReactMethod
    public void showOfferWall(final String wallCode,final String userId) {
    // get access to current UI thread first
    reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
    @Override
    public void run() {
      launchOfferwall(wallCode,userId);
      // you have access to main ui thread so you can effect
      // immediate behavior on window and decorView
    }
  });
}

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
