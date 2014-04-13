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

## Formatting

The current parser uses a whitespace delimiter when parsing tokens (individual symbols) from the expression string.
Therefore, each symbol must be separated by whitespace.  For example:

    Good:
    "( ( 2 * 3 ) + 4 ) / 5"

    Bad:
    "((2 * 3) + 4) / 5"

## Future Plans

In no particular order

- Exponent operator (^)
- Improve parser to remove whitespace requirement
- Add support for parsing operands written in scientific notation
- Unary operators, such as negation ( - ) and factorial ( ! )
- Support common functions like sine/cosine/tangent
- Support custom functions
- Support variables (operands whose values can be defined after parsing)
- Support expression reusability (similar to java.sql.PreparedStatement)