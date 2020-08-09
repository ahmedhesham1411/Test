package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class CommentsModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<CommentsBean> comments;

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {

            private int CommentUID;
            private int Rate_From_UserID;
            private int Rate_To_UserID;
            private int Comment_Rate;
            private String Comment_Desc;
            private String Comment_Date;
            private String From_Full_Name;
            private String From_Img_Url;
            private String From_Phone;
            private String To_Full_Name;
            private String To_Img_Url;
            private String To_Phone;

            public int getCommentUID() {
                return CommentUID;
            }

            public void setCommentUID(int CommentUID) {
                this.CommentUID = CommentUID;
            }

            public int getRate_From_UserID() {
                return Rate_From_UserID;
            }

            public void setRate_From_UserID(int Rate_From_UserID) {
                this.Rate_From_UserID = Rate_From_UserID;
            }

            public int getRate_To_UserID() {
                return Rate_To_UserID;
            }

            public void setRate_To_UserID(int Rate_To_UserID) {
                this.Rate_To_UserID = Rate_To_UserID;
            }

            public int getComment_Rate() {
                return Comment_Rate;
            }

            public void setComment_Rate(int Comment_Rate) {
                this.Comment_Rate = Comment_Rate;
            }

            public String getComment_Desc() {
                return Comment_Desc;
            }

            public void setComment_Desc(String Comment_Desc) {
                this.Comment_Desc = Comment_Desc;
            }

            public String getComment_Date() {
                return Comment_Date;
            }

            public void setComment_Date(String Comment_Date) {
                this.Comment_Date = Comment_Date;
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
        }
    }
}