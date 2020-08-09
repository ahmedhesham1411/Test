package com.uriallab.haat.haat.Utilities;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EchoWebSocketListener extends WebSocketListener {
    @Override public void onOpen(WebSocket webSocket, Response response) {
        // called when the connection is opened
        Log.e("Socket_Connected", "connected");
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
        //...
    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
        //...
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason){
        //...
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response){
        //...
    }
}