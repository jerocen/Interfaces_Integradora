package com.example.interfaces_integradora.Retrofit;

public class ResponseGetUserValuesPlant {
    private String msg;
    private data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public data getData() {
        return data;
    }

    public void setData(data data) {
        this.data = data;
    }

    class data{
        private int id;
        private String value;
        private int feed_id;
        private String feed_key;
        private String created_at;
        private location location;
        private String lat;
        private String lon;
        private String ele;
        private long created_epoch;
        private String expiration;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getFeed_id() {
            return feed_id;
        }

        public void setFeed_id(int feed_id) {
            this.feed_id = feed_id;
        }

        public String getFeed_key() {
            return feed_key;
        }

        public void setFeed_key(String feed_key) {
            this.feed_key = feed_key;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public ResponseGetUserValuesPlant.location getLocation() {
            return location;
        }

        public void setLocation(ResponseGetUserValuesPlant.location location) {
            this.location = location;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getEle() {
            return ele;
        }

        public void setEle(String ele) {
            this.ele = ele;
        }

        public long getCreated_epoch() {
            return created_epoch;
        }

        public void setCreated_epoch(long created_epoch) {
            this.created_epoch = created_epoch;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }
    }
    class location{
        private String type;
        private geometry geometry;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ResponseGetUserValuesPlant.geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(ResponseGetUserValuesPlant.geometry geometry) {
            this.geometry = geometry;
        }
    }
    class geometry{
        private String type;
        private String coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(String coordinates) {
            this.coordinates = coordinates;
        }
    }
}
