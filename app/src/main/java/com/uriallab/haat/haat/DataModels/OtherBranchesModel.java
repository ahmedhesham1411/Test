package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class OtherBranchesModel {

    List<BranchBean> productBeans;

    public List<BranchBean> getProductBeans() {
        return productBeans;
    }

    public void setProductBeans(List<BranchBean> productBeans) {
        this.productBeans = productBeans;
    }

    public static class BranchBean {

        private String name;
        private String img;
        private String address;
        private double lat;
        private double lng;

        public BranchBean(String name, String address, String img, double lat, double lng) {
            this.name = name;
            this.address = address;
            this.img = img;
            this.lat = lat;
            this.lng = lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}