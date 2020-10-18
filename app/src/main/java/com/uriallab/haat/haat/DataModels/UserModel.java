package com.uriallab.haat.haat.DataModels;

public class UserModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private UserDataBean userData;
        private int allorders;

        public UserDataBean getUserData() {
            return userData;
        }

        public void setUserData(UserDataBean userData) {
            this.userData = userData;
        }

        public int getAllorders() {
            return allorders;
        }

        public void setAllorders(int allorders) {
            this.allorders = allorders;
        }

        public static class UserDataBean {

            private int UserUID;
            private String User_Full_Nm;
            private String User_Phone;
            private String Mobile_Type;
            private int User_GenderID;
            private String User_BirthDate;
            private String User_Mail;
            private String User_CountryID;
            private String User_CityID;
            private String User_RegionID;
            private String User_Address;
            private String User_Bank_Acc;
            private String User_STCPay_Acc;
            private String User_CarID;
            private String User_CarTypeID;
            private String User_CarYearID;
            private String User_AccessToken;
            private String User_Fcm;
            private String User_Code;
            private int User_Rate;
            private int User_Count_Rate;
            private int Delivery_Status;
            private boolean IsDelivery;
            private boolean IsActive;
            private boolean IsEnabled;
            private String AdUser;
            private String AdDate;
            private String EditUser;
            private String EditDate;
            private String Code;
            private String User_Current_long;
            private String User_Current_lat;
            private String User_License_Number;
            private String User_NationalID_Type;
            private String User_NationalID;
            private String User_License_ImgUrl;
            private String User_NationalID_ImgUrl;
            private String User_ImgUrl;

            public int getUserUID() {
                return UserUID;
            }

            public void setUserUID(int UserUID) {
                this.UserUID = UserUID;
            }

            public String getUser_Full_Nm() {
                return User_Full_Nm;
            }

            public void setUser_Full_Nm(String User_Full_Nm) {
                this.User_Full_Nm = User_Full_Nm;
            }

            public String getUser_Phone() {
                return User_Phone;
            }

            public void setUser_Phone(String User_Phone) {
                this.User_Phone = User_Phone;
            }

            public String getMobile_Type() {
                return Mobile_Type;
            }

            public void setMobile_Type(String Mobile_Type) {
                this.Mobile_Type = Mobile_Type;
            }

            public int getUser_GenderID() {
                return User_GenderID;
            }

            public void setUser_GenderID(int User_GenderID) {
                this.User_GenderID = User_GenderID;
            }

            public String getUser_BirthDate() {
                return User_BirthDate;
            }

            public void setUser_BirthDate(String User_BirthDate) {
                this.User_BirthDate = User_BirthDate;
            }

            public String getUser_Mail() {
                return User_Mail;
            }

            public void setUser_Mail(String User_Mail) {
                this.User_Mail = User_Mail;
            }

            public String getUser_CountryID() {
                return User_CountryID;
            }

            public void setUser_CountryID(String User_CountryID) {
                this.User_CountryID = User_CountryID;
            }

            public String getUser_CityID() {
                return User_CityID;
            }

            public void setUser_CityID(String User_CityID) {
                this.User_CityID = User_CityID;
            }

            public String getUser_RegionID() {
                return User_RegionID;
            }

            public void setUser_RegionID(String User_RegionID) {
                this.User_RegionID = User_RegionID;
            }

            public String getUser_Address() {
                return User_Address;
            }

            public void setUser_Address(String User_Address) {
                this.User_Address = User_Address;
            }

            public String getUser_Bank_Acc() {
                return User_Bank_Acc;
            }

            public void setUser_Bank_Acc(String User_Bank_Acc) {
                this.User_Bank_Acc = User_Bank_Acc;
            }

            public String getUser_STCPay_Acc() {
                return User_STCPay_Acc;
            }

            public void setUser_STCPay_Acc(String User_STCPay_Acc) {
                this.User_STCPay_Acc = User_STCPay_Acc;
            }

            public String getUser_CarID() {
                return User_CarID;
            }

            public void setUser_CarID(String User_CarID) {
                this.User_CarID = User_CarID;
            }

            public String getUser_CarTypeID() {
                return User_CarTypeID;
            }

            public void setUser_CarTypeID(String User_CarTypeID) {
                this.User_CarTypeID = User_CarTypeID;
            }

            public String getUser_CarYearID() {
                return User_CarYearID;
            }

            public void setUser_CarYearID(String User_CarYearID) {
                this.User_CarYearID = User_CarYearID;
            }

            public String getUser_AccessToken() {
                return User_AccessToken;
            }

            public void setUser_AccessToken(String User_AccessToken) {
                this.User_AccessToken = User_AccessToken;
            }

            public String getUser_Fcm() {
                return User_Fcm;
            }

            public void setUser_Fcm(String User_Fcm) {
                this.User_Fcm = User_Fcm;
            }

            public String getUser_Code() {
                return User_Code;
            }

            public void setUser_Code(String User_Code) {
                this.User_Code = User_Code;
            }

            public int getUser_Rate() {
                return User_Rate;
            }

            public void setUser_Rate(int User_Rate) {
                this.User_Rate = User_Rate;
            }

            public int getUser_Count_Rate() {
                return User_Count_Rate;
            }

            public void setUser_Count_Rate(int User_Count_Rate) {
                this.User_Count_Rate = User_Count_Rate;
            }

            public int getDelivery_Status() {
                return Delivery_Status;
            }

            public void setDelivery_Status(int Delivery_Status) {
                this.Delivery_Status = Delivery_Status;
            }

            public boolean isIsDelivery() {
                return IsDelivery;
            }

            public void setIsDelivery(boolean IsDelivery) {
                this.IsDelivery = IsDelivery;
            }

            public boolean isIsActive() {
                return IsActive;
            }

            public void setIsActive(boolean IsActive) {
                this.IsActive = IsActive;
            }

            public boolean isIsEnabled() {
                return IsEnabled;
            }

            public void setIsEnabled(boolean IsEnabled) {
                this.IsEnabled = IsEnabled;
            }

            public String getAdUser() {
                return AdUser;
            }

            public void setAdUser(String AdUser) {
                this.AdUser = AdUser;
            }

            public String getAdDate() {
                return AdDate;
            }

            public void setAdDate(String AdDate) {
                this.AdDate = AdDate;
            }

            public String getEditUser() {
                return EditUser;
            }

            public void setEditUser(String EditUser) {
                this.EditUser = EditUser;
            }

            public String getEditDate() {
                return EditDate;
            }

            public void setEditDate(String EditDate) {
                this.EditDate = EditDate;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getUser_Current_long() {
                return User_Current_long;
            }

            public void setUser_Current_long(String User_Current_long) {
                this.User_Current_long = User_Current_long;
            }

            public String getUser_Current_lat() {
                return User_Current_lat;
            }

            public void setUser_Current_lat(String User_Current_lat) {
                this.User_Current_lat = User_Current_lat;
            }

            public String getUser_License_Number() {
                return User_License_Number;
            }

            public void setUser_License_Number(String User_License_Number) {
                this.User_License_Number = User_License_Number;
            }

            public String getUser_NationalID_Type() {
                return User_NationalID_Type;
            }

            public void setUser_NationalID_Type(String User_NationalID_Type) {
                this.User_NationalID_Type = User_NationalID_Type;
            }

            public String getUser_NationalID() {
                return User_NationalID;
            }

            public void setUser_NationalID(String User_NationalID) {
                this.User_NationalID = User_NationalID;
            }

            public String getUser_License_ImgUrl() {
                return User_License_ImgUrl;
            }

            public void setUser_License_ImgUrl(String User_License_ImgUrl) {
                this.User_License_ImgUrl = User_License_ImgUrl;
            }

            public String getUser_NationalID_ImgUrl() {
                return User_NationalID_ImgUrl;
            }

            public void setUser_NationalID_ImgUrl(String User_NationalID_ImgUrl) {
                this.User_NationalID_ImgUrl = User_NationalID_ImgUrl;
            }

            public String getUser_ImgUrl() {
                return User_ImgUrl;
            }

            public void setUser_ImgUrl(String User_ImgUrl) {
                this.User_ImgUrl = User_ImgUrl;
            }
        }
    }
}