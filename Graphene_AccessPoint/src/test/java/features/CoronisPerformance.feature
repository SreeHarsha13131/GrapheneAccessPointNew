Feature: Coronis Performance
#  @DashBoardRun
  Scenario Outline: Validate All the tabs in the Coronis Performance

    Given Open the Chrome Browsers and Navigate to AccessPoint Login Pages
    When User login to the AccessPoint Sites usings <UserName> and <Password>
    Then User will land on the Home pages of the AccessPoint Sites
    And Click on the Applications Tabs
    Then I click on the following Applications and Validate the Coronis Performance DashBoard Loadings:
      |       Coronis Performance           |

    Examples:
      | UserName |                             | Password |
      | Sreeharsha@graphenesvc.com |           | Hahsrah13131?1!|
