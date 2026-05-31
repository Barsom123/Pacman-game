// interface Movable for Movable objects
public interface Movable {
   
    void move(GameMap map); // method to move the object to (x, y)
    void update(GameMap map); // method to update the object's state based on the game map
    
}