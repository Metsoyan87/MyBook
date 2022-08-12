package storage;


import model.Book;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    public void writeBooksToExcel(String fileDir) throws IOException {
        File directory = new File(fileDir);
        if (directory.isFile()) {
            throw new RuntimeException("fileDit must bi a directory!");
        }
        File excelFile = new File(directory, "books_" + System.currentTimeMillis() + ".xlsx");
        excelFile.createNewFile();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("books");
        Row headerRow = sheet.createRow(0);

        Cell titleCell = headerRow.createCell(0);
        titleCell.setCellValue("title");

        Cell priceCell = headerRow.createCell(1);
        priceCell.setCellValue("price");

        Cell countCell = headerRow.createCell(2);
        countCell.setCellValue("count");

        for (int i = 0; i < size; i++) {
            Book book = array[i];
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(book.getTitle());
            row.createCell(1).setCellValue(book.getPrice());
            row.createCell(2).setCellValue(book.getCount());
        }
workbook.write(new FileOutputStream(excelFile));
        System.out.println("Excel was created successfully");
    }
}


