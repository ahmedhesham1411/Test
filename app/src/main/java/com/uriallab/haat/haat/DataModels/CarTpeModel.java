package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class CarTpeModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private List<TypesEntity> types;

        public void setTypes(List<TypesEntity> types) {
            this.types = types;
        }

        public List<TypesEntity> getTypes() {
            return types;
        }

        public class TypesEntity {

            private int CareUID;
            private String Care_Type;

            public void setCareUID(int CareUID) {
                this.CareUID = CareUID;
            }

            public void setCare_Ar_Nm(String Care_Ar_Nm) {
                this.Care_Type = Care_Ar_Nm;
            }

            public int getCareUID() {
                return CareUID;
            }

            public String getCare_Ar_Nm() {
                return Care_Type;
            }
        }
    }
}