package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class ComplaintModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ComplaintsBean> complaints;

        public List<ComplaintsBean> getComplaints() {
            return complaints;
        }

        public void setComplaints(List<ComplaintsBean> complaints) {
            this.complaints = complaints;
        }

        public static class ComplaintsBean {

            private int ComplaintUID;
            private String From_Full_Name;
            private String From_Img_Url;
            private String From_Phone;
            private String From_Address;
            private String To_Full_Name;
            private String To_Img_Url;
            private String To_Phone;
            private String To_Address;
            private int Complaint_Reason_ID;
            private String Reason_Ar_Nm;
            private String Reason_En_Nm;
            private int OrderID;
            private int User_From_ID;
            private int User_Feadback_Status;
            private int User_To_ID;
            private String App_Feadback;
            private String Complaint_ImgUrl;
            private String Complaint_Desc;
            private int Complaint_Status_ID;
            private String Complaint_Date;
            private String Feadback_Status_Ar_Nm;
            private String Feadback_Status_En_Nm;
            private String Complaint_Status_Ar_Nm;
            private String Complaint_Status_En_Nm;
            private String Ord_Shop_Nm;
            private String Ord_Shop_ImgUrl;
            private String Ord_Dtls;

            public int getComplaintUID() {
                return ComplaintUID;
            }

            public void setComplaintUID(int ComplaintUID) {
                this.ComplaintUID = ComplaintUID;
            }

            public String getFrom_Full_Name() {
                return From_Full_Name;
            }

            public void setFrom_Full_Name(String From_Full_Name) {
                this.From_Full_Name = From_Full_Name;
            }

            public String getFrom_Img_Url() {
                return From_Img_Url;
            }

            public void setFrom_Img_Url(String From_Img_Url) {
                this.From_Img_Url = From_Img_Url;
            }

            public String getFrom_Phone() {
                return From_Phone;
            }

            public void setFrom_Phone(String From_Phone) {
                this.From_Phone = From_Phone;
            }

            public String getFrom_Address() {
                return From_Address;
            }

            public void setFrom_Address(String From_Address) {
                this.From_Address = From_Address;
            }

            public String getTo_Full_Name() {
                return To_Full_Name;
            }

            public void setTo_Full_Name(String To_Full_Name) {
                this.To_Full_Name = To_Full_Name;
            }

            public String getTo_Img_Url() {
                return To_Img_Url;
            }

            public void setTo_Img_Url(String To_Img_Url) {
                this.To_Img_Url = To_Img_Url;
            }

            public String getTo_Phone() {
                return To_Phone;
            }

            public void setTo_Phone(String To_Phone) {
                this.To_Phone = To_Phone;
            }

            public String getTo_Address() {
                return To_Address;
            }

            public void setTo_Address(String To_Address) {
                this.To_Address = To_Address;
            }

            public int getComplaint_Reason_ID() {
                return Complaint_Reason_ID;
            }

            public void setComplaint_Reason_ID(int Complaint_Reason_ID) {
                this.Complaint_Reason_ID = Complaint_Reason_ID;
            }

            public String getReason_Ar_Nm() {
                return Reason_Ar_Nm;
            }

            public void setReason_Ar_Nm(String Reason_Ar_Nm) {
                this.Reason_Ar_Nm = Reason_Ar_Nm;
            }

            public String getReason_En_Nm() {
                return Reason_En_Nm;
            }

            public void setReason_En_Nm(String Reason_En_Nm) {
                this.Reason_En_Nm = Reason_En_Nm;
            }

            public int getOrderID() {
                return OrderID;
            }

            public void setOrderID(int OrderID) {
                this.OrderID = OrderID;
            }

            public int getUser_From_ID() {
                return User_From_ID;
            }

            public void setUser_From_ID(int User_From_ID) {
                this.User_From_ID = User_From_ID;
            }

            public int getUser_Feadback_Status() {
                return User_Feadback_Status;
            }

            public void setUser_Feadback_Status(int User_Feadback_Status) {
                this.User_Feadback_Status = User_Feadback_Status;
            }

            public int getUser_To_ID() {
                return User_To_ID;
            }

            public void setUser_To_ID(int User_To_ID) {
                this.User_To_ID = User_To_ID;
            }

            public String getApp_Feadback() {
                return App_Feadback;
            }

            public void setApp_Feadback(String App_Feadback) {
                this.App_Feadback = App_Feadback;
            }

            public String getComplaint_ImgUrl() {
                return Complaint_ImgUrl;
            }

            public void setComplaint_ImgUrl(String Complaint_ImgUrl) {
                this.Complaint_ImgUrl = Complaint_ImgUrl;
            }

            public String getComplaint_Desc() {
                return Complaint_Desc;
            }

            public void setComplaint_Desc(String Complaint_Desc) {
                this.Complaint_Desc = Complaint_Desc;
            }

            public int getComplaint_Status_ID() {
                return Complaint_Status_ID;
            }

            public void setComplaint_Status_ID(int Complaint_Status_ID) {
                this.Complaint_Status_ID = Complaint_Status_ID;
            }

            public String getComplaint_Date() {
                return Complaint_Date;
            }

            public void setComplaint_Date(String Complaint_Date) {
                this.Complaint_Date = Complaint_Date;
            }

            public String getFeadback_Status_Ar_Nm() {
                return Feadback_Status_Ar_Nm;
            }

            public void setFeadback_Status_Ar_Nm(String Feadback_Status_Ar_Nm) {
                this.Feadback_Status_Ar_Nm = Feadback_Status_Ar_Nm;
            }

            public String getFeadback_Status_En_Nm() {
                return Feadback_Status_En_Nm;
            }

            public void setFeadback_Status_En_Nm(String Feadback_Status_En_Nm) {
                this.Feadback_Status_En_Nm = Feadback_Status_En_Nm;
            }

            public String getComplaint_Status_Ar_Nm() {
                return Complaint_Status_Ar_Nm;
            }

            public void setComplaint_Status_Ar_Nm(String Complaint_Status_Ar_Nm) {
                this.Complaint_Status_Ar_Nm = Complaint_Status_Ar_Nm;
            }

            public String getComplaint_Status_En_Nm() {
                return Complaint_Status_En_Nm;
            }

            public void setComplaint_Status_En_Nm(String Complaint_Status_En_Nm) {
                this.Complaint_Status_En_Nm = Complaint_Status_En_Nm;
            }

            public String getOrd_Shop_Nm() {
                return Ord_Shop_Nm;
            }

            public void setOrd_Shop_Nm(String Ord_Shop_Nm) {
                this.Ord_Shop_Nm = Ord_Shop_Nm;
            }

            public String getOrd_Shop_ImgUrl() {
                return Ord_Shop_ImgUrl;
            }

            public void setOrd_Shop_ImgUrl(String Ord_Shop_ImgUrl) {
                this.Ord_Shop_ImgUrl = Ord_Shop_ImgUrl;
            }

            public String getOrd_Dtls() {
                return Ord_Dtls;
            }

            public void setOrd_Dtls(String Ord_Dtls) {
                this.Ord_Dtls = Ord_Dtls;
            }
        }
    }
}