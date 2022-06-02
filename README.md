# Wildebeest Keeper
***Wildebeest Keeper*** is a code analysis tool used to find irregularities with C/C++ based software
projects.

![](image/WildebeestKeeper.png)

Historically, the suffix for a C++ source file was .C. This caused a few problems the first time C++ was ported to a system where case wasn't significant in the filename.

Different users adopted different solutions: .cc, .cpp, .cxx and possibly others. Today, outside of the Unix world, it's mostly .cpp. Unix seems to use .cc more often.

For headers, the situation is even more confusing: for whatever reasons, the earliest C++ authors decided not to distinguish between headers for C and for C++, and used .h.

This doesn't cause any problems if there is no C in the project, but when you start having to deal with both, it's usually a good idea to distinguish between the headers which
can be used in C (.h) and those which cannot (.hh or .hpp).

In addition, in C++, a lot of users (including myself) prefer keeping the template sources and the inline functions in a separate file. Which, while strictly speaking a header file,
tends to get yet another set of conventions (.inl, .tcc and probably a lot of others).

In the case of headers it makes absolutely no difference to the compiler.

In the case of source files different endings will cause the compiler to assume a different language. But this can normally be overridden.

In this project I tried to cover all types of suffixes for this language.

___

## Metrics
|name|calculation formula|
|----|-------------------|
|AMW (Average Method Weight)|The sum of all of the methods' cyclomatic complexity divided by the number of methods
|WMC (Weighted Method Count)|The sum of all of the methods' cyclomatic complexity
|NOM (Number of Methods)|The methods defined in the class
|NOPA (Number of Public Attributes)|The number of public fields defined in the class
|NProtM (Number of Protected Members)|The number of protected methods and fields defined in the class
|ATFD (Access to Foreign Data)|The sum of all public accessed attributes (outside of constructors) from inside a class
|ATFD2 (Access to Foreign Data 2)|The sum of all public accessed attributes which have a user-defined type (outside of constructors) from inside a class
|FDP (Foreign Data Provider)|The classes in which the attributes for ATFD are provided
|WOC (Weight Of a Class)|The number of functional public methods that are not divided by the total number of public members
|BOvR (Base Class Overriding Ratio)|The number of overriding methods divided by the total number of methods in the class
|CC (Changing Classes)|The number of classes that call methods defined in the class
|CM (Changing Methods)|The number of methods that call methods defined in the class
|CINT (Coupling Intensity)|The number of methods called by the class
|CDISP (Coupling Dispersion)|The number of classes in which the methods that are called by the class are defined, divided by CINT
|BUR (Baseclass Usage Ratio)|The number of protected fields and methods that are used inside the measured class and are defined in the superclass of the measured class divided by the total protected members defined in the superclass
|HIT (Height In Tree)|The length from the measured class to its deepest subclass
|DIT (Depth In Tree)|The length from the measured class to its highest defined superclass
|NOC (Number Of Children)|The amount of all classes that extend the measured class
|RFC (Response For a Class)|The sum of all the defined methods in the measured classed and all of the called methods from the measured class

Still in development...
