package hello;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimpeTest {

    @BeforeClass
    public void setUp(){

    }

    @Test(description = "Test it")
    public void testMethod1(){
        Assert.assertTrue(true);
    }

    @AfterClass
    public void tearDown(){

    }
}
