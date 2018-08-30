package steps;

import com.odde.massivemailer.model.Options;
import com.odde.massivemailer.model.Questions;
import com.odde.massivemailer.model.User;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import javax.xml.bind.Element;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionStep {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^User is in the test page$")
    public void user_is_in_the_test_page() {
        site.visit("question");
    }

    @Given("^User is in the top page$")
    public void user_is_in_the_top_page() throws Throwable {
        site.visit("");
    }

    @When("^User clicks \"([^\"]*)\" button$")
    public void user_clicks_button(String arg1) {
        site.visit("question");
    }

    @Then("^User go to the test page$")
    public void user_go_to_the_test_page() {
        driver.expectElementWithIdToContainText("description", "What is scrum?");
    }

    @Then("^User should see a question and options$")
    public void user_should_see_a_question_and_options(DataTable question) {
        Map<String, String> questionMap = question.asMap(String.class, String.class);
        driver.expectElementWithIdToContainText("description", questionMap.get("description"));
        driver.expectElementWithIdToContainText("option1", questionMap.get("option1"));
        driver.expectElementWithIdToContainText("option2", questionMap.get("option2"));
        driver.expectElementWithIdToContainText("option3", questionMap.get("option3"));
        driver.expectElementWithIdToContainText("option4", questionMap.get("option4"));
        driver.expectElementWithIdToContainText("option5", questionMap.get("option5"));
    }

    @When("^User chooses \"([^\"]*)\"$")
    public void user_chooses(String selected_option) {
        driver.clickById(selected_option);
    }

    @When("^User clicks the answer button$")
    public void user_clicks_the_answer_button() {
        driver.clickButton("answer");
    }

    @Then("^Move to \"([^\"]*)\" page$")
    public void move_to_page(String redirected_page) {
        driver.pageShouldContain(redirected_page);
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() {
        driver.clickById("option5");
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String pageName) {
        driver.expectElementWithIdToContainText("title", "End Of Test");
    }

    @When("^User chooses the \"([^\"]*)\" option$")
    public void user_chooses_the_option(String incorrectId) throws Throwable {
        driver.clickById(incorrectId);
    }

    @Then("^User go to the \"([^\"]*)\" page$")
    public void user_go_to_the_page(String pageName) {
        driver.pageShouldContain(pageName);
    }

    @Then("^User should see \"([^\"]*)\" option highlighted and text \"([^\"]*)\"$")
    public void user_should_see_option_highlighted_and_text(String clazz, String text) {
        String cssSelector = "." + clazz.replace(" ", ".");
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        assertEquals(elements.size(), 1);
        assertEquals(elements.get(0).getText(), text);
    }

    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see(String text) {
        driver.expectElementWithIdToContainValue("advice", text);
    }

    @Given("^a trainer enters the question edit page$")
    public void aTrainerEntersTheQuestionEditPage() {
        driver.visit(site.baseUrl() + "add_question.jsp");
    }

    @When("^trainer add a new question with description that have \"([^\"]*)\"$")
    public void trainerAddANewQuestionWithDescriptionThatHave(int description_length) {
        String description = "";
        for (int i = 0; i < description_length; i++) {
            description += "a";
        }
        driver.setTextField("description", description);
    }

    @When("^option(\\d+) is selected as correct answer$")
    public void optionIsSelectedAsCorrectAnswer(String optionId) {
        driver.clickButton("option"+optionId );
    }

    @Given("^User arrives at advice page$")
    public void user_arrives_at_advice_page() {
        site.visit("question");
        driver.clickById("option1");
        driver.clickButton("answer");
    }

    @When("^User clicks the next button$")
    public void user_clicks_the_next_button() {
        driver.clickButton("next");
    }

    @When("^sets default value$")
    public void setsDefaultValue() {
        driver.setTextField("description", "dumy description");
        driver.setTextField("option1", "dumy option1");
        driver.setTextField("option2", "dumy option2");
        driver.setTextField("advice", "dumy advice");
    }

    @When("^trainer add a new question with advice that have \"([^\"]*)\"$")
    public void trainerAddANewQuestionWithAdviceThatHave(int description_length) {
        driver.setTextField("description", getStringOfLength(description_length));
    }

    private String getStringOfLength(int descriptionLength) {
        String description = "";
        for (int i = 0; i < descriptionLength; i++) {
            description += "a";
        }
        return description;
    }

    @And("^trainer press the \"([^\"]*)\"$")
    public void trainerPressThe(String buttonName) {
        driver.clickButtonByName(buttonName);
    }

    @Then("^Error message \"([^\"]*)\" appears and stay at the same page$")
    public void errorMessageAppearsAndStayAtTheSamePage(String errorMessage) {
        driver.expectElementWithIdToContainValue("message", errorMessage);
    }

    @When("^trainer inputs question:$")
    public void trainerInputsQuestion(DataTable questionData) throws Throwable {
        List<List<String>> data = questionData.raw();
        driver.setTextField(data.get(0).get(0), data.get(0).get(1));
        driver.setTextField(data.get(1).get(0), data.get(1).get(1));
        driver.setTextField(data.get(2).get(0), data.get(2).get(1));
        driver.setTextField(data.get(3).get(0), data.get(3).get(1));
    }

    @And("^trainer set the option(\\d+) as the correct answer$")
    public void trainerSetTheOptionAsTheCorrectAnswer(int optionID) {
        driver.clickButton("option" + String.valueOf(optionID));
    }

    @Then("^trainer should see question in question list:$")
    public void trainerShouldSeeQuestionInQuestionList(DataTable questionData) {
        driver.visit(site.baseUrl() + "question_list.jsp");
        Map<String, String> questions = questionData.asMap(String.class, String.class);
        questions.forEach((column, value) -> {
            assertEquals(value, driver.findElementById(column).getText());
        });
    }

    @And("^row of option(\\d+) is green$")
    public void rowOfOptionIsGreen(int optionID) {
        String clazz = "bg-success";
        String cssSelector = "." + clazz.replace(" ", ".");
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        assertEquals(1, elements.size());
        assertEquals("option" + optionID + "row", elements.get(0).getAttribute("id"));
    }

    @Given("^There is a question \"([^\"]*)\"$")
    public void there_is_a_question(String arg1) throws Throwable {
    }

    @When("^User clicks \"([^\"]*)\" button on menu$")
    public void user_clicks_button_on_menu(String link) throws Throwable {
        driver.clickById("start_test");
    }

}
