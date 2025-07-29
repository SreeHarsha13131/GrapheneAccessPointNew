Feature: Application Login
  @DashBoardRun

  Scenario Outline: Validate Login Scenario

    Given Open the Chrome Browser and Navigate to AccessPoint Login Page
    When User login to the AccessPoint Site using <UserName> and <Password>
    Then User will land on the Home page of the AccessPoint Site
    And Click on the Application Tab
    Then I click on the following Application and Validate the DashBoard Loading:
      |       ANZ           |
      |       AT&T Demo           |
      |       Cipla Contract Management - Order Form Demo           |
      |       Citibank           |
      |       Clinical Trials           |
      |       Coke           |
      |       Constellation - Price Watcher           |
      |       Coronis Performance           |
      |       Cost Management Report           |
      |       DBS 2018           |
      |       DBS 2019           |
      |       Duphaston           |
      |       Gillette           |
      |       HSBC           |
      |       Incident Management           |
      |       InfantCare UK Market Drivers           |
      |       Inspector Insight           |
      |       Medical Affairs           |
      |       Nestle           |
      |       P&G Quarterly Dashboard           |
      |       P&G R&R           |
      |       Pharma 360 Channel Tracker           |
      |       Price Watcher Demo           |
      |       Unilever Dove           |
      | Medical Affairs_prism         |

    
    Examples:
      | UserName |                             | Password |
      | Sreeharsha@graphenesvc.com |           | Hahsrah13131?1!|