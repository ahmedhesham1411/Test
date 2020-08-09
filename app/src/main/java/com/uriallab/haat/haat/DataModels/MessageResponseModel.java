package com.uriallab.haat.haat.DataModels;

public class MessageResponseModel {

    private ResultEntity result;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public class ResultEntity {

        private String Message;

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getMessage() {
            return Message;
        }
    }
}