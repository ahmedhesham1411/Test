package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class CitiesModel {

    private NationalityModel.ResultBean result;

    public NationalityModel.ResultBean getResult() {
        return result;
    }

    public void setResult(NationalityModel.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<NationalityModel.ResultBean.DataBean> data;

        public List<NationalityModel.ResultBean.DataBean> getData() {
            return data;
        }

        public void setData(List<NationalityModel.ResultBean.DataBean> data) {
            this.data = data;
        }

        public static class DataBean {

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}