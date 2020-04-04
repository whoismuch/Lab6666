package server.armory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.command.CommandDescription;
import common.manager.DataExchangeWithClient;
import server.commands.Command;
import common.converters.CommandDescriptionConverter;
import server.receiver.collection.Navigator;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnection {

    private Navigator navigator;
    private Socket incoming;
    private HashMap<String, Command> commands;
    private Driver driver;

    public ServerConnection (Navigator navigator, Socket incoming) {
        driver = new Driver( );
        this.navigator = navigator;
        this.incoming = incoming;
    }


    public void serverWork ( ) throws  IOException {
        try (DataOutputStream sendToClient = new DataOutputStream(incoming.getOutputStream( ));
             DataInputStream getFromClient = new DataInputStream(incoming.getInputStream( ))) {

            DataExchangeWithClient dataExchangeWithClient = new DataExchangeWithClient(getFromClient,sendToClient);
            dataExchangeWithClient.sendToClient("Соединение установлено.\nВы можете начать ввод команд\n");
            //sendToClient.writeObject("Соединение установлено.\n Вы можете начать ввод команд \n");
            Gson gson = new GsonBuilder( ).setPrettyPrinting( ).registerTypeAdapter(CommandDescription.class, new CommandDescriptionConverter( )).create( );
            CommandDescription command = gson.fromJson(dataExchangeWithClient.getFromClient(), CommandDescription.class);
            Driver.getLive( ).execute(dataExchangeWithClient, navigator, command.getName( ), command.getArg( ));
            System.out.println("пока-пока");

        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace( );
//        }
    }

}
