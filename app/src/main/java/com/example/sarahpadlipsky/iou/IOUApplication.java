package com.example.sarahpadlipsky.iou;

import android.app.Application;
import android.provider.Settings;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * IOU Application
 * @author sarahpadlipsky
 * @version October 28, 2016
 */
public class IOUApplication extends Application {

  // Database
  Realm realm;

  /**
   * Android lifecycle function. Called when application is opened for the first time.
   */
  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this.getBaseContext());

    //TODO: Change migration technique before deploying.
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded()
        .build();
    Realm.setDefaultConfiguration(realmConfiguration);
    realm = Realm.getDefaultInstance();
  }

  /**
   * Android lifecycle function. Called when application is completely closed.
   */
  @Override
  public void onTerminate() {
    super.onTerminate();
    realm.close();
  }

}

