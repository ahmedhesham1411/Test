package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class FavModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<FavoritelocationsBean> favoritelocations;

        public List<FavoritelocationsBean> getFavoritelocations() {
            return favoritelocations;
        }

        public void setFavoritelocations(List<FavoritelocationsBean> favoritelocations) {
            this.favoritelocations = favoritelocations;
        }

        public static class FavoritelocationsBean {

            private int LocationUID;
            private int User_ID;
            private String Favorite_Nm;
            private String Location_Nm;
            private String Location_Lat;
            private String Location_Lng;

            public int getLocationUID() {
                return LocationUID;
            }

            public void setLocationUID(int LocationUID) {
                this.LocationUID = LocationUID;
            }

            public int getUser_ID() {
                return User_ID;
            }

            public void setUser_ID(int User_ID) {
                this.User_ID = User_ID;
            }

            public String getFavorite_Nm() {
                return Favorite_Nm;
            }

            public void setFavorite_Nm(String Favorite_Nm) {
                this.Favorite_Nm = Favorite_Nm;
            }

            public String getLocation_Nm() {
                return Location_Nm;
            }

            public void setLocation_Nm(String Location_Nm) {
                this.Location_Nm = Location_Nm;
            }

            public String getLocation_Lat() {
                return Location_Lat;
            }

            public void setLocation_Lat(String Location_Lat) {
                this.Location_Lat = Location_Lat;
            }

            public String getLocation_Lng() {
                return Location_Lng;
            }

            public void setLocation_Lng(String Location_Lng) {
                this.Location_Lng = Location_Lng;
            }
        }
    }
}