package com.homework.vaisakh.congressapi;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by vaisakh on 11/21/16.
 */

public class FavItems extends Application {


    HashSet<String> legidIdSet = new HashSet<String>();
    HashSet<String> billIdSet = new HashSet<String>();
    HashSet<String> commIdSet = new HashSet<String>();

    public HashSet<String> getCommIdSet() {
        return commIdSet;
    }

    public void setCommIdSet(String commIdSet) {
        this.commIdSet.add(commIdSet);
    }



    public HashSet<String> getBillIdSet() {
        return billIdSet;
    }

    public void setBillIdSet(String billIdSet) {
        this.billIdSet.add(billIdSet);
    }

    public void delBillId(String billIdSet) {
        for (Iterator<String> i = this.billIdSet.iterator(); i.hasNext();) {
            String element = i.next();
            if (element.equalsIgnoreCase(billIdSet)) {
                i.remove();
            }
        }
    }

    public void delCommId(String commIdSet) {
        for (Iterator<String> i = this.commIdSet.iterator(); i.hasNext();) {
            String element = i.next();
            if (element.equalsIgnoreCase(commIdSet)) {
                i.remove();
            }
        }
    }



    public void setLegidIdSet(String legidIdSet) {
        this.legidIdSet.add(legidIdSet);
    }

    public HashSet<String> getLegidIdSet() {
        return legidIdSet;
    }

    public void delLegidId(String legidIdSet) {
        for (Iterator<String> i = this.legidIdSet.iterator(); i.hasNext();) {
            String element = i.next();
            if (element.equalsIgnoreCase(legidIdSet)) {
                i.remove();
            }
        }
    }




}
