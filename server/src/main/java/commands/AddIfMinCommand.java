package commands;


import databaseUtils.DBDragon;
import exceptions.CommandExecutingException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * command 'add_if_min' to add element if it will be minimum in collection
 */
public class AddIfMinCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private DBDragon dbDragon;

    public AddIfMinCommand(CollectionManager collectionManager, DBDragon dbDragon) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.dbDragon = dbDragon;
    }


    @Override
    public ResponseMessage execute(){
        try{
            if (collectionManager.addIfMin(getArgs().getDragon()) || (collectionManager.getCollection().size()==0)){
                if (dbDragon.addDragon(getArgs().getDragon(), getArgs().getUser())){
                    collectionManager.addToCollection(getArgs().getDragon());
                    return new ResponseMessage("Element was successfully added to collection");
                }

            }
            else throw new CommandExecutingException("Element is not less than minimum element in collection");
        }catch (CommandExecutingException e){
            return new ResponseMessage(e.getMessage());
        }
        return null;
    }
}
