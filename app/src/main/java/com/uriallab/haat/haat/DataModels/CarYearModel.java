package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class CarYearModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private List<YearsEntity> years;

        public void setYears(List<YearsEntity> years) {
            this.years = years;
        }

        public List<YearsEntity> getYears() {
            return years;
        }

        public class YearsEntity {

            private String CareYear;

            public String getCareYear() {
                return CareYear;
            }

            public void setCareYear(String careYear) {
                CareYear = careYear;
            }
        }
    }
}