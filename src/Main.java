
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalTime;

/**
 * Main class runs the initialization of the application as well as all the Text
 * output.
 *
 * @author Simon Sørensen
 * @version 1.0
 */
public class Main {
    static boolean running;
    static Person p;
    static File userFile;
    static BufferedWriter writer;
    static String builder;

    /**
     * Reads the file if it exist, parsing all information into the counter and p
     * object.
     * 
     * @param name         name of the user
     * @param splittedDate the date to be read from the file split on ":"
     * @param counter      an object of the CoffeCounter class
     * @param p            an object of the Person class
     * @throws IOException is thrown if there there is no input to be read
     */

    public static void readFiles(String name, String[] splittedDate, CoffeCounter counter, Person p)
            throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name + ".txt"));
        String date = splittedDate[0] + " " + splittedDate[1] + " " + splittedDate[2] + " " + splittedDate[5];
        String date2 = reader.readLine();
        if (date.equals(date2)) {
            String n = reader.readLine();
            p.setName(n);
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.contains(".")) {
                    int cups = p.getCup();
                    p.setCup(cups + Integer.parseInt(currentLine));
                } else if (currentLine.contains(".") && !currentLine.contains(":")) {
                    p.setTotalCaffein(p.getCup() * 94.8);
                    counter.setCaffeineInBody(Double.parseDouble(currentLine));
                } else {
                    String[] timeSplit = currentLine.split(":");
                    int hour = Integer.parseInt(timeSplit[0]);
                    int minute = Integer.parseInt(timeSplit[1]);
                    String[] split2 = timeSplit[2].split("");
                    int second = Integer.parseInt(split2[0] + split2[1]);
                    LocalTime time = LocalTime.of(hour, minute, second);
                    counter.setTime2(time);
                }
            }
        }
        reader.close();
        p.setMgCaffein(counter.mgInBody());
        counter.setTimeInBody();
    }

    /**
     * Builds up a string to be parsed into the file upon closure.
     * 
     * @param input whatever should be written to the file.
     */
    public static void writeToFile(String input) {
        if (builder == null) {
            builder = input;
        } else {
            String temp1 = builder;
            builder = temp1 + "\n" + input;
        }
    }

    /**
     * Tries to create a file
     * <p>
     * This method tries to read a file with the "readFile" method. If no such file
     * exists, it catches an exception which is being handled by creating a file and
     * write the username and date to it.
     * </p>
     * 
     * @param name         the name of the user
     * @param splittedDate the date to be read from the file split on ":"
     * @param counter      an object of the CoffeCounter class
     * @param p            an object of the Person class
     * @throws IOException is thrown if there there is no input to be read
     */
    public static void createFile(String name, String[] splittedDate, CoffeCounter counter, Person p)
            throws IOException {

        try {
            readFiles(name, splittedDate, counter, p);
            System.out.println("Account successfully loaded");
        } catch (Exception e) {
            writer = new BufferedWriter(new FileWriter(p.getName() + ".txt"));
            userFile = new File(name + ".txt");
            System.out.println("Account successfully created");
        }

    }

    /**
     *
     * Sets the mg of Caffeine in the body of the user
     *
     * @param p       The person object who's using the applications
     * @param counter The counter object used to calculate the percentage difference
     *                from consumption.
     */
    public static void setMg(Person p, CoffeCounter counter) {
        counter.setCaffeineInBody(p.getMgCaffein());
        p.setMgCaffein(counter.mgInBody());
        counter.setTimeInBody();
    }

    /**
     * Recursive method to detect if the application should be closed.
     *
     * @param sc the Scanner detecting the User's input.
     */
    public static void terminate(Scanner sc, CoffeCounter counter) {
        String answer = sc.next().toUpperCase();
        setMg(p, counter);
        if (answer.equals("YES")) {
            running = false;
            writeToFile("" + p.getMgCaffein());
            writeToFile("" + p.getCup());
            writeToFile(counter.getTimeInBody().toString());
        } else if (answer.equals("NO")) {
            System.out.println("========================================================");
            System.out.println("Alrighty, let's continue then ;-)");
            System.out.println("========================================================");

        } else {
            System.out.println("========================================================");
            System.out.println("Unknown input, please input either \"YES\" or \"NO\"");
            System.out.println("========================================================");
            terminate(sc, counter);

        }
    }

    /**
     * Method to handle most of the I/O
     *
     * @param sc      the Scanner detecting the User's input.
     * @param p       the Person object storing the user information.
     * @param counter the CoffeCounter object handling the percentile calculations.
     */
    public static void runningCommand(Scanner sc, Person p, CoffeCounter counter) {
        while (running) {
            var input = sc.next();
            int current;
            try {
                if (input.equals("HELP") || input.equals("help")) {
                    System.out.println("========================================================");
                    System.out.println("TIME            displays the current time");
                    System.out.println("MG              displays the current amount of caffeine in your body");
                    System.out.println(
                            "STOP            ends the application - your data will be saved, but make sure to use the same name when using the application again");
                    System.out.println("STATUS          give a status of today's coffee intake");
                    System.out.println("FAQ             gives you some facts about caffeine");
                    System.out.println("========================================================");
                } else if (input.equals("STATUS") || input.equals("status")) {
                    System.out.println("========================================================");
                    System.out.println("Here's your current status:");
                    System.out.println("===========================");
                    if (p.getCup() == 1) {
                        System.out.println("You have been drinking:         1 cup of coffee today");
                    } else {
                        System.out.println("You have been drinking:         " + p.getCup() + " cups of coffee today");
                    }
                    setMg(p, counter);
                    System.out.println(
                            "You currently have              " + p.getMgCaffein() + " mg of caffeine in your body");
                    System.out.println("========================================================");

                } else if (input.equals("FAQ") || input.equals("faq")) {
                    System.out.println("========================================================");
                    System.out.println("Here are some facts about caffeine:");
                    System.out.println("========================================================");
                    System.out.println("The halve time for caffeine is around 6 hours.\n");
                    System.out.println(
                            "Caffeine is often used as a flavouring agent to circumvent the bitterness of certain drinks\n");
                    System.out.println(
                            "The recommended amount of caffeine per day is around 400 mg. This is just above 4 average sized cups of coffee\n");
                    System.out.println(
                            "There is no set lethal dose of caffeine. People have died from as little as 500 mg to 51 grams. Regardless, it's a bad idea to inject it; drink coffee instead.\n");
                    System.out.println("Coffee is awesome, and you can never get enough of it!");
                    System.out.println("========================================================");
                } else if (input.equals("TIME") || input.equals("time")) {
                    System.out.println("========================================================");
                    System.out.println("The time is currently " + counter.getTime() + " o'clock");
                    System.out.println("========================================================");
                } else if (input.equals("MG") || input.equals("mg")) {
                    setMg(p, counter);
                    System.out.println("========================================================");
                    System.out.println("You currently have " + p.getMgCaffein() + " mg of caffeine in your body!");
                    System.out.println("========================================================");
                } else if (input.equals("STOP") || input.equals("stop")) {
                    setMg(p, counter);
                    System.out.println("========================================================");
                    System.out.println("Before we leave, you have been drinking " + p.getCup()
                            + " cup(s) of coffee while using this app.\nYou also had " + p.getMgCaffein()
                            + " mg of caffein in your body when terminating the application.");
                    System.out.println("========================================================");
                    System.out.println(
                            "Are you sure you wish to exit? Write \"NO\" if you wish to continue, otherwise, write \"YES\"");
                    System.out.println("========================================================");
                    terminate(sc, counter);

                } else if (Integer.parseInt(input) > 0) {
                    if (p.getCup() == 0) {
                        p.setCup(Integer.parseInt(input));
                        p.setMgCaffein(p.getCup() * 94.8);
                        p.setTotalCaffein(p.getCup() * 94.8);
                    } else {
                        current = p.getCup();
                        int newCup = Integer.parseInt(input);
                        p.setTotalCaffein((current + newCup) * 94.8);
                        counter.setCaffeineInBody(p.getMgCaffein());
                        p.setMgCaffein(counter.mgInBody() + (newCup * 94.8));
                        p.setCup(current + newCup);
                    }
                    counter.setTimeInBody();

                    if (Integer.parseInt(input) < 2) {
                        System.out.println("========================================================");
                        System.out.println(
                                "Thanks for inputting 1 cup of coffee. This brings up your total cups today to "
                                        + p.getCup() + ". This amounts to " + p.getMgCaffein()
                                        + " mg of caffeine today.");
                        System.out.println("========================================================");
                    } else {
                        System.out.println("========================================================");
                        System.out.println("Thanks for inputting " + Integer.parseInt(input)
                                + " cups of coffee. This brings up your total cups today to " + p.getCup()
                                + ". This amounts to " + p.getMgCaffein() + " mg of caffeine today.");
                        System.out.println("========================================================");
                    }
                }
                if (p.getTotalCaffein() > 400 && !input.equals("STOP") && !input.equals("stop")) {
                    System.out.println("========================================================");
                    System.out.println(
                            "You're currently at or above the recommended max amount of caffeine per day.\nPerhaps you should take a break?");
                    System.out.println("========================================================");
                } else if (p.getMgCaffein() < 400 && p.getTotalCaffein() > 300 && !input.equals("STOP")
                        && !input.equals("stop")) {
                    System.out.println("========================================================");
                    System.out.println(
                            "You are approaching the recommended max amount of caffeine per day. Remember to stay below below 400 mg");
                    System.out.println("========================================================");
                }
            } catch (Exception e) {
                System.out.println("========================================================");
                System.out.println(
                        "Sorry, invalid input. Please input either the amount of coffee or exit the application by writing \"STOP\" or \"HELP\" for more options");
                System.out.println("========================================================");
            }
        }
    }

    /**
     * Main method running the code
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================================");
        System.out.println("Hello!\nPlease input your name:");
        System.out.println("========================================================");
        String name = sc.next();
        p = new Person();
        p.setName(name);
        Date date = new Date();
        String[] splittedDate = date.toString().split(" ");
        String formattedDate = splittedDate[0] + " " + splittedDate[1] + " " + splittedDate[2] + " " + splittedDate[5];
        CoffeCounter counter = new CoffeCounter();
        try {
            createFile(p.getName(), splittedDate, counter, p);
            writeToFile(formattedDate);
            writeToFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("========================================================");
        System.out.println("Thanks, " + name
                + " :-D\nEvery time you have a cup of coffee, please write the amount of cups you're drinking.\nIf you wish to end the application, write \"STOP\"\nAlternatively, write \"HELP\" for more info.");
        System.out.println("========================================================");
        running = true;

        runningCommand(sc, p, counter);
        sc.close();
        try {
            writer = new BufferedWriter(new FileWriter(p.getName() + ".txt"));
            writer.write(builder);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}