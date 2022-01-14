将 MOOC WOW 改编为 Java Version，使用 JUnit 通过文本对比进行 verification



# 前言

魔兽世界系列练习来自[程序设计实习MOOC](http://cxsjsxmooc.openjudge.cn/)，共分为四个阶段。前三个阶段是面向所有人开放的，第四阶段是[程序设计实习](http://cxsjsx.openjudge.cn/)中的隐藏阶段。原练习要求使用`C++`编写，为了调试方便和简化代码逻辑，将其改编为`Java`版本。



# 阶段一和阶段二



## 准备测试用例

将一个文件分解为几个小文件

```python
import os

inPath = r"D:\Users\VGalaxy\Desktop\Directory\魔兽世界\docs\魔兽世界之一：备战\in\\"
oriInFile = r"D:\Users\VGalaxy\Desktop\Directory\魔兽世界\docs\魔兽世界之一：备战\in.txt"

outPath = r"D:\Users\VGalaxy\Desktop\Directory\魔兽世界\docs\魔兽世界之一：备战\out\\"
oriOutFile = r"D:\Users\VGalaxy\Desktop\Directory\魔兽世界\docs\魔兽世界之一：备战\out.txt"

os.mkdir(inPath)
os.mkdir(outPath)

with open(oriInFile, 'r') as inFile, open(oriOutFile, 'r') as outFile:
    count = int(inFile.readline())
    for i in range(1, count + 1):
        contents = inFile.readline()
        contents += inFile.readline()
        with open(inPath + "in" + str(i) + ".txt", 'w') as f:
            f.write(contents)
    for i in range(1, count + 1):
        if i == 1:
            outFile.readline()
        head = ""
        contents = ""
        while True:
            head = outFile.readline()
            if head.startswith("Case") or head == "":
                break
            contents += head
        with open(outPath + "out" + str(i) + ".txt", 'w') as f:
            f.write(contents)
```



## 搭建[JUnit](https://junit.org/junit5/)框架

这里模仿平时作业助教的写法

```java
package test;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;


import main.Demo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WOW1Test {
    InputStream in = null;
    PrintStream out = null;

    InputStream inputStream = null;
    OutputStream outputStream = null;

    String seq = System.lineSeparator();

    String path = "D:\\Users\\VGalaxy\\Desktop\\Directory\\魔兽世界\\docs\\魔兽世界之一：备战";

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
```



## 编写代码

阶段一和阶段二较为简单，具体内容略



# 阶段三

## 思路

测试类调用`Demo`类的`main`方法，`main`方法读取输入信息后，创建一个`Event`类，并调用`Event`类的`start`方法。

`Event`类中拥有红司令部和蓝司令部字段，司令部类`Headquarter`中拥有一个武士的集合字段，武士类`Warrior`是抽象类，共有五种武士。武士类中拥有一个武器的集合字段，武器类`Weapon`也是抽象类，共有三种武器。



## 细节

- 为了满足题目要求，我们需要在武士类和武器类中实现一些不同的比较器
- 逻辑上较为复杂的方法是武士的`attack`方法，使用多个辅助函数将其分解
- 需要注意武士在获得他人的武器时需要修改武器的所有者
- 为了实现一边遍历集合一边删除集合中的对象，我们需要使用`Iterator`类的`remove()`方法，否则会触发异常`ConcurrentModificationException`
- 对于战斗中武士状态不再变化的判断，实现中使用惰性判断，战斗次数超过某个值即判定平局
- 抽象类`Warrior`和`Weapon`各有一个`switchTable`方法，进行显式分派



# 阶段四

可能要鸽了🤣，通过扩展游戏功能的复杂性判断面向对象设计的优劣。
