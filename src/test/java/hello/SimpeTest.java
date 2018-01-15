package hello;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpeTest {

    @BeforeClass
    public void setUp(){

    }

    @Test(description = "Test it")
    public void testMethod1(){
        String testStr = "10.11.1000.00506";
        String last_build_num_pattern = "[1-9]+.*$";
        String fStr1 = testStr.split("\\.")[3];
        System.out.println("-----:"+fStr1);
        String temp1 = "";
        Pattern pattern = Pattern.compile(last_build_num_pattern);
        Matcher m1 = pattern.matcher(fStr1);

        if(m1.find()) {
            temp1 = m1.group();
            System.out.print("Find true");
        }
        System.out.print("=========> result1="+temp1);
        int f1 = Integer.parseInt(temp1);
        System.out.print(">>>>>>> "+f1);
    }

    @AfterClass
    public void tearDown(){

    }
}
