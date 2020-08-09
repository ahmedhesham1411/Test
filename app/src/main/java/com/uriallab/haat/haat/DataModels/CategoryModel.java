package com.uriallab.haat.haat.DataModels;

import java.util.List;

/**
 * Created by Mahmoud on 4/22/2020.
 */

public class CategoryModel {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<CategoryBean> category;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {

            private int CategoryUID;
            private String Category_Title_AR;
            private String Category_Type;
            private String Category_Icon_Urls;
            private String Category_Title_EN;
            private boolean selected;

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public int getCategoryUID() {
                return CategoryUID;
            }

            public void setCategoryUID(int CategoryUID) {
                this.CategoryUID = CategoryUID;
            }

            public String getCategory_Title_AR() {
                return Category_Title_AR;
            }

            public void setCategory_Title_AR(String Category_Title_AR) {
                this.Category_Title_AR = Category_Title_AR;
            }

            public String getCategory_Type() {
                return Category_Type;
            }

            public void setCategory_Type(String Category_Type) {
                this.Category_Type = Category_Type;
            }

            public String getCategory_Icon_Url() {
                return Category_Icon_Urls;
            }

            public void setCategory_Icon_Url(String Category_Icon_Url) {
                this.Category_Icon_Urls = Category_Icon_Url;
            }

            public String getCategory_Title_EN() {
                return Category_Title_EN;
            }

            public void setCategory_Title_EN(String Category_Title_EN) {
                this.Category_Title_EN = Category_Title_EN;
            }
        }
    }
}