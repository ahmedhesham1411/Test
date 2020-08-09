package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class NationalityModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private List<CountryEntity> country;

        public void setCountry(List<CountryEntity> country) {
            this.country = country;
        }

        public List<CountryEntity> getCountry() {
            return country;
        }

        public class CountryEntity {

            private String Country_Name;
            private int CountryUID;

            public void setCountry_Ar_Nm(String Country_Ar_Nm) {
                this.Country_Name = Country_Ar_Nm;
            }

            public void setCountryUID(int CountryUID) {
                this.CountryUID = CountryUID;
            }

            public String getCountry_Ar_Nm() {
                return Country_Name;
            }

            public int getCountryUID() {
                return CountryUID;
            }
        }
    }
}