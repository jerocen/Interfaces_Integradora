package com.example.interfaces_integradora.Retrofit;

import java.util.List;
import java.util.Map;

public class ResponsePostUserRegister {

    private String msg;
    private Userdata userdata;
    private ErrorResponse errorResponse;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
    public Userdata getUserdata() {
        return userdata;
    }

    public void setUserdata(Userdata userdata) {
        this.userdata = userdata;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public class ErrorResponse {
        private String msg;
        private Map<String, List<String>> data;

        public String getMsg() {
            return msg;
        }

        public Map<String, List<String>> getData() {
            return data;
        }
    }

    public class Userdata{

        private String name;
        private String email;
        private String password;
        private String updated_at;
        private String created_at;
        private int id;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

}
