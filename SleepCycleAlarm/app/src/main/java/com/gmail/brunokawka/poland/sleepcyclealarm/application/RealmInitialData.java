package com.gmail.brunokawka.poland.sleepcyclealarm.application;


import com.gmail.brunokawka.poland.sleepcyclealarm.data.Alarm;

import io.realm.Realm;

public class RealmInitialData implements Realm.Transaction {
    @Override
    public void execute(Realm realm) {
        Alarm alarm = new Alarm();
        alarm.setTitle("halo");
        alarm.setSummary("halohalo");
        realm.insertOrUpdate(alarm);
    }

    @Override
    public int hashCode() {
        return RealmInitialData.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof RealmInitialData;
    }
}