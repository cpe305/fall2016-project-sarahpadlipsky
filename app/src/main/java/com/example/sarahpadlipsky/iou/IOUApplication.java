package com.example.sarahpadlipsky.iou;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * IOU Application
 * @author sarahpadlipsky
 * @version October 16, 2016
 */
public class IOUApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this.getBaseContext());

        //TODO: Change migration technique before deploying.
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();

        // Sets current user is the database.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long num = realm.where(User.class).count();
                // TODO: Should this check if it should update instead?
                System.out.println("NUMBER OF USERS " + num);
                User user = realm.createObject(User.class,Long.toString(num));
                //TODO: Get username from log-in.
                user.setName("Sarah");
                user.setIsCurrentUser(true);
            }
        });

        realm.close();

    }
}
