import commands.Commands;
import enums.Gender;
import exception.AuthorNotFoundException;
import model.Author;
import model.Book;
import model.Role;
import model.User;
import org.apache.poi.ss.usermodel.Workbook;
import storage.AuthorStorage;
import storage.BookStorage;
import storage.UserStorage;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import static util.DateUtil.stringToDate;


public class BookDemo implements commands.Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static BookStorage bookStorage = new BookStorage();
    private static AuthorStorage authorStorage = new AuthorStorage();
    private static UserStorage userStorage = new UserStorage();

    private static User currentUser = null;

    public static void main(String[] args) {


        Author Sevak = new Author("Paruyr", "Sevak", "sevak@mail.ru", Gender.MALE, stringToDate("03/05/2021"));
        authorStorage.add(Sevak);
        Author Viliam = new Author("Viliam", "Saroyan", "saroyan@mail.ru", Gender.MALE, stringToDate("01/09/2021"));
        authorStorage.add(Viliam);
        Author Silva = new Author("Silva", "Hakobyan", "Hakobyan@mail.ru", Gender.FEMALE, stringToDate("03/05/2021"));
        authorStorage.add(Silva);

        //Admin
        User admin = new User("admin", "admin", "admin@mail.ru", "admin", Role.ADMIN);
        userStorage.add(admin);
        User user = new User("admin", "admin", "a", "a", Role.USER);
        userStorage.add(user);
        User user1 = new User("admin", "admin", "admin@mail.ru", "admin", Role.USER);
        userStorage.add(user1);


        bookStorage.add(new Book("Hayastan", Silva, 12.25, 3, "vep", admin, new Date()));
        bookStorage.add(new Book("Sasunci Davit", Viliam, 52.36, 1, "Patmakan", user, new Date()));


        boolean run = true;
        while (run) {
            commands.Commands.printLoginCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case 0:
                    run = false;
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void login() {
        System.out.println("please input email, password");
        String emailPasswordStr = scanner.nextLine();
        String[] emailPassword = emailPasswordStr.split(",");
        User user = userStorage.getUserByEmail(emailPassword[0]);
        if (user == null) {
            System.out.println("User does not exists");
        } else {
            if (!user.getPassword().equals(emailPassword[1])) {
                System.out.println("Password iz wrong!");
            } else {
                currentUser = user;
                if (user.getRole() == Role.ADMIN) {
                    adminLogin();
                } else if (user.getRole() == Role.USER) {
                    userLogin();
                }
            }
        }
    }

    private static void register() {

        System.out.println("Please input name, surName, email, password");
        String userDataStr = scanner.nextLine();
        String[] userData = userDataStr.split(",");
        if (userData.length < 4) {
            System.out.println("please input correct user data");
        } else {
            if (userStorage.getUserByEmail(userData[2]) == null) {
                User user = new User();
                user.setName(userData[0]);
                user.setSurName(userData[1]);
                user.setEmail(userData[2]);
                user.setPassword(userData[3]);
                user.setRole(Role.USER);
                userStorage.add(user);
                System.out.println("User registered!");
            } else {
                System.out.println("User wit " + userData[2] + " already exists");
            }
        }

    }

    private static void adminLogin() {
        System.out.println("Welcome, " + currentUser.getName());
        boolean run = true;
        while (run) {
            commands.Commands.printAdminCommand();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    run = false;
                    currentUser = null;
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_ALL_Books:
                    bookStorage.print();
                    break;
                case PRINT_BOOKS_BY_AUTHOR_NAME:
                    searchByBook();
                    break;
                case PRINT_BOOKS_BY_GENRE:
                    searchByGenre();
                    break;
                case PRINT_BOOKS_BY_PRICE_RANGE:
                    booksByPrice();
                    break;
                case ADD_AUTHOR:
                    addAuthor();
                    break;
                case PRINT_ALL_AUTHORS:
                    authorStorage.print();
                    break;
                case SEARCH_AUTHOR_BY_INDEX:
                    authorByIndex();
                    break;
                case CHANGE_BOOK_AUTHOR:
                    changeBookAuthor();
                    break;
                case DOWNLOAD_ALL_BOOKS:
                    downloadAllBooks();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void userLogin() {
        System.out.println("Welcome, " + currentUser.getName());
        boolean run = true;
        while (run) {
            Commands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    run = false;
                    currentUser = null;
                    break;
                case PRINT_ALL_Books:
                    bookStorage.print();
                    break;
                case PRINT_BOOKS_BY_AUTHOR_NAME:
                    searchByBook();
                    break;
                case PRINT_BOOKS_BY_GENRE:
                    searchByGenre();
                    break;
                case PRINT_BOOKS_BY_PRICE_RANGE:
                    booksByPrice();
                    break;
                case PRINT_ALL_AUTHORS:
                    authorStorage.print();
                    break;
                case SEARCH_AUTHOR_BY_INDEX:
                    authorByIndex();
                    break;
                case DOWNLOAD_ALL_BOOKS:
                    downloadAllBooks();
                    break;

                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void addAuthor() {

        System.out.println("Please input author name");
        String name = scanner.nextLine();

        System.out.println("Please input author surname");
        String surname = scanner.nextLine();

        System.out.println("Please input author email");
        String email = scanner.nextLine();

        System.out.println("Please input author gender " + Gender.MALE + " or " + Gender.FEMALE);

        try {
            Gender genderStr = Gender.valueOf(scanner.nextLine());
            System.out.println("Please input lesson start date (dd/MM/yyyy)");
            String strDate = scanner.nextLine();
            Author author = new Author(name, surname, email, genderStr, stringToDate(strDate));
            authorStorage.add(author);
            System.out.println("author created!");
        } catch (IllegalArgumentException e) {
            System.out.println("Please input MALE or Female");
            addAuthor();
        }
    }

    private static void changeBookAuthor() {
        bookStorage.print();
        System.out.println("Please choose book index");
        int index = Integer.parseInt(scanner.nextLine());
        Book book = bookStorage.getBookByIndex(index);
        if (book != null) {
            authorStorage.print();
            System.out.println("Please choose author index");
            int authorIndex = Integer.parseInt(scanner.nextLine());
            try {
                Author author = authorStorage.getAuthorByIndex(authorIndex);
                book.setAuthor(author);
                System.out.println("Author changed!");
            } catch (AuthorNotFoundException e) {
                System.out.println(e.getMessage());
                changeBookAuthor();
            }
        } else {
            System.out.println("invalid index, please try again");
            changeBookAuthor();
        }
    }

    private static void addBook() {
        if (authorStorage.getSize() == 0) {
            System.out.println("Please add author");
            addAuthor();
        } else {
            authorStorage.print();
            System.out.println("Please choose author index");
            int authorIndex = Integer.parseInt(scanner.nextLine());
            try {
                Author author = authorStorage.getAuthorByIndex(authorIndex);
                System.out.println("Please input book title");
                String title = scanner.nextLine();
                System.out.println("Please input book price");
                String priceStr = scanner.nextLine();
                System.out.println("Please input book count");
                String countStr = scanner.nextLine();
                System.out.println("Please input book genre");
                String genre = scanner.nextLine();
                System.out.println("Please input lesson start date (dd/MM/yyyy)");
                String strDate = scanner.nextLine();

                double price = Double.parseDouble(priceStr);
                int count = Integer.parseInt(countStr);
                Book book = new Book(title, author, price, count, genre, currentUser, stringToDate(strDate));
                bookStorage.add(book);
                System.out.println("Thank you, book added");
            } catch (AuthorNotFoundException e) {
                System.out.println(e.getMessage());
                addBook();
            }
        }
    }

    static void searchByBook() {
        System.out.println("Please input book author name");
        String authorName = scanner.nextLine();
        bookStorage.printBooksByAuthor(authorName);
    }

    static void searchByGenre() {
        System.out.println("Please input book genre");
        String genre = scanner.nextLine();
        bookStorage.printBooksByGenre(genre);
    }

    static void booksByPrice() {
        System.out.println("Please input book price");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Please input book price");
        double range = Double.parseDouble(scanner.nextLine());
        bookStorage.printBooksByPriceRange(price, range);
    }

    private static void authorByIndex() {
        try {
            System.out.println("Please input author index");
            int index = Integer.parseInt(scanner.nextLine());

            System.out.println(authorStorage.getAuthorByIndex(index));
        } catch (AuthorNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void downloadAllBooks() {
        System.out.println("Please input file directory");
        String fileDir = scanner.nextLine();
        try {
            bookStorage.writeBooksToExcel(fileDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
