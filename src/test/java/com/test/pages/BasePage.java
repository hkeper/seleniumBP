package com.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BasePage {

    WebDriver driver;
    @CacheLookup
    @FindBy(how = How.CLASS_NAME, using = "card-up")
    private List<WebElement> cards;

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void clickCardElements(){
        cards.get(0).click();
    }


}
