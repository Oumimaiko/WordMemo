package jp.oumimaiko.wordmemo;

import android.app.Application;

import io.realm.Realm;

public class WordMemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
