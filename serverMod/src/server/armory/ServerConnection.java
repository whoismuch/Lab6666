package server.armory;

import common.command.CommandDescription;
import server.receiver.collection.Navigator;

import java.io.*;
import java.net.Socket;

public class ServerConnection<T> {

    private Socket incoming;

    private static Driver driver;
    private static Navigator navigator;
    private static String path;

    public ServerConnection (Navigator navigator,Socket incoming, String path) {
        driver = new Driver( );
        this.navigator = navigator;
        this.path = path;
        this.incoming = incoming;

    }


    public void serverWork () throws  IOException {

            DataExchangeWithClient dataExchangeWithClient = new DataExchangeWithClient(incoming);
            dataExchangeWithClient.sendToClient(driver.getAvailable( ));
            driver.load(dataExchangeWithClient, navigator, path);
            while (true) {
                boolean flag = false;
                CommandDescription command = (CommandDescription) dataExchangeWithClient.getFromClient();
                if (command.getName( ).equals("exit")) flag = true;
                Driver.getLive( ).execute(dataExchangeWithClient, navigator, command.getName( ), command.getArg( ), command.getRoute( ));
                if (flag) break;
            }

    }


    public static void theEnd() {
        driver.save(null, navigator, path );
    }
}
