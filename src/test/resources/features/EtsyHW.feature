Feature: Etsy Homework
  I want to use keys and mouse actions
  @EtsyHW
  Scenario: Mouse Actions
    Given I am on the Etsy "https://etsy.com" homepage
    And I Hover Over on Jewelry & Accessories
    Then I Mouseover on Bags & Purses
    And I Mouseover to Shoulder Bags
    When I Click on the shoulder bags
    Then I should Verify that I am on the Shoulder bags page
