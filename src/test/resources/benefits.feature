#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@SOAI-5721
Feature: Benefits RAW Input to Output ETL Validation


  @SE-112870
  Scenario: Automated ETL validation of CS90_EHUB_PROD RAW Output table with RAW Input table(s)
  Given  Test Data is in RECEIVED state in CS90_CONTRACT_MASTER table
  When  Java Batch Engine processes provided Contract Codes
  | ETL_SQL_Pair | SE-112870 |
  Then Validate columns of RAW Input table CS90_CONTRACT_MASTER are moved to CS90_EHUB_PROD Table
  | ETL_SQL_Pair | SE-112870 |


