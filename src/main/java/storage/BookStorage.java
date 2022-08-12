package storage;


import model.Book;

public class BookStorage {
    Book[] array = new Book[10];
    int size = 0;

    public void add(Book book) {
        if (size == array.length) {
            extend();
        }
        array[size++] = book;
    }

    private void extend() {
        Book[] temp = new Book[array.length + 10];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + array[i] + " ");
        }
    }

    public int getSize() {
        return size;
    }

    public void printBooksByAuthor(String authorName) {
        for (int i = 0; i < size; i++) {
            if (array[i].getAuthor().equals(authorName)) {
                System.out.println(array[i]);
            }
        }
    }

    public void printBooksByGenre(String genre) {
        for (int i = 0; i < size; i++) {
            if (array[i].getGenre().equalsIgnoreCase(genre)) {
                System.out.println(array[i]);
            }
        }
    }

    public void printBooksByPriceRange(double price, double range) {

        for (int i = 0; i < size; i++) {
            if (array[i].getPrice() >= price && array[i].getPrice() <= range) {
                System.out.println(array[i]);
            }
                System.out.println("not found");
                break;
        }
    }
    public Book getBookByIndex(int index) {
        if (index >= 0 && index < size) {
            return array[index];
        }
        return null;
    }
}


