package step_definitions;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.EtsyPage;
import utilities.BrowserUtils;
import utilities.Driver;


public class EtsyActionsHW {
	EtsyPage page = new EtsyPage();

	BrowserUtils utils = new BrowserUtils();
	
	@Given("I am on the Etsy {string} homepage")
	public void i_am_on_the_etsy_homepage(String etsyURL) {
	   Driver.getDriver().get(etsyURL);
	}
	@Given("I Hover Over on Jewelry & Accessories")
	public void i_hover_over_on_jewelry_accessories() throws InterruptedException {
		Thread.sleep(1000);
		utils.hoverOverToElement(page.jeweleryAndAccessoriesTab);
	    
	}
	@Then("I Mouseover on Bags & Purses")
	public void i_mouseover_on_bags_purses() throws InterruptedException {
		Thread.sleep(1000);
		utils.hoverOverToElement(page.bagsAndPursesLink);
	   
	}
	@Then("I Mouseover to Shoulder Bags")
	public void i_mouseover_to_shoulder_bags() {
		utils.hoverOverToElement(page.shoulderBagsLink);
	    
	}
	@When("I Click on the shoulder bags")
	public void i_click_on_the_shoulder_bags() throws InterruptedException {
		page.shoulderBagsLink.click();
	   
	}
	@Then("I should Verify that I am on the Shoulder bags page")
	public void i_should_verify_that_i_am_on_the_shoulder_bags_page() {
		Assert.assertTrue(page.shoulderBagsPageHeading.isDisplayed());
	   
	}


}
