public class Game {
    private Room currentRoom;
    public Game() {

    }

    public static void main(String[]args) {
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms() {
        Room prison = new Room("You are lying on a hard floor. Your head aches and your vision is blurred. You need to stand up and look around.");
        Room northRoom = new Room("You enter the North Room. It is a small room that has a strong citrus scent. Investigate the room further.");
        Room eastRoom = new Room("You enter the East Room. It is brightly lit and the items inside are damaged. Investigate the room further.");
        Room southRoom = new Room("You enter the South Room. You vaguely see an old lantern on an overturned trash can in the corner of the room as you walk in. You cannot see anything else as it is too dark.");
        Room westRoom = new Room("You enter the West Room. You see a dusty flashlight on the ground as you get to the entrance of the room. You cannot see into the room you are about to enter as it is too dark.");
        Room basement = new Room("You enter the basement. You see a heavy vault door on the far wall. There are bullet holes and slashes on its surface. Investigate the room further.");
    }
    public void play() {
        printWelcome();
        boolean finished = false;
        while(!finished) {

        }
        System.out.println("Thanks for playing");
    }
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself in a garden maze, desperate to escape!");
        System.out.println("Type \"help\" if you need assistance");
        System.out.println();
        System.out.println("We will print a long room description here");
    }
}