Feature: Trade position book system tests

  Scenario: Buying Securities
    When I set http body with file fixtures/buying_and_selling_securities.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/buying_and_selling_securities.json

  Scenario: Buying Different Securities
    When I set http body with file fixtures/buying_different_securities.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/buying_different_securities.json

  Scenario: Buying and Selling Securities
    When  I set http body with file fixtures/buying_securities.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/buying_securities.json

  Scenario: Cancelling Buying event
    When I set http body with file fixtures/cancelling_buying_event.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/cancelling_buying_event.json

  Scenario: Cancelling Selling event
    When I set http body with file fixtures/cancelling_selling_event.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/cancelling_selling_event.json

  Scenario: Buying Securities and Fetching response using valid account and security
    When I set http body with file fixtures/buying_and_selling_securities.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/buying_and_selling_securities.json
    When Get Position details using /ACC1/SEC1
    Then Position http response code should be 200
    And Position Response is same to output in file results/position_buying_and_selling_securities.json

  Scenario: Buying Securities and Fetching response using invalid account and security
    When I set http body with file fixtures/buying_and_selling_securities.json
    Then http response code should be 200
    And http response header Content-Type should be application/json
    And Response is same to output in file results/buying_and_selling_securities.json
    When Get Position details using /abc/test
    Then Position http response code should be 404

  Scenario: Invalid Event
    When I set http body with invalid json "{aa: 1,abc:2,}"
    Then Invalid http response code should be 400