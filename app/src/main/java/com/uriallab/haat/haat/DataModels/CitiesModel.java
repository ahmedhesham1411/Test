package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class CitiesModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private List<CitiesEntity> cities;

        public void setCities(List<CitiesEntity> cities) {
            this.cities = cities;
        }

        public List<CitiesEntity> getCities() {
            return cities;
        }

        public class CitiesEntity {

            private String City_Name;
            private int CityUID;

            public void setCity_Ar_Nm(String City_Ar_Nm) {
                this.City_Name = City_Ar_Nm;
            }

            public void setCityUID(int CityUID) {
                this.CityUID = CityUID;
            }

            public String getCity_Ar_Nm() {
                return City_Name;
            }

            public int getCityUID() {
                return CityUID;
            }
        }
    }
}