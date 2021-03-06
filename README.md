me4j
=============

Java library for parsing and evaluating mathematical expression strings

---

## Usage

There are two interfaces to the me4j API: `Expression` and `MathEvaluator`.  Current functionality is identical between
the two, but the `Expression` class has been included to support variables and additional functionality in the future.
The `MathEvaluator` class is a static utility class intended for one-shot evaluation.

To evaluate a String using an `Expression`, create a new instance by passing the expression string, and call
`evaluate()` on the new instance:

```java
Expression myExpression = new Expression ("1 + 1");
double shouldBeTwo = myExpression.evaluate ();
```

To evaluate a String using the `MathEvaluator` class, call the static `evaluate()` method and pass in the string.  In
the background, the `MathEvaluator` class is performing the same operation as above.

```java
double shouldBeTwo = MathEvaluator.evaluate ("1 + 1");
```

## Supported Operator Symbols

### Operations
- Addition ("+")
- Subtraction ("-")
- Multiplication ("*")
- Division ("/")
- Exponentiation ("^")

### Grouping
- Parentheses ("()")
- Square Brackets ("[]")
- Curly Brackets ("{}")

## Formatting

The latest revision (4/19/14) removes the whitespace delimiter requirement, so any string, with or without whitespace, should
be properly parsed.

## Future Plans

In no particular order

- Add support for parsing operands written in scientific notation
- Unary operators, such as negation ( - ) and factorial ( ! ) -- in progress
- Support common functions like sine/cosine/tangent
- Support custom functions
- Support variables (operands whose values can be defined after parsing)
- Support expression reusability (similar to java.sql.PreparedStatement)