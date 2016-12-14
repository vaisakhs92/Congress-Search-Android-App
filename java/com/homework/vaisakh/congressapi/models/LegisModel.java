package com.homework.vaisakh.congressapi.models;

import android.support.v7.widget.Toolbar;

/**
 * Created by vaisakh on 11/14/16.
 */

public class LegisModel {

    private String bioguide_id;



    private String party;
    private String chamber;
    private int district;
    private String first_name;
    private String last_name;
    private String state_name;
    private String birthday;
    private String term_end;
    private String term_start;
    private String phone;
    private String fax;
    private String oc_email;
    private String website;
    private String facebook_id;
    private String twitter_id;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }



    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }



    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }





    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    private String office;

    public String getOc_email() {
        return oc_email;
    }

    public void setOc_email(String oc_email) {
        this.oc_email = oc_email;
    }



    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }



    public String getTerm_start() {
        return term_start;
    }

    public void setTerm_start(String term_start) {
        this.term_start = term_start;
    }



    public String getTerm_end() {
        return term_end;
    }

    public void setTerm_end(String term_end) {
        this.term_end = term_end;
    }



    public String getContact_form() {
        return phone;
    }

    public void setContact_form(String contact_form) {
        this.phone = contact_form;
    }







    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }



    public String getFirstname() {
        return first_name;
    }

    public void setFirstname(String firstname) {
        this.first_name = firstname;
    }



    public String getLastname() {
        return last_name;
    }

    public void setLastname(String lastname) {
        this.last_name = lastname;
    }



    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }



    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }



    public String getBioguideid() {
        return bioguide_id;
    }

    public void setBioguideid(String bioguideid) {
        this.bioguide_id = bioguideid;
    }

}
