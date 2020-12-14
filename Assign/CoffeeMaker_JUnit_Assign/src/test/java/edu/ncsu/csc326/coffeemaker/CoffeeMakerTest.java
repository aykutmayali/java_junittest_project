
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker ;
	//i = coffeeMaker.checkInventory();
	private static Inventory inventory;
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe[] recipeArray;
	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}
	
	
    @Test
	public void testAddInventory() {
		try {
			this.coffeeMaker.addInventory("3", "1", "1", "0");
		} catch (InventoryException e) {
			e.printStackTrace();
			fail();
		}
		String expectedInventoryString = "Coffee: 3\nMilk: 1\nSugar: 1\nChocolate: 0\n";
		String actualInventoryString = this.coffeeMaker.checkInventory();
		assertEquals(expectedInventoryString, actualInventoryString);
	}
    
    @Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "abcd", "30");
	}   
    
    @Test // should fail
    public void testAddInventory2() throws InventoryException{
    	coffeeMaker.addInventory("A", "B", "C", " ");
    }
    
    @Test // should fail
    public void testAddInventory3() throws InventoryException{
    	coffeeMaker.addInventory("1","-2", "1", "3");
    }
       
    @Test
	public void testMakeCoffee() {
		int expectedInt = 1;
		int actualInt = this.coffeeMaker.makeCoffee(0, 5);
		String expectedInventoryString = "Coffee: 10\nMilk: 10\nSugar: 10\nChocolate: 15\n";
		String actualInventoryString = this.coffeeMaker.checkInventory();
		assertEquals(expectedInt, actualInt);
		assertEquals(expectedInventoryString, actualInventoryString);
	}
    
    @Test
    public void testMakeCoffee2() {
    	coffeeMaker.addRecipe(recipe1);
    	assertEquals(25,coffeeMaker.makeCoffee(0, 75));
    }
    
    @Test // should fail
    public void testMakeCoffee3() {
    	coffeeMaker.addRecipe(recipe2);
    	assertEquals(75,coffeeMaker.makeCoffee(2,100));
    }
    
    @Test // should fail
    public void testMakeCoffee4() {
    	coffeeMaker.addRecipe(recipe3);
    	assertEquals("Raspberry",coffeeMaker.makeCoffee(3,100));
    }
    
    @Test
	public void testAddRecipe() {
		this.coffeeMaker.addRecipe(recipe1);
		Recipe[] expectedRecipeArray = this.recipeArray;
		Recipe[] actualRecipeArray = this.coffeeMaker.getRecipes();
		assertArrayEquals(expectedRecipeArray, actualRecipeArray);
	}
    
    @Test
	public void testRecipeAdd2()
	{
		boolean recipeAdd3 = coffeeMaker.addRecipe(recipe3);
		assertTrue(recipeAdd3);
	}

    @Test // should fail
	public void testRecipeAdd3()
	{
		boolean recipeAdd4 = coffeeMaker.addRecipe(recipe3);
		assertTrue(recipeAdd4);
		assertFalse(recipeAdd4);
	}
    
    
    @Test
	public void testEditRecipe() {
		Recipe newRecipe = new Recipe();
		newRecipe.setName("Test New Recipe");
		String expectedOldRecipeName = this.recipeArray[0].getName();
		String actualOldRecipeName = this.coffeeMaker.editRecipe(0, newRecipe);
		this.recipeArray[0] = newRecipe;
		Recipe[] expectedRecipeArray = this.recipeArray;
		Recipe[] actualRecipeArray = this.coffeeMaker.getRecipes();
		assertEquals(expectedOldRecipeName, actualOldRecipeName);
		assertArrayEquals(expectedRecipeArray, actualRecipeArray);
	}
    @Test
	public void testDeleteRecipe() {
		String expectedRecipeName = this.recipeArray[0].getName();
		String actualRecipeName = this.coffeeMaker.deleteRecipe(0);
		this.recipeArray[0] = null;
		Recipe[] expectedRecipeArray = this.recipeArray;
		Recipe[] actualRecipeArray = this.coffeeMaker.getRecipes();
		assertEquals(expectedRecipeName, actualRecipeName);
		assertArrayEquals(expectedRecipeArray, actualRecipeArray);
	}
    
    @Test
    public void testDeleteRecipe2() {
    	boolean expected= coffeeMaker.addRecipe(recipe1);
    	assertTrue(expected);
    	String actual = coffeeMaker.deleteRecipe(0);
    	assertEquals(null,actual);
    }
    
    @Test
    public void testSetValidAmountOfChocolateInInventory() throws InventoryException{
    	inventory.setChocolate(0);
    	assertEquals(inventory.getChocolate(),0);
    	inventory.setChocolate(100);
    	assertEquals(inventory.getChocolate(),100);
    }
    
    @Test
    public void testSetInvalidAmountOfChocolateInInventory() throws InventoryException{
    	assertThrows(InventoryException.class,()->inventory.setChocolate(-4));
    	assertThrows(InventoryException.class,()->inventory.setChocolate(1020));
    }
    
    @Test
    public void testSetValidAmountOfMilkInInventory() throws InventoryException{
    	inventory.setMilk(0);
    	assertEquals(inventory.getMilk(),0);
    	inventory.setMilk(100);
    	assertEquals(inventory.getMilk(),100);
    }
    
    @Test
    public void testSetInvalidAmountOfMilkInInventory() throws InventoryException{
    	assertThrows(InventoryException.class,()->inventory.setMilk(-4));
    	assertThrows(InventoryException.class,()->inventory.setMilk(1020));
    }
    
    @Test
	public void testCheckInventory() {
		String expectedInventoryString = "Coffee: 0\nMilk: 1\nSugar: 1\nChocolate: 4\n";
		String actualInventoryString = this.coffeeMaker.checkInventory();
		assertEquals(expectedInventoryString, actualInventoryString);
	}
    
    @Test //should fail
   	public void testCheckInventory2() throws InventoryException
   	{
    	String cm = coffeeMaker.checkInventory();
    	assertEquals(cm,null);
   	}
    
    @Test
	public void testMakeCoffeeInsufficientInventory() {
		Recipe badRecipe = new Recipe();
		badRecipe.setName("Test Bad Recipe");
		try {
			badRecipe.setPrice("4");
			badRecipe.setAmtCoffee("20");
		} catch (RecipeException e) {
			fail();
		}
		this.coffeeMaker.addRecipe(badRecipe);
		int expectedInt = 5;
		int actualInt = this.coffeeMaker.makeCoffee(1, 5);
		String expectedInventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
		String actualInventoryString = this.coffeeMaker.checkInventory();
		assertEquals(expectedInt, actualInt);
		assertEquals(expectedInventoryString, actualInventoryString);
	}
    
    @Test
	public void testMakeCoffeeInvalidIndex() {
		int expectedInt = 4;
		int actualInt = this.coffeeMaker.makeCoffee(1, 4);
		String expectedInventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
		String actualInventoryString = this.coffeeMaker.checkInventory();
		assertEquals(expectedInt, actualInt);
		assertEquals(expectedInventoryString, actualInventoryString);
	}
    
    @Test
	public void testMakeCoffeeInvalidAmtPaid() {
		int expectedInt = 2;
		int actualInt = this.coffeeMaker.makeCoffee(0, 2);
		String expectedInventoryString = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
		String actualInventoryString = this.coffeeMaker.checkInventory();
		assertEquals(expectedInt, actualInt);
		assertEquals(expectedInventoryString, actualInventoryString);
	}
    
	@Test
	public void testGetRecipes() {
		Recipe[] expected = this.recipeArray;
		Recipe[] actual = this.coffeeMaker.getRecipes();
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetRecipe1() {
		coffeeMaker.addRecipe(recipe4);
		coffeeMaker.getRecipes();
	}
	
	@Test
	public void testGetRecipe2() {
		boolean expected = coffeeMaker.addRecipe(recipe3);
		//Recipe[] actual = coffeeMaker.getRecipes();
		assertTrue(expected);
	}
	
    @Test
    public void testSetPriceValid_1() throws RecipeException {
        recipe1.setPrice("25");
    }

    @Test
    public void testSetPriceValid_2() throws RecipeException {
    	recipe1.setPrice("0");
    }

    @Test(expected = RecipeException.class)
    public void testSetPriceInvalid0() throws RecipeException {
    	recipe1.setPrice("adsada");
    }

    @Test(expected = RecipeException.class)
    public void testSetPriceInvalid1() throws RecipeException {
    	recipe1.setPrice(" ");
    }

    @Test(expected = RecipeException.class)
    public void testSetPriceInvalid2() throws RecipeException {
    	recipe1.setPrice("-1");
    }
}