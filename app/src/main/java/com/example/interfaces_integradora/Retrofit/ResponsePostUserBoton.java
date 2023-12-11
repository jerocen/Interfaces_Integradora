package com.example.interfaces_integradora.Retrofit;

public class ResponsePostUserBoton {

        private String msg;
        private Data data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class Data {
            private String id;
            private String value;
            private int feed_id;
            private String feed_key;
            private String created_at;
            private long created_epoch;
            private String expiration;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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
}
