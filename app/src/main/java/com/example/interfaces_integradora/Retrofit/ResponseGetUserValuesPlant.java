package com.example.interfaces_integradora.Retrofit;

import java.util.List;

public class ResponseGetUserValuesPlant {
    private String msg;
    private List<Data> data;
    // Getters y setters...

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String feedkey;
        private String value;

        public String getFeedkey() {
            return feedkey;
        }

        public void setFeedkey(String feedkey) {
            this.feedkey = feedkey;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        // Getters y setters...
    }
}

