package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class NotificationsModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<NotficationsBean> notfications;
        private int EndPage;
        private int NextPage;

        public int getNextPage() {
            return NextPage;
        }

        public void setNextPage(int nextPage) {
            NextPage = nextPage;
        }

        public int getEndPage() {
            return EndPage;
        }

        public void setEndPage(int endPage) {
            EndPage = endPage;
        }

        public List<NotficationsBean> getNotfications() {
            return notfications;
        }

        public void setNotfications(List<NotficationsBean> notfications) {
            this.notfications = notfications;
        }

        public static class NotficationsBean {

            private int NoticUID;
            private String Notic_Type;
            private int ClientID;
            private String OrderID;
            private String Notic_Title;
            private String Notic_Mssge;
            private String Notic_Date;
            private String Notic_Time;

            public int getNoticUID() {
                return NoticUID;
            }

            public void setNoticUID(int NoticUID) {
                this.NoticUID = NoticUID;
            }

            public String getNotic_Type() {
                return Notic_Type;
            }

            public void setNotic_Type(String Notic_Type) {
                this.Notic_Type = Notic_Type;
            }

            public int getClientID() {
                return ClientID;
            }

            public void setClientID(int ClientID) {
                this.ClientID = ClientID;
            }

            public String getOrderID() {
                return OrderID;
            }

            public void setOrderID(String OrderID) {
                this.OrderID = OrderID;
            }

            public String getNotic_Title() {
                return Notic_Title;
            }

            public void setNotic_Title(String Notic_Title) {
                this.Notic_Title = Notic_Title;
            }

            public String getNotic_Mssge() {
                return Notic_Mssge;
            }

            public void setNotic_Mssge(String Notic_Mssge) {
                this.Notic_Mssge = Notic_Mssge;
            }

            public String getNotic_Date() {
                return Notic_Date;
            }

            public void setNotic_Date(String Notic_Date) {
                this.Notic_Date = Notic_Date;
            }

            public String getNotic_Time() {
                return Notic_Time;
            }

            public void setNotic_Time(String Notic_Time) {
                this.Notic_Time = Notic_Time;
            }
        }
    }
}