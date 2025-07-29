Feature: Constellation - Price Watcher
#  @DashBoardRun
  Scenario Outline: Validate All the tabs in the Constellation - Price Watcher

    Given Open the Chrome Browsers and Navigate to AccessPoint Login Pages
    When User login to the AccessPoint Sites usings <UserName> and <Password>
    Then User will land on the Home pages of the AccessPoint Sites
    And Click on the Applications Tabs
    Then I click on the following Applications and Validate the DashBoard Loadings:
      |       Constellation - Price Watcher           |

    Examples:
      | UserName |                             | Password |
      | Sreeharsha@graphenesvc.com |           | Hahsrah13131?1!|
