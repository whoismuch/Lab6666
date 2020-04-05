package server.commands;


import common.generatedClasses.Route;
import server.armory.DataExchangeWithClient;
import server.receiver.collection.ICollectionManager;

/**
 * Интерфейс, используемый для паттерна команд (все команды реализуют его)
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
public interface Command {

    /**
     * Абстрактный метод для выполнения команды
     * @param icm
     * @param s
     */
    void execute (DataExchangeWithClient dataExchangeWithClient, ICollectionManager icm, String arg, Route route);

    /**
     * Абстрактный метод для получения описания команды
     * @return description описание команды
     */
    String getDescription ( );

    /**
     * Абстрактный метод для получения имени команды
     * @return name имя команды
     */
    String getName ( );

    String getArg ( );


}