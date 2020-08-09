package com.uriallab.haat.haat.DataModels;

public class TermsModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private String itmes;

        public String getItmes() {
            return itmes;
        }

        public void setItmes(String itmes) {
            this.itmes = itmes;
        }
    }
}