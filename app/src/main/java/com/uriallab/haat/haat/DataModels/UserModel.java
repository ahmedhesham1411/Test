package com.uriallab.haat.haat.DataModels;

public class UserModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private UserDataEntity userData;
        private int allorders;

        public void setUserData(UserDataEntity userData) {
            this.userData = userData;
        }

        public void setAllorders(int allorders) {
            this.allorders = allorders;
        }

        public UserDataEntity getUserData() {
            return userData;
        }

        public int getAllorders() {
            return allorders;
        }

        public class UserDataEntity {

            private boolean IsDelivery;
            private int User_GenderID;
            private double User_Current_long;
            private String User_ImgUrl;
            private boolean IsActive;
            private int Delivery_Status;
            private String User_Phone;
            private String User_AccessToken;
            private String User_Address;
            private int User_Rate;
            private int User_CarID;
            private String EditUser;
            private int User_CityID;
            private String User_License_Number;
            private int User_Count_Rate;
            private String User_NationalID;
            private String User_STCPay_Acc;
            private int User_CarYearID;
            private int UserUID;
            private String User_BirthDate;
            private String AdDate;
            private String User_Fcm;
            private String User_Full_Nm;
            private String User_Bank_Acc;
            private boolean IsEnabled;
            private String User_License_ImgUrl;
            private String EditDate;
            private int User_CarModelID;
            private int Code;
            private double User_Current_lat;
            private String User_Mail;
            private String User_Code;
            private String AdUser;
            private String Mobile_Type;
            private String User_NationalID_ImgUrl;
            private int User_CountryID;

            public void setIsDelivery(boolean IsDelivery) {
                this.IsDelivery = IsDelivery;
            }

            public void setUser_GenderID(int User_GenderID) {
                this.User_GenderID = User_GenderID;
            }

            public void setUser_Current_long(double User_Current_long) {
                this.User_Current_long = User_Current_long;
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

            public void setUser_CarID(int User_CarID) {
                this.User_CarID = User_CarID;
            }

            public void setEditUser(String EditUser) {
                this.EditUser = EditUser;
            }

            public void setUser_CityID(int User_CityID) {
                this.User_CityID = User_CityID;
            }

            public void setUser_License_Number(String User_License_Number) {
                this.User_License_Number = User_License_Number;
            }

            public void setUser_Count_Rate(int User_Count_Rate) {
                this.User_Count_Rate = User_Count_Rate;
            }

            public void setUser_NationalID(String User_NationalID) {
                this.User_NationalID = User_NationalID;
            }

            public void setUser_STCPay_Acc(String User_STCPay_Acc) {
                this.User_STCPay_Acc = User_STCPay_Acc;
            }

            public void setUser_CarYearID(int User_CarYearID) {
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

            public void setUser_CarModelID(int User_CarModelID) {
                this.User_CarModelID = User_CarModelID;
            }

            public void setCode(int Code) {
                this.Code = Code;
            }

            public void setUser_Current_lat(double User_Current_lat) {
                this.User_Current_lat = User_Current_lat;
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

            public void setMobile_Type(String Mobile_Type) {
                this.Mobile_Type = Mobile_Type;
            }

            public void setUser_NationalID_ImgUrl(String User_NationalID_ImgUrl) {
                this.User_NationalID_ImgUrl = User_NationalID_ImgUrl;
            }

            public void setUser_CountryID(int User_CountryID) {
                this.User_CountryID = User_CountryID;
            }

            public boolean isIsDelivery() {
                return IsDelivery;
            }

            public int getUser_GenderID() {
                return User_GenderID;
            }

            public double getUser_Current_long() {
                return User_Current_long;
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

            public int getUser_CarID() {
                return User_CarID;
            }

            public String getEditUser() {
                return EditUser;
            }

            public int getUser_CityID() {
                return User_CityID;
            }

            public String getUser_License_Number() {
                return User_License_Number;
            }

            public int getUser_Count_Rate() {
                return User_Count_Rate;
            }

            public String getUser_NationalID() {
                return User_NationalID;
            }

            public String getUser_STCPay_Acc() {
                return User_STCPay_Acc;
            }

            public int getUser_CarYearID() {
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

            public int getUser_CarModelID() {
                return User_CarModelID;
            }

            public int getCode() {
                return Code;
            }

            public double getUser_Current_lat() {
                return User_Current_lat;
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

            public String getMobile_Type() {
                return Mobile_Type;
            }

            public String getUser_NationalID_ImgUrl() {
                return User_NationalID_ImgUrl;
            }

            public int getUser_CountryID() {
                return User_CountryID;
            }
        }
    }
}