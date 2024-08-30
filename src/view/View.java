package view;

public class View {

    /*
     * first welcome of programming
     * welcome to CSTAD
     */
    public void welcome() {
        System.out.println("*====================================================================================*");
        System.out.println("" +
                "   ██████╗ ██████╗ ██╗    ██╗███╗   ██╗██████╗  █████╗ ██████╗██╗ ██████╗ ███╗   ██╗     █████╗ ██╗         █████╗ ███████╗███████╗ \n"+
                "   ██╔═════╝██╔════██╗██║    ██║████╗  ██║██╔═══██╗██╔══██╗╚══██╔══╝██║██╔═══ ██╗████╗  ██║    ██╔════╝ ██║        ██╔══██╗██╔═══════╝██╔══════╝  \n"+
                "   ████╗   ██║    ██║██║    ██║██╔██╗ ██║██║   ██║██████║   ██║   ██║██║    ██║██╔██╗ ██║    ██║      ██║        ██████║███████╗███████╗ \n"+
                "   ██╔══╝   ██║    ██║██║    ██║██║╚██╗██║██║   ██║██╔══██║   ██║   ██║██║    ██║██║╚██╗██║    ██║      ██║        ██╔══██║╚═══════██║╚══════██║ \n"+
                "   ██║      ╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║    ╚█████╗ ███████╗██║  ██║███████║███████║ \n"+
                "   ╚══╝       ╚════════╝  ╚════════╝ ╚══╝  ╚═════╝╚════════╝ ╚══╝  ╚══╝   ╚══╝   ╚══╝ ╚════════╝ ╚══╝  ╚═════╝     ╚══════╝ ╚══════════╝╚══╝  ╚══╝╚══════════╝╚═════════╝ \n"+
                "                                                                                                                                "+
                "");
    }
    public static void menu() {
        System.out.println("=================================================");
        System.out.println("1. Add new student");
        System.out.println("2. List all students");
        System.out.println("3. Commit data to file");
        System.out.println("4. Search for student");
        System.out.println("5. Update students by ID");
        System.out.println("6. Delete student's data ");
        System.out.println("7. Generate data into file");
        System.out.println("8. Delete/Clear all data from Data store");
        System.out.println("9. Exit");
        System.out.println("=================================================");
    }

}
