package storage;


import exception.AuthorNotFoundException;
import model.Author;

public class AuthorStorage {
    Author[] author = new Author[10];
    int size = 0;

    public void add(Author author) {
        if (size == this.author.length) {
            extend();
        }
        this.author[size++] = author;
    }

    private void extend() {
        Author[] temp = new Author[author.length + 10];
        System.arraycopy(author, 0, temp, 0, size);
        author = temp;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + author[i] + " ");
        }
    }

    public int getSize() {
        return size;
    }


    public Author getAuthorByIndex(int index) throws AuthorNotFoundException {
        if (index >= 0 && index < size) {
            return author[index];
        } else {
            throw new AuthorNotFoundException("Lesson with " + index + " index does not exists");
        }
    }


    public void printBooksByAuthorName(String author) {

        for (int i = 0; i < size; i++) {
            if (this.author[i].getName().equalsIgnoreCase(author)) {
                System.out.println(this.author[i]);
            }
        }
    }

}