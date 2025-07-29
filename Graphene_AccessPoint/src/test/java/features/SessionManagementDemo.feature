Feature: Session Management Demo
  As a test automation engineer
  I want to demonstrate session management functionality
  So that I can verify authentication works without repeated mobile PIN verification

  Background:
    Given Open the Chrome Browser and Navigate to AccessPoint Login Pages

#  @SessionDemo
  Scenario Outline: Login with Session Management
    When User login to the AccessPoint Site usings <UserName> and <Password>
    Then User will land on the Home page of the AccessPoint Sites
    And Click on the Application Tabs
    Then I click on the following Application and Validate the DashBoard Loadings:
      | Constellation Price Watcher |

    Examples:
      | UserName                    | Password      |
      | Sreeharsha@graphenesvc.com | Hahsrah13131?1! |

  @SessionReuse
  Scenario: Verify Session Reuse
    # This scenario should run immediately after the first one
    # and should NOT require mobile PIN verification
    When User login to the AccessPoint Site usings Sreeharsha@graphenesvc.com and Hahsrah1313?1!
    Then User will land on the Home page of the AccessPoint Sites
    And Click on the Application Tabs
    Then I click on the following Application and Validate the DashBoard Loadings:
      | Constellation Price Watcher |

  @SessionValidation
  Scenario: Session Management Validation
    # This scenario validates that session management components work
    Given Open the Chrome Browser and Navigate to AccessPoint Login Pages
    When User login to the AccessPoint Site usings Sreeharsha@graphenesvc.com and Hahsrah1313?1!
    Then User will land on the Home page of the AccessPoint Sites
    # At this point, session should be saved and reusable for next test runs
