package com.homework.vaisakh.congressapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vaisakh on 11/18/16.
 */

public class BillModel {
    public String introduced_on;
    public String official_title;
    public String bill_id;
    public String chamber;
    public String pdf;
    public Sponsor sponsor;
    public History history;
    public Lastversion last_version;


    public String getUrls() {
        return urls.getCongressUrl();
    }

    public Url urls;
    public String bill_type;



    class Url {
        private String congress;

        public String getCongressUrl() {
            return congress;
        }

        public void setCongressUrl(String congress) {
            this.congress = congress;
        }
    }




    public String getSponsor() {
        return sponsor.getTitle()+". "+sponsor.getLast_name()+","+sponsor.getFirst_name();
    }



    public boolean getHistory() {
        return history.isActive();
    }

    public String getLastversion() {
        return last_version.getVersion_name();
    }

    public String getpdfUrl() {
        return last_version.urls.getPdf();
    }

     class Sponsor {
         String first_name;
         String last_name;
         String title;
         String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getLast_name() {
            return last_name;
        }



        public String getFirst_name() {
            return first_name;
        }



    }

     class History {
        public boolean isActive() {
            return active;
        }

         boolean active;


    }

     class Lastversion {

          Url urls;
         class Url {
             public String getPdf() {
                 return pdf;
             }

             private String pdf;


         }

        public String getVersion_name() {
            if(version_name!=null)
            return version_name;
            else
                return "N.A";
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

         String version_name;

    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }


    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void setShort_title(String short_title) {
        this.official_title = short_title;
    }




    public String getBill_id() {

        return bill_id.toUpperCase();
    }



    public String getShort_title() {

        return official_title;
    }



    public String getIntroduced_on() {

        return introduced_on;
    }



}
