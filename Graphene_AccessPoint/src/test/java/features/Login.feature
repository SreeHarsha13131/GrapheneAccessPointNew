Feature: Application Login
#  @ApplicationsRun

  Scenario Outline: Validate Login Scenario

    Given Open the Chrome Browser and Navigate to AccessPoint Login Page
    When User login to the AccessPoint Site using <UserName> and <Password>
    Then User will land on the Home page of the AccessPoint Site
    And Click on the Application Tab
    Then I click on the following site tiles and validate loading:
      | Advanced Tracker Demo |
      | AI: Corporate Insight |
      | Alliance Pharma RR Demo |
      |  AMGEN DEMO |
      | ANZ         |
      | AT&T Demo   |
      | Caveman Demo |
      | Cipla Contract Management - Order Form Demo |
      | Citibank                                    |
      | CLAROS STRATEGIC PLANNING                   |
      | Clinical Trials                             |
      | Coke                                        |
      | COKE AI                                     |
      | Colgate AI                                  |
      | Constellation - Price Watcher               |
      | Contract Management Demo                    |
      | Coronis                                     |
      | Coronis Image Capture                       |
      | Coronis Image Desktop                       |
      | Coronis Performance                         |
      | COSENTYX                                    |
      | Cost Management Report                      |
      | DBS 2018                                    |
      | DBS 2019                                    |
      | Demo of Pharma 2022 Track                   |
      | DIAGEO Demo                                 |
      | Duphaston                                   |
      | Ecomm Ezy Demo                              |
      | GIA Icecream demo INDIA                     |
      | GIA Icecream demo US                        |
      | GIA RR Demo                                 |
      | GIA Ruth Demo                               |
      | Gillette                                    |
      | HR 2.0 - ADANI GROUP                        |
      | HR 2.0 - Demo                               |
      | HR 2.0 - E.W. SCRIPPS USA                   |
      | HSBC                                        |
      | Incident Management                         |
      | InfantCare UK Market Drivers                |
      | Inspector Insight                           |
      | J&J India : Product Reviews                 |
      | JJVC Demo                                   |
      | JJVC PRESCRIPTION INSIGHTS                  |
      | Kearney KDP                                 |
      | LODESTAR DEMO                               |
      | Mashreq                                     |
      | Mavis HR 2.0 Demo (Cipla)                   |
      | Mavis HR D'frens Demo                       |
      | Mavis RR v2 - APDO                          |
      | Medical Affairs                             |
      | Medical Affairs_prism                       |
      | Nestle                                      |
      | Nicotex Demo                                |
      | Ninlaro Drug Performance                    |
      | OCR Demo                                    |
      | Old Spice APDO Demo                         |
      | Omnichannel Tracker 2023                    |
      | P&G Quarterly Dashboard                     |
      | P&G R&R                                     |
      | Pharma 360 Channel Tracker                  |
      | Pharma AI: Category Content                 |
      | Pharma AI: Doctor & Patient Insight         |
      | Pharma AI: Regulatory Compliance            |
      | Pharma Intelligent Monitor Version1         |
      | Pharma Intelligent Monitor Version2         |
      | Price Watcher Demo                          |
      | R&R 2.0 - Bayer                             |
      | R&R 2.0 - Church & Dwight                   |
      | R&R 2.0 - Constellation                     |
      | R&R 2.0 - Demo                              |
      | Ratings & Reviews - APDO                    |
      | Roddy Knowles                               |
      | Salonpas 2021 Demo                          |
      | Salonpas AI                                 |
      | Salonpas AI UAT                             |
      | Sanofi Dupixent                             |
      | Sanofi MCE 2022                             |
      | Sanofi MCE 2024                             |
      | Sanofi MCE 2024 UAT                         |
      | SCJ AI                                      |
      | SG Govt App Reviews Demo                    |
      | Skincare China-Japan                        |
      | TAKEDA 2021 TEST                            |
      | Takeda AI                                   |
      | Total B2C                                   |
      | Unbiased Insights                           |
      | Unbiased Insights Demo                      |
      | Unilever Dove                               |
      | Vista Health                                |

    Examples:
      | UserName |                             | Password |
      | Sreeharsha@graphenesvc.com |           | Hahsrah13131?1!|
