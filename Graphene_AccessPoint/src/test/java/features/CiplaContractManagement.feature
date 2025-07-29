Feature: Cipla Contract Management - Order Form Demo
#  @DashBoardRun
  Scenario Outline: Validate Login Scenario

    Given Open the Chrome Browser and Navigate to AccessPoint Login Pages
    When User login to the AccessPoint Site usings <UserName> and <Password>
    Then User will land on the Home page of the AccessPoint Sites
    And Click on the Application Tabs
    Then I click on the following Application and Validate the DashBoard Loadings:
     |       Cipla Contract Management - Order Form Demo           |


    Examples:
      | UserName |                             | Password |
      | Sreeharsha@graphenesvc.com |           | Hahsrah13131?1!|
