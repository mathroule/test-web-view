package com.mathroule.testwebview.activity;

import com.good.gd.GDStateListener;

import java.util.Map;

import timber.log.Timber;

public class GDStateListenerImpl implements GDStateListener {

    @Override
    public void onAuthorized() {
        Timber.d("onAuthorized");
    }

    @Override
    public void onLocked() {
        Timber.d("onLocked");
    }

    @Override
    public void onWiped() {
        Timber.d("onWiped");
    }

    @Override
    public void onUpdateConfig(Map<String, Object> map) {
        Timber.d("onUpdateConfig %s", map);
    }

    @Override
    public void onUpdatePolicy(Map<String, Object> map) {
        Timber.d("onUpdatePolicy %s", map);
    }

    @Override
    public void onUpdateServices() {
        Timber.d("onUpdateServices");
    }

    @Override
    public void onUpdateEntitlements() {
        Timber.d("onUpdateEntitlements");
    }
}
