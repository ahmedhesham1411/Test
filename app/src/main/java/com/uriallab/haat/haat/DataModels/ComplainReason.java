package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class ComplainReason {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ComplaintreasonBean> complaintreason;

        public List<ComplaintreasonBean> getComplaintreason() {
            return complaintreason;
        }

        public void setComplaintreason(List<ComplaintreasonBean> complaintreason) {
            this.complaintreason = complaintreason;
        }

        public static class ComplaintreasonBean {

            private int ComplaintReasonUID;
            private String Reason_Nm;

            public int getComplaintReasonUID() {
                return ComplaintReasonUID;
            }

            public void setComplaintReasonUID(int ComplaintReasonUID) {
                this.ComplaintReasonUID = ComplaintReasonUID;
            }

            public String getReason_Nm() {
                return Reason_Nm;
            }

            public void setReason_Nm(String Reason_Nm) {
                this.Reason_Nm = Reason_Nm;
            }
        }
    }
}