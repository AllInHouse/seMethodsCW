# USE CASE: The top N populated cities in a country where N is provided by the user.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *employee* of the organisation, *I want to be able to find the top populated cities in a country, and be able to define the length of the list,* so that *I can review this data.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the country. We know the length of the list. Database contains population data for cities in a country.

### Success End Condition

A report is available for the employee to view.

### Failed End Condition

No report is produced.

### Primary Actor

Employee.

### Trigger

A request for the information is given.

## MAIN SUCCESS SCENARIO

1. Report is requested.
2. Country is input.
3. List size is input.
4. Report is generated with correct information.

## EXTENSIONS

2. **Country does not exist**
    1. Application informs user country does not exist.

3. **List length is not defined**
    1. Application asks user to input list length.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0