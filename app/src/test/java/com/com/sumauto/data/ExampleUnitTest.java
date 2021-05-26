package com.com.sumauto.data;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println("hello");
        try {
            testRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testRead() throws IOException {
        Properties properties = new Properties();
        File file=new File("myOtherStream/fw.txt");
        System.out.println(""+file.getAbsolutePath());
        FileReader reader = new FileReader("app/test.txt");
        properties.load(reader);
        reader.close();

    }
}