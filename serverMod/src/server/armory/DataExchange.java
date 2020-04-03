package common.manager;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class DataExchange {

    private ObjectInputStream get;
    private ObjectOutputStream send;
    private Socket outcoming;
    public DataExchange (ObjectInputStream get, ObjectOutputStream send) {
        this.get = get;
        this.send = send;
    }




    public void send (String message) {
        try {
            send.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    public Object get (){
        try {
            return get.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace( );
            return  "Ой-Ой-Ой";
        }
    }



}
