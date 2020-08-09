package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class ServerStoresModel {

    private List<ResultEntity> result;

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public class ResultEntity {

        private String name;
        private String icon;
        private String place_id;

        public void setName(String name) {
            this.name = name;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        public String getPlace_id() {
            return place_id;
        }
    }
}