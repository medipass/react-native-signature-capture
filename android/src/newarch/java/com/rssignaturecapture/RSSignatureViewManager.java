package com.rssignaturecapture;

import android.graphics.Color;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.RSSignatureViewManagerDelegate;
import com.facebook.react.viewmanagers.RSSignatureViewManagerInterface;

@ReactModule(name = RSSignatureViewManager.NAME)
public class RSSignatureViewManager extends ViewGroupManager<RSSignatureCaptureMainView> implements RSSignatureViewManagerInterface<RSSignatureCaptureMainView> {

    public static final String NAME = "RSSignatureView";

    private final ViewManagerDelegate<RSSignatureCaptureMainView> mDelegate;
    private final RSSignatureCaptureContextModule mContextModule;

    public RSSignatureViewManager(ReactApplicationContext reactContext) {
        mContextModule = new RSSignatureCaptureContextModule(reactContext);
        mDelegate = new RSSignatureViewManagerDelegate<>(this);
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<RSSignatureCaptureMainView> getDelegate() {
        return mDelegate;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public RSSignatureCaptureMainView createViewInstance(ThemedReactContext context) {
        return new RSSignatureCaptureMainView(context, mContextModule.getActivity());
    }

    @Override
    @ReactProp(name = "saveImageFileInExtStorage")
    public void setSaveImageFileInExtStorage(RSSignatureCaptureMainView view, boolean value) {
        view.setSaveFileInExtStorage(value);
    }

    @Override
    @ReactProp(name = "viewMode")
    public void setViewMode(RSSignatureCaptureMainView view, @Nullable String value) {
        view.setViewMode(value);
    }

    @Override
    @ReactProp(name = "showBorder")
    public void setShowBorder(RSSignatureCaptureMainView view, boolean value) {
        // This property exists in the view but might need a setter if not handled via layout
    }

    @Override
    @ReactProp(name = "showNativeButtons")
    public void setShowNativeButtons(RSSignatureCaptureMainView view, boolean value) {
        view.setShowNativeButtons(value);
    }

    @Override
    @ReactProp(name = "showTitleLabel")
    public void setShowTitleLabel(RSSignatureCaptureMainView view, boolean value) {
        // Handled in view logic if necessary
    }

    @Override
    @ReactProp(name = "maxSize")
    public void setMaxSize(RSSignatureCaptureMainView view, int value) {
        view.setMaxSize(value);
    }

    @Override
    @ReactProp(name = "minStrokeWidth")
    public void setMinStrokeWidth(RSSignatureCaptureMainView view, int value) {
        view.getSignatureView().setMinStrokeWidth(value);
    }

    @Override
    @ReactProp(name = "maxStrokeWidth")
    public void setMaxStrokeWidth(RSSignatureCaptureMainView view, int value) {
        view.getSignatureView().setMaxStrokeWidth(value);
    }

    @Override
    @ReactProp(name = "strokeColor")
    public void setStrokeColor(RSSignatureCaptureMainView view, @Nullable Integer value) {
        if (value != null) {
            view.getSignatureView().setStrokeColor(value);
        }
    }

    @Override
    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(RSSignatureCaptureMainView view, @Nullable Integer value) {
        if (value != null) {
            view.getSignatureView().setBackgroundColor(value);
        }
    }

    // Overriding specific String-based color setters from the interface if Codegen produces them
    // Note: Fabric usually passes colors as Integers.

    @Override
    public void saveImage(RSSignatureCaptureMainView view) {
        view.saveImage();
    }

    @Override
    public void resetImage(RSSignatureCaptureMainView view) {
        view.reset();
    }

    @Override
    public void receiveCommand(RSSignatureCaptureMainView view, String commandId, @Nullable ReadableArray args) {
        mDelegate.receiveCommand(view, commandId, args);
    }
}
