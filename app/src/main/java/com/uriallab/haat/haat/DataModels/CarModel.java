package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class CarModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {

            private String id;
            private String Care_Type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCare_Type() {
                return Care_Type;
            }

            public void setCare_Type(String Care_Type) {
                this.Care_Type = Care_Type;
            }
        }
    }
}