public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    public Game(){
        parser = new Parser();
        player = new Player();
    }

    public static void main(String[]args) {
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms() {
        Room prison = new Room("You are lying on a hard floor. Your head aches and your vision is blurred. You need to stand up and look around.", "You are standing in the middle of a large building with a high ceiling and gray walls. There are two levels of cells. " +
                "The rusted cell doors are open and you can see old bunk beds. The fluorescent ceiling lights flicker periodically. There are no windows in sight. It appears to be a prison, but you have no recollection of how you got there. There are four dimly lit concrete tunnels leading north, " +
                "east, south, and west.");
        Room northRoom = new Room("You enter the North Room. It is a small room that has a strong citrus scent. Investigate the room further.", "The North Room appears to be a janitorial closet. There are folded newspapers and cleaning supplies spilled on the floor. " +
                "One light bulb is still functioning and you can barely make out the collapsed staircase heading north.");
        Room eastRoom = new Room("You enter the East Room. It is brightly lit and the items inside are damaged. Investigate the room further.", "The East Room appears to be a control room for the prison guards to keep watch on the inmates. There are large cracked TV’s hanging " +
                "on the walls and face-down computer monitors on several long desks. There are papers and files strewn about which show prisoner information. It looks like the guards left in a hurry. There is a door leading north. You cannot open it because it is jammed against something on the other " +
                "side. You spot a rusted key on the floor.");
        Room southRoom = new Room("You enter the South Room. You vaguely see an old lantern on an overturned trash can in the corner of the room as you walk in. You cannot see anything else as it is too dark.", "You are standing in a spacious room. " +
                "You make out the outlines of weightlifting and exercise equipment. It appears to be the prison gym. The weight plates are still on the barbells, which you find strange, leading you to believe that the prisoners left in a hurry. There are used towels and worn prison uniforms " +
                "laying on the floor and on various equipment. There is a dark staircase leading downwards to the east.");
        Room westRoom = new Room("You enter the West Room. You see a dusty flashlight on the ground as you get to the entrance of the room. You cannot see into the room you are about to enter as it is too dark.", "You are standing in an unlit room. You see rows of pews " +
                "and crosses hung from the ceiling. It appears to be the prison chapel. There are paintings of biblical figures and scenes on the walls. There are several windows on the north wall, but they are completely boarded shut with a large red “X” spray painted over them. There is a" +
                " dark staircase leading downwards to the west.");
        Room basement = new Room("You enter the basement. You see a heavy vault door on the far wall. There are bullet holes and slashes on its surface. Investigate the room further.", "The basement is grimy and the air is heavy. Upon further investigation, a " +
                "keyhole emerges on the door, hidden by the damage caused to it. You see shattered homemade melee weapons and unusable police-issued firearms haphazardly thrown about, explaining the damage. It appears the prisoners and guards attempted to escape the prison together.");
        Room outside = new Room("You open the vault door and walk up a metal stairwell to exit the prison. As you reach the top, you turn around and see a dense forest. There is a lazy river flowing downstream. Large sequoia trees block the bright sunlight of the afternoon. " +
                "The air feels fresh and you can hear the melodious calls of birds. You see a grassy clearing with colorful plants after the treeline ends. You make out the peaks of mountains in the distance. Turning around again, you see the barbed wire fences and chain-linked walls of " +
                "the prison in front of you. To your left, you hear the piercing whistle and sounds of a locomotive and see its smoke dissipating into the cloudless sky. You begin walking in that direction. You are free.";

        prison.setExit("north", northRoom);
        prison.setExit("west", westRoom);
        prison.setExit("south", southRoom);
        prison.setExit("east", eastRoom);
        northRoom.setExit("south", prison);
        westRoom.setExit("south", prison);
        westRoom.setExit("west", basement);
        southRoom.setExit("south", prison);
        southRoom.setExit("east", basement);
        eastRoom.setExit("south", prison);
        basement.setExit("east", southRoom);
        basement.setExit("west", westRoom);
        basement.setExit("north", outside);

        Item lantern = new Item();
        Item flashlight = new Item();
        Item key = new Item();

        eastRoom.setItem("key", key);
        southRoom.setItem("lantern", lantern);
        westRoom.setItem("flashlight", flashlight);

        currentRoom = prison;
    }

    public void play() {
        printWelcome();

        boolean finished = false;

        while(!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thanks for playing!");
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch(commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean.");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;

            case LOOK:
                look(command);
                break;

            case DROP:
                drop(command);
                break;

            case GRAB:
                grab(command);
                break;
        }

        return wantToQuit;
    }

    private void grab(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Grab what?");
            return;
        }

        String key = command.getSecondWord();
        Item grabItem = currentRoom.getItem(key);

        if (grabItem == null) {
            System.out.println("You can't grab " + command.getSecondWord());
        }
        else {
            player.setItem(key, grabItem);
        }
    }

    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
            return;
        }

        String key = command.getSecondWord();
        Item dropItem = player.getItem(key);

        if (dropItem == null) {
            System.out.println("You can't drop " + command.getSecondWord());
        }
        else {
            currentRoom.setItem(key, dropItem);
        }
    }

    private void printHelp() {
        System.out.println("You are lost");
        System.out.println("You are in a prison.");
        System.out.println();
        System.out.println("Your command words are: go, quit, help, ?, look, grab, drop, turnon.");
        parser.showCommands();
    }

    private void look(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("You can't look at " + command.getSecondWord());
            return;
        }

        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }

    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("You can't quit " + command.getSecondWord());
            return false;
        }
        else {
            return true;
        }
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself in a prison, desperate to escape!");
        System.out.println("Type \"help\" if you need assistance");
        System.out.println();
        System.out.println("");
    }

}