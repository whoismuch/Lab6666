package server.armory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import common.command.CommandDescription;
import common.manager.DataExchange;
import server.commands.Command;
import common.converters.CommandDescriptionConverter;
import server.receiver.collection.Navigator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try (ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream( ));
             ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream( ))) {

            DataExchange dataExchange = new DataExchange(getFromClient,sendToClient);
            dataExchange.send("Соединение установлено.\n Вы можете начать ввод команд \n");
            //sendToClient.writeObject("Соединение установлено.\n Вы можете начать ввод команд \n");
            Gson gson = new GsonBuilder( ).setPrettyPrinting( ).registerTypeAdapter(CommandDescription.class, new CommandDescriptionConverter( )).create( );
            CommandDescription command = gson.fromJson((String) dataExchange.get(), CommandDescription.class);
            System.out.println(command.getName());
            Driver.getLive( ).execute(dataExchange, navigator, command.getName( ), command.getArg( ));
            System.out.println("пока-пока");

        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace( );
//        }
    }

}
