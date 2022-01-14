package test;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;


import main.Demo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WOW3Test {
    InputStream in = null;
    PrintStream out = null;

    InputStream inputStream = null;
    OutputStream outputStream = null;

    String seq = System.lineSeparator();

    String path = "D:\\Users\\VGalaxy\\Desktop\\Directory\\魔兽世界\\docs\\魔兽世界之三：开战";

    @Before
    public void setUp() {
        in = System.in;
        out = System.out;

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

    }

    @After
    public void tearDown() {
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    public void test1() {
        String in = "\\in\\in1.txt";
        String out = "\\out\\out1.txt";
        check(path + in, path + out);
    }

    @Test
    public void test2() {
        String in = "\\in\\in2.txt";
        String out = "\\out\\out2.txt";
        check(path + in, path + out);
    }

    @Test
    public void test3() {
        String in = "\\in\\in3.txt";
        String out = "\\out\\out3.txt";
        check(path + in, path + out);
    }

    @Test
    public void test4() {
        String in = "\\in\\in4.txt";
        String out = "\\out\\out4.txt";
        check(path + in, path + out);
    }

    @Test
    public void test5() {
        String in = "\\in\\in5.txt";
        String out = "\\out\\out5.txt";
        check(path + in, path + out);
    }

    @Test
    public void test6() {
        String in = "\\in\\in6.txt";
        String out = "\\out\\out6.txt";
        check(path + in, path + out);
    }

    @Test
    public void test7() {
        String in = "\\in\\in7.txt";
        String out = "\\out\\out7.txt";
        check(path + in, path + out);
    }

    @Test
    public void test8() {
        String in = "\\in\\in8.txt";
        String out = "\\out\\out8.txt";
        check(path + in, path + out);
    }

    @Test
    public void test9() {
        String in = "\\in\\in9.txt";
        String out = "\\out\\out9.txt";
        check(path + in, path + out);
    }

    @Test
    public void test10() {
        String in = "\\in\\in10.txt";
        String out = "\\out\\out10.txt";
        check(path + in, path + out);
    }

    @Test
    public void test11() {
        String in = "\\in\\in11.txt";
        String out = "\\out\\out11.txt";
        check(path + in, path + out);
    }

    private void check(String inPath, String outPath) {
        Scanner in = null, out = null;
        StringBuilder input, output;
        try {
            in = new Scanner(Path.of(inPath));
            out = new Scanner(Path.of(outPath));
            input = new StringBuilder();
            while (in.hasNextLine())
                input.append(in.nextLine() + seq);
            inputStream = new ByteArrayInputStream(input.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(inputStream);

        Demo.main(null);
        String actual = outputStream.toString();

        output = new StringBuilder();
        while (out.hasNextLine())
            output.append(out.nextLine() + seq);
        String expected = output.toString();

        assertEquals(expected, actual);
    }
}