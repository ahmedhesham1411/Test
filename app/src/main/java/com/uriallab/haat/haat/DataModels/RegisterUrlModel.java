package com.uriallab.haat.haat.DataModels;

public class RegisterUrlModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private String registerurl;

        public String getRegisterurl() {
            return registerurl;
        }

        public void setRegisterurl(String registerurl) {
            this.registerurl = registerurl;
        }
    }
}