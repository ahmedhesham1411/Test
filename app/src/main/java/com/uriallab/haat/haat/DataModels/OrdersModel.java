package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class OrdersModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<OrdersBean> orders;

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class OrdersBean {

            private int OrderUID;
            private int Ord_PaymentID;
            private String Ord_Dtls;
            private int Ord_DurationID;
            private String Ord_Additional_Dta;
            private int Ord_ClientID;
            private String Client_Lat;
            private String Client_Lng;
            private String Ord_Shop_Nm;
            private String OrdCoupon_Per;
            private String Shop_Lat;
            private String Shop_Lng;
            private int Ord_DriverID;
            private int Ord_Client_StatusID;
            private int Ord_Driver_StatusID;
            private double Ord_Items_Price;
            private double Ord_Offer_Amt;
            private double Ord_Offer_App_Amt;
            private double Ord_Offer_Vat;
            private double Ord_Offer_Vat_Amt;

            public String getOrd_Shop_ImgUrl() {
                return OrdCoupon_Per;
            }

            public void setOrd_Shop_ImgUrl(String OrdCoupon_Per) {
                OrdCoupon_Per = OrdCoupon_Per;
            }

            public int getOrderUID() {
                return OrderUID;
            }

            public void setOrderUID(int OrderUID) {
                this.OrderUID = OrderUID;
            }

            public int getOrd_PaymentID() {
                return Ord_PaymentID;
            }

            public void setOrd_PaymentID(int Ord_PaymentID) {
                this.Ord_PaymentID = Ord_PaymentID;
            }

            public String getOrd_Dtls() {
                return Ord_Dtls;
            }

            public void setOrd_Dtls(String Ord_Dtls) {
                this.Ord_Dtls = Ord_Dtls;
            }

            public int getOrd_DurationID() {
                return Ord_DurationID;
            }

            public void setOrd_DurationID(int Ord_DurationID) {
                this.Ord_DurationID = Ord_DurationID;
            }

            public String getOrd_Additional_Dta() {
                return Ord_Additional_Dta;
            }

            public void setOrd_Additional_Dta(String Ord_Additional_Dta) {
                this.Ord_Additional_Dta = Ord_Additional_Dta;
            }

            public int getOrd_ClientID() {
                return Ord_ClientID;
            }

            public void setOrd_ClientID(int Ord_ClientID) {
                this.Ord_ClientID = Ord_ClientID;
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

            public int getOrd_DriverID() {
                return Ord_DriverID;
            }

            public void setOrd_DriverID(int Ord_DriverID) {
                this.Ord_DriverID = Ord_DriverID;
            }

            public int getOrd_Client_StatusID() {
                return Ord_Client_StatusID;
            }

            public void setOrd_Client_StatusID(int Ord_Client_StatusID) {
                this.Ord_Client_StatusID = Ord_Client_StatusID;
            }

            public int getOrd_Driver_StatusID() {
                return Ord_Driver_StatusID;
            }

            public void setOrd_Driver_StatusID(int Ord_Driver_StatusID) {
                this.Ord_Driver_StatusID = Ord_Driver_StatusID;
            }

            public double getOrd_Items_Price() {
                return Ord_Items_Price;
            }

            public void setOrd_Items_Price(double Ord_Items_Price) {
                this.Ord_Items_Price = Ord_Items_Price;
            }

            public double getOrd_Offer_Amt() {
                return Ord_Offer_Amt;
            }

            public void setOrd_Offer_Amt(double Ord_Offer_Amt) {
                this.Ord_Offer_Amt = Ord_Offer_Amt;
            }

            public double getOrd_Offer_App_Amt() {
                return Ord_Offer_App_Amt;
            }

            public void setOrd_Offer_App_Amt(double Ord_Offer_App_Amt) {
                this.Ord_Offer_App_Amt = Ord_Offer_App_Amt;
            }

            public double getOrd_Offer_Vat() {
                return Ord_Offer_Vat;
            }

            public void setOrd_Offer_Vat(double Ord_Offer_Vat) {
                this.Ord_Offer_Vat = Ord_Offer_Vat;
            }

            public double getOrd_Offer_Vat_Amt() {
                return Ord_Offer_Vat_Amt;
            }

            public void setOrd_Offer_Vat_Amt(double Ord_Offer_Vat_Amt) {
                this.Ord_Offer_Vat_Amt = Ord_Offer_Vat_Amt;
            }
        }
    }
}