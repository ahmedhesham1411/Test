package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class TimeModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DurationsBean> durations;

        public List<DurationsBean> getDurations() {
            return durations;
        }

        public void setDurations(List<DurationsBean> durations) {
            this.durations = durations;
        }

        public static class DurationsBean {

            private int DurationUID;
            private String Duration_Nm;

            public int getDurationUID() {
                return DurationUID;
            }

            public void setDurationUID(int DurationUID) {
                this.DurationUID = DurationUID;
            }

            public String getDuration_Nm() {
                return Duration_Nm;
            }

            public void setDuration_Nm(String Duration_Nm) {
                this.Duration_Nm = Duration_Nm;
            }
        }
    }
}