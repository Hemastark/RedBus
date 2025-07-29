@redbus
Feature: Validating the Bus Booking functionality

  @login
  Scenario: Redbus portal navigation
    Given Navigate to the Redbus portal
    Then verify the Landing page

  Scenario Outline: Searching the available Buses
    Given Enter the Source <source> and Destination <destination>
    Given Enter Source and Destination from Excel file
    And Select the required Date <date>
    When Click the Search buses option
    Then Verify if the results are fetched

    Examples:
      | source   | destination | date |
      | Kotagiri | Chennai     | 30   |

  Scenario: Analysing the Search result
    Given Collect the Bus list
    Then Select the Bus with lowest price