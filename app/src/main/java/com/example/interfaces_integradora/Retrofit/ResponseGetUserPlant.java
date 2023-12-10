package com.example.interfaces_integradora.Retrofit;

import com.example.interfaces_integradora.Models.ItemPlant;

import java.util.List;

public class ResponseGetUserPlant {
    private String msg;
    private List<Group> groups;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public class Group{
        private String name;
        private String groupkey;

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
