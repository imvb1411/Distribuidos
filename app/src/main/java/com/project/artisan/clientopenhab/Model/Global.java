package com.project.artisan.clientopenhab.Model;

public class Global {

    public static String addressServer;
    public static int portServer;
    public static String url;

    public Global() {
        addressServer="";
        portServer=0;
        url="";
    }

    public Global(String addressServer, int portServer) {
        Global.addressServer = addressServer;
        Global.portServer = portServer;
        url ="http://"+addressServer+":"+portServer;
    }
}
