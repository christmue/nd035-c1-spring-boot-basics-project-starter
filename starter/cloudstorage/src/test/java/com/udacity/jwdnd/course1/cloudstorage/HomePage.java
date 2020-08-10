package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="note-submit-button")
    private WebElement noteSubmitButton;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="show-note-modal")
    private WebElement notesModal;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-new-credential-button")
    private WebElement addNewCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(xpath = ".//*[text()='Edit']")
    private WebElement editButton;

    @FindBy(xpath = ".//*[text()='Delete']")
    private WebElement deleteButton;


    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void addOrUpdateNote(String noteTitle, String noteDescription)  {
        this.noteTitleField.sendKeys(noteTitle);
        this.noteDescriptionField.sendKeys(noteDescription);
        this.noteSubmitButton.click();
    }

    public void clearInputNoteModal() {
        this.noteTitleField.clear();
        this.noteDescriptionField.clear();
    }

    public void navigateToNotesTab() {
        this.notesTab.click();
    }

    public void openNoteModal() {
        this.notesModal.click();
    }

    public void navigateToCredentialsTab() {
        this.credentialsTab.click();
    }

    public void openCredentialModal() {
        this.addNewCredentialButton.click();
    }

    public void addOrUpdateCredential(String url, String username, String password) {
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);
        this.credentialPasswordField.submit();
    }

    public void clearCredentialModal() {
        this.credentialUrlField.clear();
        this.credentialUsernameField.clear();
        this.credentialPasswordField.clear();
    }

    public void edit() {
        this.editButton.click();
    }

    public void delete() {
        this.deleteButton.click();
    }

}
