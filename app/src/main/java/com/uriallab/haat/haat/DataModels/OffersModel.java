package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class OffersModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<OffersBean> offers;

        public List<OffersBean> getOffers() {
            return offers;
        }

        public void setOffers(List<OffersBean> offers) {
            this.offers = offers;
        }

        public static class OffersBean {

            private int Client_ID;
            private int Driver_ID;
            private String Client_Full_Name;
            private String Client_Phone;
            private String Client_Address;
            private String Client_AccessToken;
            private String Client_Fcm;
            private boolean Client_IsDelivery;
            private boolean Client_IsActive;
            private int Client_Rate;
            private int Client_Count_Rate;
            private String Driver_ImgUrl;
            private String Driver_Full_Name;
            private String Driver_Phone;
            private String Driver_Address;
            private String Driver_AccessToken;
            private String Driver_Fcm;
            private boolean Driver_IsDelivery;
            private boolean Driver_IsActive;
            private int Driver_Rate;
            private int Driver_Count_Rate;
            private int Order_ID;
            private String Ord_Dtls;
            private String Duration_Ar_Nm;
            private String Duration_En_Nm;
            private String Ord_Additional_Dta;
            private String Client_Lat;
            private String Client_Lng;
            private String Ord_Shop_Nm;
            private String Shop_Lat;
            private String Shop_Lng;
            private double Offer_Price;
            private double Final_Amt;
            private String Ord_Shop_ImgUrl;
            private String Driver_Lat;
            private String Driver_Long;
            private double OrdCoupon_Per;

            public String getDriver_ImgUrl() {
                return Driver_ImgUrl;
            }

            public void setDriver_ImgUrl(String driver_ImgUrl) {
                Driver_ImgUrl = driver_ImgUrl;
            }

            public double getOrdCoupon_Per() {
                return OrdCoupon_Per;
            }

            public void setOrdCoupon_Per(double ordCoupon_Per) {
                OrdCoupon_Per = ordCoupon_Per;
            }

            public double getOrdCoupon_Amt() {
                return OrdCoupon_Per;
            }

            public void setOrdCoupon_Amt(double ordCoupon_Amt) {
                OrdCoupon_Per = ordCoupon_Amt;
            }

            public String getDriver_Lat() {
                return Driver_Lat;
            }

            public void setDriver_Lat(String driver_Lat) {
                Driver_Lat = driver_Lat;
            }

            public String getDriver_Long() {
                return Driver_Long;
            }

            public void setDriver_Long(String driver_Long) {
                Driver_Long = driver_Long;
            }

            public String getOrd_Shop_ImgUrl() {
                return Ord_Shop_ImgUrl;
            }

            public void setOrd_Shop_ImgUrl(String ord_Shop_ImgUrl) {
                Ord_Shop_ImgUrl = ord_Shop_ImgUrl;
            }

            public double getFinal_Amt() {
                return Final_Amt;
            }

            public void setFinal_Amt(double final_Amt) {
                Final_Amt = final_Amt;
            }

            public int getClient_ID() {
                return Client_ID;
            }

            public void setClient_ID(int Client_ID) {
                this.Client_ID = Client_ID;
            }

            public int getDriver_ID() {
                return Driver_ID;
            }

            public void setDriver_ID(int Driver_ID) {
                this.Driver_ID = Driver_ID;
            }

            public String getClient_Full_Name() {
                return Client_Full_Name;
            }

            public void setClient_Full_Name(String Client_Full_Name) {
                this.Client_Full_Name = Client_Full_Name;
            }

            public String getClient_Phone() {
                return Client_Phone;
            }

            public void setClient_Phone(String Client_Phone) {
                this.Client_Phone = Client_Phone;
            }

            public String getClient_Address() {
                return Client_Address;
            }

            public void setClient_Address(String Client_Address) {
                this.Client_Address = Client_Address;
            }

            public String getClient_AccessToken() {
                return Client_AccessToken;
            }

            public void setClient_AccessToken(String Client_AccessToken) {
                this.Client_AccessToken = Client_AccessToken;
            }

            public String getClient_Fcm() {
                return Client_Fcm;
            }

            public void setClient_Fcm(String Client_Fcm) {
                this.Client_Fcm = Client_Fcm;
            }

            public boolean isClient_IsDelivery() {
                return Client_IsDelivery;
            }

            public void setClient_IsDelivery(boolean Client_IsDelivery) {
                this.Client_IsDelivery = Client_IsDelivery;
            }

            public boolean isClient_IsActive() {
                return Client_IsActive;
            }

            public void setClient_IsActive(boolean Client_IsActive) {
                this.Client_IsActive = Client_IsActive;
            }

            public int getClient_Rate() {
                return Client_Rate;
            }

            public void setClient_Rate(int Client_Rate) {
                this.Client_Rate = Client_Rate;
            }

            public int getClient_Count_Rate() {
                return Client_Count_Rate;
            }

            public void setClient_Count_Rate(int Client_Count_Rate) {
                this.Client_Count_Rate = Client_Count_Rate;
            }

            public String getDriver_Full_Name() {
                return Driver_Full_Name;
            }

            public void setDriver_Full_Name(String Driver_Full_Name) {
                this.Driver_Full_Name = Driver_Full_Name;
            }

            public String getDriver_Phone() {
                return Driver_Phone;
            }

            public void setDriver_Phone(String Driver_Phone) {
                this.Driver_Phone = Driver_Phone;
            }

            public String getDriver_Address() {
                return Driver_Address;
            }

            public void setDriver_Address(String Driver_Address) {
                this.Driver_Address = Driver_Address;
            }

            public String getDriver_AccessToken() {
                return Driver_AccessToken;
            }

            public void setDriver_AccessToken(String Driver_AccessToken) {
                this.Driver_AccessToken = Driver_AccessToken;
            }

            public String getDriver_Fcm() {
                return Driver_Fcm;
            }

            public void setDriver_Fcm(String Driver_Fcm) {
                this.Driver_Fcm = Driver_Fcm;
            }

            public boolean isDriver_IsDelivery() {
                return Driver_IsDelivery;
            }

            public void setDriver_IsDelivery(boolean Driver_IsDelivery) {
                this.Driver_IsDelivery = Driver_IsDelivery;
            }

            public boolean isDriver_IsActive() {
                return Driver_IsActive;
            }

            public void setDriver_IsActive(boolean Driver_IsActive) {
                this.Driver_IsActive = Driver_IsActive;
            }

            public int getDriver_Rate() {
                return Driver_Rate;
            }

            public void setDriver_Rate(int Driver_Rate) {
                this.Driver_Rate = Driver_Rate;
            }

            public int getDriver_Count_Rate() {
                return Driver_Count_Rate;
            }

            public void setDriver_Count_Rate(int Driver_Count_Rate) {
                this.Driver_Count_Rate = Driver_Count_Rate;
            }

            public int getOrder_ID() {
                return Order_ID;
            }

            public void setOrder_ID(int Order_ID) {
                this.Order_ID = Order_ID;
            }

            public String getOrd_Dtls() {
                return Ord_Dtls;
            }

            public void setOrd_Dtls(String Ord_Dtls) {
                this.Ord_Dtls = Ord_Dtls;
            }

            public String getDuration_Ar_Nm() {
                return Duration_Ar_Nm;
            }

            public void setDuration_Ar_Nm(String Duration_Ar_Nm) {
                this.Duration_Ar_Nm = Duration_Ar_Nm;
            }

            public String getDuration_En_Nm() {
                return Duration_En_Nm;
            }

            public void setDuration_En_Nm(String Duration_En_Nm) {
                this.Duration_En_Nm = Duration_En_Nm;
            }

            public String getOrd_Additional_Dta() {
                return Ord_Additional_Dta;
            }

            public void setOrd_Additional_Dta(String Ord_Additional_Dta) {
                this.Ord_Additional_Dta = Ord_Additional_Dta;
            }

            public String getClient_Lat() {
                return Client_Lat;
            }

            public void setClient_Lat(String Client_Lat) {
                this.Client_Lat = Client_Lat;
            }

            public String getClient_Lng() {
                return Client_Lng;
            }

            public void setClient_Lng(String Client_Lng) {
                this.Client_Lng = Client_Lng;
            }

            public String getOrd_Shop_Nm() {
                return Ord_Shop_Nm;
            }

            public void setOrd_Shop_Nm(String Ord_Shop_Nm) {
                this.Ord_Shop_Nm = Ord_Shop_Nm;
            }

            public String getShop_Lat() {
                return Shop_Lat;
            }

            public void setShop_Lat(String Shop_Lat) {
                this.Shop_Lat = Shop_Lat;
            }

            public String getShop_Lng() {
                return Shop_Lng;
            }

            public void setShop_Lng(String Shop_Lng) {
                this.Shop_Lng = Shop_Lng;
            }

            public double getOffer_Price() {
                return Offer_Price;
            }

            public void setOffer_Price(double Offer_Price) {
                this.Offer_Price = Offer_Price;
            }
        }
    }
}