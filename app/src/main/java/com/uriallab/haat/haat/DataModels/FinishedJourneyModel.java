package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class FinishedJourneyModel {

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
            private String Payment_Ar_Nm;
            private String Payment_En_Nm;
            private String Ord_Dtls;
            private int Ord_DurationID;
            private String Duration_Ar_Nm;
            private String Duration_En_Nm;
            private String Ord_Additional_Dta;
            private int Ord_ClientID;
            private String Client_Full_Name;
            private String Client_Phone;
            private String Client_Address;
            private String Client_AccessToken;
            private String Client_Fcm;
            private boolean Client_IsDelivery;
            private boolean Client_IsActive;
            private int Client_Rate;
            private int Client_Count_Rate;
            private String Client_Lat;
            private String Client_Lng;
            private String Ord_Shop_Nm;
            private String Shop_Lat;
            private String Shop_Lng;
            private int Ord_DriverID;
            private String Driver_Full_Name;
            private String Driver_Phone;
            private String Driver_Address;
            private String Driver_AccessToken;
            private String Driver_Fcm;
            private boolean Driver_IsDelivery;
            private boolean Driver_IsActive;
            private int Driver_Rate;
            private int Driver_Count_Rate;
            private int Ord_Client_StatusID;
            private String Client_Status_Ar_Nm;
            private String Client_Status_En_Nm;
            private int Ord_Driver_StatusID;
            private String Driver_Status_Ar_Nm;
            private String Driver_Status_En_Nm;
            private String Ord_Shop_ImgUrl;
            private double Ord_Items_Price;
            private double Ord_Offer_Amt;
            private double Ord_Offer_App_Amt;
            private double Ord_Offer_Vat;
            private double Ord_Offer_Vat_Amt;

            public String getOrd_Shop_ImgUrl() {
                return Ord_Shop_ImgUrl;
            }

            public void setOrd_Shop_ImgUrl(String ord_Shop_ImgUrl) {
                Ord_Shop_ImgUrl = ord_Shop_ImgUrl;
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

            public String getPayment_Ar_Nm() {
                return Payment_Ar_Nm;
            }

            public void setPayment_Ar_Nm(String Payment_Ar_Nm) {
                this.Payment_Ar_Nm = Payment_Ar_Nm;
            }

            public String getPayment_En_Nm() {
                return Payment_En_Nm;
            }

            public void setPayment_En_Nm(String Payment_En_Nm) {
                this.Payment_En_Nm = Payment_En_Nm;
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

            public int getOrd_ClientID() {
                return Ord_ClientID;
            }

            public void setOrd_ClientID(int Ord_ClientID) {
                this.Ord_ClientID = Ord_ClientID;
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

            public int getOrd_Client_StatusID() {
                return Ord_Client_StatusID;
            }

            public void setOrd_Client_StatusID(int Ord_Client_StatusID) {
                this.Ord_Client_StatusID = Ord_Client_StatusID;
            }

            public String getClient_Status_Ar_Nm() {
                return Client_Status_Ar_Nm;
            }

            public void setClient_Status_Ar_Nm(String Client_Status_Ar_Nm) {
                this.Client_Status_Ar_Nm = Client_Status_Ar_Nm;
            }

            public String getClient_Status_En_Nm() {
                return Client_Status_En_Nm;
            }

            public void setClient_Status_En_Nm(String Client_Status_En_Nm) {
                this.Client_Status_En_Nm = Client_Status_En_Nm;
            }

            public int getOrd_Driver_StatusID() {
                return Ord_Driver_StatusID;
            }

            public void setOrd_Driver_StatusID(int Ord_Driver_StatusID) {
                this.Ord_Driver_StatusID = Ord_Driver_StatusID;
            }

            public String getDriver_Status_Ar_Nm() {
                return Driver_Status_Ar_Nm;
            }

            public void setDriver_Status_Ar_Nm(String Driver_Status_Ar_Nm) {
                this.Driver_Status_Ar_Nm = Driver_Status_Ar_Nm;
            }

            public String getDriver_Status_En_Nm() {
                return Driver_Status_En_Nm;
            }

            public void setDriver_Status_En_Nm(String Driver_Status_En_Nm) {
                this.Driver_Status_En_Nm = Driver_Status_En_Nm;
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