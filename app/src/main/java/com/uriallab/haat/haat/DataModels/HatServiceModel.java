package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class HatServiceModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private List<HaatCardCategoryEntity> HaatCardCategory;

        public void setHaatCardCategory(List<HaatCardCategoryEntity> HaatCardCategory) {
            this.HaatCardCategory = HaatCardCategory;
        }

        public List<HaatCardCategoryEntity> getHaatCardCategory() {
            return HaatCardCategory;
        }

        public class HaatCardCategoryEntity {

            private List<CardsEntity> Cards;
            private int Haat_Card_Category_UID;
            private String Category_Title;

            public void setCards(List<CardsEntity> Cards) {
                this.Cards = Cards;
            }

            public void setHaat_Card_Category_UID(int Haat_Card_Category_UID) {
                this.Haat_Card_Category_UID = Haat_Card_Category_UID;
            }

            public void setCategory_Title(String Category_Title) {
                this.Category_Title = Category_Title;
            }

            public List<CardsEntity> getCards() {
                return Cards;
            }

            public int getHaat_Card_Category_UID() {
                return Haat_Card_Category_UID;
            }

            public String getCategory_Title() {
                return Category_Title;
            }

            public class CardsEntity {

                private int Card_UID;
                private double Price;
                private String Url;
                private String Name;

                public int getCard_UID() {
                    return Card_UID;
                }

                public void setCard_UID(int card_UID) {
                    Card_UID = card_UID;
                }

                public void setPrice(double Price) {
                    this.Price = Price;
                }

                public void setUrl(String Url) {
                    this.Url = Url;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public double getPrice() {
                    return Price;
                }

                public String getUrl() {
                    return Url;
                }

                public String getName() {
                    return Name;
                }
            }
        }
    }
}