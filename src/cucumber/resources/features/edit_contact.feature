Feature: Edit Contact
  As the admin I want to edit contacts,
  so that I can sent newsletters to them later.

  @developing
  Scenario: Edit Location Information of Contact
    Given There is a contact "terry@odd-e.com"
    When I change the location information of contact to be "Singapore"
    Then contact "terry@odd-e.com"'s locations should be "Singapore"




