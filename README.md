# DecimalExpr
**_A precise expression evaluator for Java_**

## Classes

**_DecimalExpr_** is based on a new set of mathematical numbers, 
which is implemented in package 'trashsoftware.numbers'

Package 'trashsoftware.numbers' has

* Interface `Real` which is the real number.
* Class `Rational` implements `Real`
* Class `Irrational` implements `Real`

## Instructions

## Examples

#### Example 1:
Simple expression evaluation.
```
DecimalExpr expr = new DecimalExprBuilder("2 + 3").build();
Real result = expr.evaluate();
```
Which the `result` is a `Rational` containing value `5`.

#### Example 2:
Expression with variables.
```
DecimalExpr expr = new DecimalExprBuilder("x + 1")
        .variable("x")
        .build()
        .setVariable("x", 3);
Real result = expr.evaluate();
```
Which the `result` is a `Rational` containing value `4`.

#### Example 3:
Custom functions.
```
Function exp = new Function("exp", 1) {
    @Override
    public Real eval(Real... numbers) {
        return Irrational.E.power(numbers[0]);
    }
};

DecimalExpr expr = new DecimalExprBuilder("exp(x)")
        .variable("x")
        .function(exp)
        .build()
        .setVariable("x", 2);
Real result = expr.evaluate();
```
Which the `result` will be assign to an `Irrational` containing value
approximately `7.38905609893065`.
