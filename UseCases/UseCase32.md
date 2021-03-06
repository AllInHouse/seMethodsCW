# USE CASE: The top N populated countries in the world where N is provided by the user.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *employee* of the organisation, *I want to be able to find the top populated countries in the world, and be able to define the length of the list,* so that *I can review this data.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the length of the list. Database contains population data.

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
2. Report is generated with correct information.

## EXTENSIONS

2. **No list length is specified**:
    1. Application asks user to specify list length.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0