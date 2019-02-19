# USE CASE: All the cities in a region organised by largest population to smallest

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *employee* of the organisation, *I want to sort each city in a region from largest population to smallest* so that *I can review this data.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the region. Database contains population data on cities within regions.

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
2. Region is input.
3. Report is generated with correct information.

## EXTENSIONS

2. **Region does not exist**:
    1. Application informs user region doesn't exist.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0