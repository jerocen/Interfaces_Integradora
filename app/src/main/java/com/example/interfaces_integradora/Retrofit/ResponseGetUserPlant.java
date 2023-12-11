package com.example.interfaces_integradora.Retrofit;

import com.example.interfaces_integradora.Models.ItemPlant;

import java.util.List;

public class ResponseGetUserPlant {
    private String msg;
    private List<data> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<data> getData() {
        return data;
    }

    public void setData(List<data> data) {
        this.data = data;
    }


    public class data{
        private int id;
        private String name;

        private int user_id;
        private String groupkey;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroupkey() {
            return groupkey;
        }

        public void setGroupkey(String groupkey) {
            this.groupkey = groupkey;
        }
    }
}
