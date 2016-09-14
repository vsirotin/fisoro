package examples.numbers.shop;

import examples.numbers.shop.NumbersShopTestDataGenerator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by vsirotin on 06.02.2015.
 */
public class TesterTestDataGenerator {

    private static NumbersShopTestDataGenerator numbersShopTestDataGenerator;

    @BeforeClass
    public static void setUpClass() {
        numbersShopTestDataGenerator = new NumbersShopTestDataGenerator();
    }

    @Test
    public void testFibonacci() throws Exception {

        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(0));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(1));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(2));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(3));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(5));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(8));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(13));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(89));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFibonacci(144));

        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(4));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(6));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(7));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(9));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(10));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(11));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(54));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(56));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(88));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(90));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFibonacci(143));

    }

    @Test
    public void testFactorial() throws Exception {

        Assert.assertTrue(NumbersShopTestDataGenerator.isFactorial(1));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFactorial(2));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFactorial(6));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFactorial(24));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFactorial(120));
        Assert.assertTrue(NumbersShopTestDataGenerator.isFactorial(720));

        Assert.assertFalse(NumbersShopTestDataGenerator.isFactorial(0));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFactorial(3));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFactorial(4));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFactorial(25));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFactorial(121));
        Assert.assertFalse(NumbersShopTestDataGenerator.isFactorial(722));
    }

    @Test
    public void testPrime() throws Exception {

        Assert.assertTrue(NumbersShopTestDataGenerator.isPrime(2));
        Assert.assertTrue(NumbersShopTestDataGenerator.isPrime(3));
        Assert.assertTrue(NumbersShopTestDataGenerator.isPrime(5));
        Assert.assertTrue(NumbersShopTestDataGenerator.isPrime(67));
        Assert.assertTrue(NumbersShopTestDataGenerator.isPrime(683));
        Assert.assertTrue(NumbersShopTestDataGenerator.isPrime(761));

        Assert.assertFalse(NumbersShopTestDataGenerator.isPrime(0));
        Assert.assertFalse(NumbersShopTestDataGenerator.isPrime(1));
        Assert.assertFalse(NumbersShopTestDataGenerator.isPrime(4));
        Assert.assertFalse(NumbersShopTestDataGenerator.isPrime(25));
        Assert.assertFalse(NumbersShopTestDataGenerator.isPrime(762));
        Assert.assertFalse(NumbersShopTestDataGenerator.isPrime(900));
    }

    @Test
    public void testRound() throws Exception {

        Assert.assertTrue(NumbersShopTestDataGenerator.isRound(20));
        Assert.assertTrue(NumbersShopTestDataGenerator.isRound(130));
        Assert.assertTrue(NumbersShopTestDataGenerator.isRound(0));
        Assert.assertTrue(NumbersShopTestDataGenerator.isRound(990));

        Assert.assertFalse(NumbersShopTestDataGenerator.isRound(1));
        Assert.assertFalse(NumbersShopTestDataGenerator.isRound(21));
        Assert.assertFalse(NumbersShopTestDataGenerator.isRound(141));

        Assert.assertFalse(NumbersShopTestDataGenerator.isRound(901));
    }

    @Test
    public void testHorizontal() throws Exception {

        Assert.assertTrue(NumbersShopTestDataGenerator.isHorizontal(222));
        Assert.assertTrue(NumbersShopTestDataGenerator.isHorizontal(333));
        Assert.assertTrue(NumbersShopTestDataGenerator.isHorizontal(999));

        Assert.assertFalse(NumbersShopTestDataGenerator.isHorizontal(1));
        Assert.assertFalse(NumbersShopTestDataGenerator.isHorizontal(22));
        Assert.assertFalse(NumbersShopTestDataGenerator.isHorizontal(132));

        Assert.assertFalse(NumbersShopTestDataGenerator.isHorizontal(901));
    }

    @Test
    public void testTextPresentation0() {
        assertEquals("\"Zero\"", numbersShopTestDataGenerator.generateTextProperties(0));
    }

    @Test
    public void testTextPresentation10() {
        assertEquals("\"Ten\"", numbersShopTestDataGenerator.generateTextProperties(10));
    }

    @Test
    public void testTextPresentation11() {
        assertEquals("\"Eleven\"", numbersShopTestDataGenerator.generateTextProperties(11));
    }

    @Test
    public void testTextPresentation18() {
        assertEquals("\"Eighteen\"", numbersShopTestDataGenerator.generateTextProperties(18));
    }

    @Test
    public void testTextPresentation70() {
        assertEquals("\"Seventy\"", numbersShopTestDataGenerator.generateTextProperties(70));
    }
}
