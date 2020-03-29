package robots;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Game game = new Game();
        game.activeRobot().move(Direction.NORTH);
        game.activeRobot().move(Direction.NORTH);
        game.activeRobot().move(Direction.NORTH);
        game.activeRobot().move(Direction.NORTH);
        game.activeRobot().move(Direction.NORTH);
        game.activeRobot().move(Direction.NORTH);
    }
}
