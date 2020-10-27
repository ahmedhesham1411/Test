package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class ChatModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public static class ResultEntity {

        private UserDataEntity userData;
        private List<MessagesEntity> messages;
        private int ChatId;
        private OrderEntity order;

        public void setUserData(UserDataEntity userData) {
            this.userData = userData;
        }

        public void setMessages(List<MessagesEntity> messages) {
            this.messages = messages;
        }

        public void setChatId(int ChatId) {
            this.ChatId = ChatId;
        }

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public UserDataEntity getUserData() {
            return userData;
        }

        public List<MessagesEntity> getMessages() {
            return messages;
        }

        public int getChatId() {
            return ChatId;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public class UserDataEntity {

            private boolean IsDelivery;
            private int User_GenderID;
            private String User_ImgUrl;
            private boolean IsActive;
            private int Delivery_Status;
            private String User_Phone;
            private String User_AccessToken;
            private String User_Address;
            private int User_Rate;
            private String User_CarID;
            private String EditUser;
            private String User_CityID;
            private int User_Count_Rate;
            private String User_NationalID;
            private String User_CarYearID;
            private int UserUID;
            private String User_BirthDate;
            private String AdDate;
            private String User_Fcm;
            private String User_Full_Nm;
            private String User_Bank_Acc;
            private boolean IsEnabled;
            private String User_License_ImgUrl;
            private String EditDate;
            private String User_CarModelID;
            private int Code;
            private String User_Mail;
            private String User_Code;
            private String AdUser;
            private String User_NationalID_ImgUrl;
            private String Mobile_Type;
            private String User_CountryID;

            public void setIsDelivery(boolean IsDelivery) {
                this.IsDelivery = IsDelivery;
            }

            public void setUser_GenderID(int User_GenderID) {
                this.User_GenderID = User_GenderID;
            }

            public void setUser_ImgUrl(String User_ImgUrl) {
                this.User_ImgUrl = User_ImgUrl;
            }

            public void setIsActive(boolean IsActive) {
                this.IsActive = IsActive;
            }

            public void setDelivery_Status(int Delivery_Status) {
                this.Delivery_Status = Delivery_Status;
            }

            public void setUser_Phone(String User_Phone) {
                this.User_Phone = User_Phone;
            }

            public void setUser_AccessToken(String User_AccessToken) {
                this.User_AccessToken = User_AccessToken;
            }

            public void setUser_Address(String User_Address) {
                this.User_Address = User_Address;
            }

            public void setUser_Rate(int User_Rate) {
                this.User_Rate = User_Rate;
            }

            public void setUser_CarID(String User_CarID) {
                this.User_CarID = User_CarID;
            }

            public void setEditUser(String EditUser) {
                this.EditUser = EditUser;
            }

            public void setUser_CityID(String User_CityID) {
                this.User_CityID = User_CityID;
            }

            public void setUser_Count_Rate(int User_Count_Rate) {
                this.User_Count_Rate = User_Count_Rate;
            }

            public void setUser_NationalID(String User_NationalID) {
                this.User_NationalID = User_NationalID;
            }

            public void setUser_CarYearID(String User_CarYearID) {
                this.User_CarYearID = User_CarYearID;
            }

            public void setUserUID(int UserUID) {
                this.UserUID = UserUID;
            }

            public void setUser_BirthDate(String User_BirthDate) {
                this.User_BirthDate = User_BirthDate;
            }

            public void setAdDate(String AdDate) {
                this.AdDate = AdDate;
            }

            public void setUser_Fcm(String User_Fcm) {
                this.User_Fcm = User_Fcm;
            }

            public void setUser_Full_Nm(String User_Full_Nm) {
                this.User_Full_Nm = User_Full_Nm;
            }

            public void setUser_Bank_Acc(String User_Bank_Acc) {
                this.User_Bank_Acc = User_Bank_Acc;
            }

            public void setIsEnabled(boolean IsEnabled) {
                this.IsEnabled = IsEnabled;
            }

            public void setUser_License_ImgUrl(String User_License_ImgUrl) {
                this.User_License_ImgUrl = User_License_ImgUrl;
            }

            public void setEditDate(String EditDate) {
                this.EditDate = EditDate;
            }

            public void setUser_CarModelID(String User_CarModelID) {
                this.User_CarModelID = User_CarModelID;
            }

            public void setCode(int Code) {
                this.Code = Code;
            }

            public void setUser_Mail(String User_Mail) {
                this.User_Mail = User_Mail;
            }

            public void setUser_Code(String User_Code) {
                this.User_Code = User_Code;
            }

            public void setAdUser(String AdUser) {
                this.AdUser = AdUser;
            }

            public void setUser_NationalID_ImgUrl(String User_NationalID_ImgUrl) {
                this.User_NationalID_ImgUrl = User_NationalID_ImgUrl;
            }

            public void setMobile_Type(String Mobile_Type) {
                this.Mobile_Type = Mobile_Type;
            }

            public void setUser_CountryID(String User_CountryID) {
                this.User_CountryID = User_CountryID;
            }

            public boolean isIsDelivery() {
                return IsDelivery;
            }

            public int getUser_GenderID() {
                return User_GenderID;
            }

            public String getUser_ImgUrl() {
                return User_ImgUrl;
            }

            public boolean isIsActive() {
                return IsActive;
            }

            public int getDelivery_Status() {
                return Delivery_Status;
            }

            public String getUser_Phone() {
                return User_Phone;
            }

            public String getUser_AccessToken() {
                return User_AccessToken;
            }

            public String getUser_Address() {
                return User_Address;
            }

            public int getUser_Rate() {
                return User_Rate;
            }

            public String getUser_CarID() {
                return User_CarID;
            }

            public String getEditUser() {
                return EditUser;
            }

            public String getUser_CityID() {
                return User_CityID;
            }

            public int getUser_Count_Rate() {
                return User_Count_Rate;
            }

            public String getUser_NationalID() {
                return User_NationalID;
            }

            public String getUser_CarYearID() {
                return User_CarYearID;
            }

            public int getUserUID() {
                return UserUID;
            }

            public String getUser_BirthDate() {
                return User_BirthDate;
            }

            public String getAdDate() {
                return AdDate;
            }

            public String getUser_Fcm() {
                return User_Fcm;
            }

            public String getUser_Full_Nm() {
                return User_Full_Nm;
            }

            public String getUser_Bank_Acc() {
                return User_Bank_Acc;
            }

            public boolean isIsEnabled() {
                return IsEnabled;
            }

            public String getUser_License_ImgUrl() {
                return User_License_ImgUrl;
            }

            public String getEditDate() {
                return EditDate;
            }

            public String getUser_CarModelID() {
                return User_CarModelID;
            }

            public int getCode() {
                return Code;
            }

            public String getUser_Mail() {
                return User_Mail;
            }

            public String getUser_Code() {
                return User_Code;
            }

            public String getAdUser() {
                return AdUser;
            }

            public String getUser_NationalID_ImgUrl() {
                return User_NationalID_ImgUrl;
            }

            public String getMobile_Type() {
                return Mobile_Type;
            }

            public String getUser_CountryID() {
                return User_CountryID;
            }
        }

        public static class MessagesEntity {

            private int Order_ID;
            private String Mssge_Date;
            private int ChatMssgeUID;
            private String Type;
            private String Message;
            private String Mssge_Day_Nm;
            private int Sender_ID;
            private String Mssge_Text;
            private String Mssge_Time;
            private String Mssge_ImgUrl;
            private int ChatID;
            private boolean isPlaying;

            public boolean isPlaying() {
                return isPlaying;
            }

            public void setPlaying(boolean playing) {
                isPlaying = playing;
            }

            public void setOrder_ID(int Order_ID) {
                this.Order_ID = Order_ID;
            }

            public void setMssge_Date(String Mssge_Date) {
                this.Mssge_Date = Mssge_Date;
            }

            public void setChatMssgeUID(int ChatMssgeUID) {
                this.ChatMssgeUID = ChatMssgeUID;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public void setMessage(String Message) {
                this.Message = Message;
            }

            public void setMssge_Day_Nm(String Mssge_Day_Nm) {
                this.Mssge_Day_Nm = Mssge_Day_Nm;
            }

            public void setSender_ID(int Sender_ID) {
                this.Sender_ID = Sender_ID;
            }

            public void setMssge_Text(String Mssge_Text) {
                this.Mssge_Text = Mssge_Text;
            }

            public void setMssge_Time(String Mssge_Time) {
                this.Mssge_Time = Mssge_Time;
            }

            public void setMssge_ImgUrl(String Mssge_ImgUrl) {
                this.Mssge_ImgUrl = Mssge_ImgUrl;
            }

            public void setChatID(int ChatID) {
                this.ChatID = ChatID;
            }

            public int getOrder_ID() {
                return Order_ID;
            }

            public String getMssge_Date() {
                return Mssge_Date;
            }

            public int getChatMssgeUID() {
                return ChatMssgeUID;
            }

            public String getType() {
                return Type;
            }

            public String getMessage() {
                return Message;
            }

            public String getMssge_Day_Nm() {
                return Mssge_Day_Nm;
            }

            public int getSender_ID() {
                return Sender_ID;
            }

            public String getMssge_Text() {
                return Mssge_Text;
            }

            public String getMssge_Time() {
                return Mssge_Time;
            }

            public String getMssge_ImgUrl() {
                return Mssge_ImgUrl;
            }

            public int getChatID() {
                return ChatID;
            }
        }

        public class OrderEntity {

            private String Driver_Address;
            private double OrdCoupon_Per;
            private String Ord_Driver_Lat;
            private String Ord_Date;
            private int Client_Count_Rate;
            private String Ord_Driver_Long;
            private String Shop_Lng;
            private int Driver_Rate;
            private String Client_AccessToken;
            private String Client_Phone;
            private String Shop_Lat;
            private double Final_Amt;
            private String Ord_Coupon_Code;
            private String Duration_Ar_Nm;
            private boolean Ord_Payment_Status;
            private boolean Driver_IsDelivery;
            private String Client_Lat;
            private String Driver_Status_En_Nm;
            private String Ord_Shop_Nm;
            private int Client_Rate;
            private boolean Ord_Is_Show;
            private int OrderUID;
            private int Driver_Count_Rate;
            private boolean Driver_IsActive;
            private String Payment_En_Nm;
            private String Client_Status_En_Nm;
            private String Ord_Additional_Dta;
            private int Ord_ClientID;
            private String Driver_AccessToken;
            private int Ord_Driver_StatusID;
            private double OrdCoupon_Amt;
            private String Ord_Dates;
            private boolean Client_IsActive;
            private String Client_Lng;
            private int Ord_Client_StatusID;
            private int Ord_DurationID;
            private double Ord_Offer_App_Amt;
            private String Client_Fcm;
            private int Ord_DriverID;
            private String Driver_Phone;
            private double Ord_Items_Price;
            private String Client_Img_Url;
            private String Driver_Status_Ar_Nm;
            private String Ord_Items_Inv_Url;
            private double Ord_Offer_Amt;
            private double Ord_Offer_Vat_Amt;
            private String Duration_En_Nm;
            private String Client_Address;
            private String Ord_Shop_ImgUrl;
            private int Ord_PaymentID;
            private String Payment_Ar_Nm;
            private String Client_Full_Name;
            private String Driver_Full_Name;
            private boolean Client_IsDelivery;
            private String Ord_Dtls;
            private double Ord_Offer_Vat;
            private String Client_Status_Ar_Nm;
            private String Driver_Fcm;

            public void setDriver_Address(String Driver_Address) {
                this.Driver_Address = Driver_Address;
            }

            public void setOrdCoupon_Per(double OrdCoupon_Per) {
                this.OrdCoupon_Per = OrdCoupon_Per;
            }

            public void setOrd_Driver_Lat(String Ord_Driver_Lat) {
                this.Ord_Driver_Lat = Ord_Driver_Lat;
            }

            public void setOrd_Date(String Ord_Date) {
                this.Ord_Date = Ord_Date;
            }

            public void setClient_Count_Rate(int Client_Count_Rate) {
                this.Client_Count_Rate = Client_Count_Rate;
            }

            public void setOrd_Driver_Long(String Ord_Driver_Long) {
                this.Ord_Driver_Long = Ord_Driver_Long;
            }

            public void setShop_Lng(String Shop_Lng) {
                this.Shop_Lng = Shop_Lng;
            }

            public void setDriver_Rate(int Driver_Rate) {
                this.Driver_Rate = Driver_Rate;
            }

            public void setClient_AccessToken(String Client_AccessToken) {
                this.Client_AccessToken = Client_AccessToken;
            }

            public void setClient_Phone(String Client_Phone) {
                this.Client_Phone = Client_Phone;
            }

            public void setShop_Lat(String Shop_Lat) {
                this.Shop_Lat = Shop_Lat;
            }

            public void setFinal_Amt(double Final_Amt) {
                this.Final_Amt = Final_Amt;
            }

            public void setOrd_Coupon_Code(String Ord_Coupon_Code) {
                this.Ord_Coupon_Code = Ord_Coupon_Code;
            }

            public void setDuration_Ar_Nm(String Duration_Ar_Nm) {
                this.Duration_Ar_Nm = Duration_Ar_Nm;
            }

            public void setOrd_Payment_Status(boolean Ord_Payment_Status) {
                this.Ord_Payment_Status = Ord_Payment_Status;
            }

            public void setDriver_IsDelivery(boolean Driver_IsDelivery) {
                this.Driver_IsDelivery = Driver_IsDelivery;
            }

            public void setClient_Lat(String Client_Lat) {
                this.Client_Lat = Client_Lat;
            }

            public void setDriver_Status_En_Nm(String Driver_Status_En_Nm) {
                this.Driver_Status_En_Nm = Driver_Status_En_Nm;
            }

            public void setOrd_Shop_Nm(String Ord_Shop_Nm) {
                this.Ord_Shop_Nm = Ord_Shop_Nm;
            }

            public void setClient_Rate(int Client_Rate) {
                this.Client_Rate = Client_Rate;
            }

            public void setOrd_Is_Show(boolean Ord_Is_Show) {
                this.Ord_Is_Show = Ord_Is_Show;
            }

            public void setOrderUID(int OrderUID) {
                this.OrderUID = OrderUID;
            }

            public void setDriver_Count_Rate(int Driver_Count_Rate) {
                this.Driver_Count_Rate = Driver_Count_Rate;
            }

            public void setDriver_IsActive(boolean Driver_IsActive) {
                this.Driver_IsActive = Driver_IsActive;
            }

            public void setPayment_En_Nm(String Payment_En_Nm) {
                this.Payment_En_Nm = Payment_En_Nm;
            }

            public void setClient_Status_En_Nm(String Client_Status_En_Nm) {
                this.Client_Status_En_Nm = Client_Status_En_Nm;
            }

            public void setOrd_Additional_Dta(String Ord_Additional_Dta) {
                this.Ord_Additional_Dta = Ord_Additional_Dta;
            }

            public void setOrd_ClientID(int Ord_ClientID) {
                this.Ord_ClientID = Ord_ClientID;
            }

            public void setDriver_AccessToken(String Driver_AccessToken) {
                this.Driver_AccessToken = Driver_AccessToken;
            }

            public void setOrd_Driver_StatusID(int Ord_Driver_StatusID) {
                this.Ord_Driver_StatusID = Ord_Driver_StatusID;
            }

            public void setOrdCoupon_Amt(double OrdCoupon_Amt) {
                this.OrdCoupon_Amt = OrdCoupon_Amt;
            }

            public void setOrd_Dates(String Ord_Dates) {
                this.Ord_Dates = Ord_Dates;
            }

            public void setClient_IsActive(boolean Client_IsActive) {
                this.Client_IsActive = Client_IsActive;
            }

            public void setClient_Lng(String Client_Lng) {
                this.Client_Lng = Client_Lng;
            }

            public void setOrd_Client_StatusID(int Ord_Client_StatusID) {
                this.Ord_Client_StatusID = Ord_Client_StatusID;
            }

            public void setOrd_DurationID(int Ord_DurationID) {
                this.Ord_DurationID = Ord_DurationID;
            }

            public void setOrd_Offer_App_Amt(double Ord_Offer_App_Amt) {
                this.Ord_Offer_App_Amt = Ord_Offer_App_Amt;
            }

            public void setClient_Fcm(String Client_Fcm) {
                this.Client_Fcm = Client_Fcm;
            }

            public void setOrd_DriverID(int Ord_DriverID) {
                this.Ord_DriverID = Ord_DriverID;
            }

            public void setDriver_Phone(String Driver_Phone) {
                this.Driver_Phone = Driver_Phone;
            }

            public void setOrd_Items_Price(double Ord_Items_Price) {
                this.Ord_Items_Price = Ord_Items_Price;
            }

            public void setClient_Img_Url(String Client_Img_Url) {
                this.Client_Img_Url = Client_Img_Url;
            }

            public void setDriver_Status_Ar_Nm(String Driver_Status_Ar_Nm) {
                this.Driver_Status_Ar_Nm = Driver_Status_Ar_Nm;
            }

            public void setOrd_Items_Inv_Url(String Ord_Items_Inv_Url) {
                this.Ord_Items_Inv_Url = Ord_Items_Inv_Url;
            }

            public void setOrd_Offer_Amt(double Ord_Offer_Amt) {
                this.Ord_Offer_Amt = Ord_Offer_Amt;
            }

            public void setOrd_Offer_Vat_Amt(double Ord_Offer_Vat_Amt) {
                this.Ord_Offer_Vat_Amt = Ord_Offer_Vat_Amt;
            }

            public void setDuration_En_Nm(String Duration_En_Nm) {
                this.Duration_En_Nm = Duration_En_Nm;
            }

            public void setClient_Address(String Client_Address) {
                this.Client_Address = Client_Address;
            }

            public void setOrd_Shop_ImgUrl(String Ord_Shop_ImgUrl) {
                this.Ord_Shop_ImgUrl = Ord_Shop_ImgUrl;
            }

            public void setOrd_PaymentID(int Ord_PaymentID) {
                this.Ord_PaymentID = Ord_PaymentID;
            }

            public void setPayment_Ar_Nm(String Payment_Ar_Nm) {
                this.Payment_Ar_Nm = Payment_Ar_Nm;
            }

            public void setClient_Full_Name(String Client_Full_Name) {
                this.Client_Full_Name = Client_Full_Name;
            }

            public void setDriver_Full_Name(String Driver_Full_Name) {
                this.Driver_Full_Name = Driver_Full_Name;
            }

            public void setClient_IsDelivery(boolean Client_IsDelivery) {
                this.Client_IsDelivery = Client_IsDelivery;
            }

            public void setOrd_Dtls(String Ord_Dtls) {
                this.Ord_Dtls = Ord_Dtls;
            }

            public void setOrd_Offer_Vat(double Ord_Offer_Vat) {
                this.Ord_Offer_Vat = Ord_Offer_Vat;
            }

            public void setClient_Status_Ar_Nm(String Client_Status_Ar_Nm) {
                this.Client_Status_Ar_Nm = Client_Status_Ar_Nm;
            }

            public void setDriver_Fcm(String Driver_Fcm) {
                this.Driver_Fcm = Driver_Fcm;
            }

            public String getDriver_Address() {
                return Driver_Address;
            }

            public double getOrdCoupon_Per() {
                return OrdCoupon_Per;
            }

            public String getOrd_Driver_Lat() {
                return Ord_Driver_Lat;
            }

            public String getOrd_Date() {
                return Ord_Date;
            }

            public int getClient_Count_Rate() {
                return Client_Count_Rate;
            }

            public String getOrd_Driver_Long() {
                return Ord_Driver_Long;
            }

            public String getShop_Lng() {
                return Shop_Lng;
            }

            public int getDriver_Rate() {
                return Driver_Rate;
            }

            public String getClient_AccessToken() {
                return Client_AccessToken;
            }

            public String getClient_Phone() {
                return Client_Phone;
            }

            public String getShop_Lat() {
                return Shop_Lat;
            }

            public double getFinal_Amt() {
                return Final_Amt;
            }

            public String getOrd_Coupon_Code() {
                return Ord_Coupon_Code;
            }

            public String getDuration_Ar_Nm() {
                return Duration_Ar_Nm;
            }

            public boolean isOrd_Payment_Status() {
                return Ord_Payment_Status;
            }

            public boolean isDriver_IsDelivery() {
                return Driver_IsDelivery;
            }

            public String getClient_Lat() {
                return Client_Lat;
            }

            public String getDriver_Status_En_Nm() {
                return Driver_Status_En_Nm;
            }

            public String getOrd_Shop_Nm() {
                return Ord_Shop_Nm;
            }

            public int getClient_Rate() {
                return Client_Rate;
            }

            public boolean isOrd_Is_Show() {
                return Ord_Is_Show;
            }

            public int getOrderUID() {
                return OrderUID;
            }

            public int getDriver_Count_Rate() {
                return Driver_Count_Rate;
            }

            public boolean isDriver_IsActive() {
                return Driver_IsActive;
            }

            public String getPayment_En_Nm() {
                return Payment_En_Nm;
            }

            public String getClient_Status_En_Nm() {
                return Client_Status_En_Nm;
            }

            public String getOrd_Additional_Dta() {
                return Ord_Additional_Dta;
            }

            public int getOrd_ClientID() {
                return Ord_ClientID;
            }

            public String getDriver_AccessToken() {
                return Driver_AccessToken;
            }

            public int getOrd_Driver_StatusID() {
                return Ord_Driver_StatusID;
            }

            public double getOrdCoupon_Amt() {
                return OrdCoupon_Amt;
            }

            public String getOrd_Dates() {
                return Ord_Dates;
            }

            public boolean isClient_IsActive() {
                return Client_IsActive;
            }

            public String getClient_Lng() {
                return Client_Lng;
            }

            public int getOrd_Client_StatusID() {
                return Ord_Client_StatusID;
            }

            public int getOrd_DurationID() {
                return Ord_DurationID;
            }

            public double getOrd_Offer_App_Amt() {
                return Ord_Offer_App_Amt;
            }

            public String getClient_Fcm() {
                return Client_Fcm;
            }

            public int getOrd_DriverID() {
                return Ord_DriverID;
            }

            public String getDriver_Phone() {
                return Driver_Phone;
            }

            public double getOrd_Items_Price() {
                return Ord_Items_Price;
            }

            public String getClient_Img_Url() {
                return Client_Img_Url;
            }

            public String getDriver_Status_Ar_Nm() {
                return Driver_Status_Ar_Nm;
            }

            public String getOrd_Items_Inv_Url() {
                return Ord_Items_Inv_Url;
            }

            public double getOrd_Offer_Amt() {
                return Ord_Offer_Amt;
            }

            public double getOrd_Offer_Vat_Amt() {
                return Ord_Offer_Vat_Amt;
            }

            public String getDuration_En_Nm() {
                return Duration_En_Nm;
            }

            public String getClient_Address() {
                return Client_Address;
            }

            public String getOrd_Shop_ImgUrl() {
                return Ord_Shop_ImgUrl;
            }

            public int getOrd_PaymentID() {
                return Ord_PaymentID;
            }

            public String getPayment_Ar_Nm() {
                return Payment_Ar_Nm;
            }

            public String getClient_Full_Name() {
                return Client_Full_Name;
            }

            public String getDriver_Full_Name() {
                return Driver_Full_Name;
            }

            public boolean isClient_IsDelivery() {
                return Client_IsDelivery;
            }

            public String getOrd_Dtls() {
                return Ord_Dtls;
            }

            public double getOrd_Offer_Vat() {
                return Ord_Offer_Vat;
            }

            public String getClient_Status_Ar_Nm() {
                return Client_Status_Ar_Nm;
            }

            public String getDriver_Fcm() {
                return Driver_Fcm;
            }
        }
    }
}