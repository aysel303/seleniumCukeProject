package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class EtsyPage {
	public EtsyPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	@FindBy (xpath = "//li/a[@href='/c/jewelry-and-accessories?ref=catnav-10855']")
	public WebElement jeweleryAndAccessoriesTab;
	
	@FindBy (id="side-nav-category-link-10865")
	public WebElement bagsAndPursesLink;
	
	@FindBy (id="catnav-l4-10869")
	public WebElement shoulderBagsLink;
	
	@FindBy (xpath="//h1[text()='Shoulder Bags']")
	public WebElement shoulderBagsPageHeading;
	

}
