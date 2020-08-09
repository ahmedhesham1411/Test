package com.uriallab.haat.haat.DataModels;

public class LoginModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private String Code;
        private String User_AccessToken;

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getUser_AccessToken() {
            return User_AccessToken;
        }

        public void setUser_AccessToken(String User_AccessToken) {
            this.User_AccessToken = User_AccessToken;
        }
    }
}