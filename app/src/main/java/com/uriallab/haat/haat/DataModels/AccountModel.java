package com.uriallab.haat.haat.DataModels;

public class AccountModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private AccountInfoEntity accountInfo;

        public void setAccountInfo(AccountInfoEntity accountInfo) {
            this.accountInfo = accountInfo;
        }

        public AccountInfoEntity getAccountInfo() {
            return accountInfo;
        }

        public class AccountInfoEntity {

            private int orderscount;
            private double myprofits;
            private double cridetamt;
            private double debitamt;

            public void setOrderscount(int orderscount) {
                this.orderscount = orderscount;
            }

            public void setMyprofits(double myprofits) {
                this.myprofits = myprofits;
            }

            public void setCridetamt(double cridetamt) {
                this.cridetamt = cridetamt;
            }

            public void setDebitamt(double debitamt) {
                this.debitamt = debitamt;
            }

            public int getOrderscount() {
                return orderscount;
            }

            public double getMyprofits() {
                return myprofits;
            }

            public double getCridetamt() {
                return cridetamt;
            }

            public double getDebitamt() {
                return debitamt;
            }
        }
    }
}