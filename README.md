# Wildebeest Keeper
***Wildebeest Keeper*** is a code analysis tool used to find irregularities with C/C++ based software
projects.

![](image/WildebeestKeeper.png)
___

## Metrics
| name                                 | calculation formula                                                                                                                                    |
|--------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| AMW (Average Method Weight)          | The average static complexity of all methods in a class. McCabe’s cyclomatic number is used to quantify the method’s complexity                        |
| WMC (Weighted Method Count)          | The sum of the statical complexity of all methods of a class. The CYCLO metric is used to quantify the method’s complexity                             |
| NOM (Number of Methods)              | The number of methods of a class                                                                                                                       |
| NOPA (Number of Public Attributes)   | The number of public attributes of a class                                                                                                             |
| NProtM (Number of Protected Members) | The number of protected methods and attributes of a class                                                                                              |
| ATFD (Access to Foreign Data)        | The number of attributes from unrelated classes that are accessed directly or by invoking accessor methods                                             |
| FDP (Foreign Data Provider)          | The number of classes in which the attributes accessed — in conformity with the ATFD metric — are defined                                              |
| WOC (Weight Of a Class)              | The number of “functional” public methods divided by the total number of public members                                                                |
| BOvR (Base Class Overriding Ratio)   | The number of methods of the measured class that override methods from the base class, divided by the total number of methods in the class             |
| CC (Changing Classes)                | The number of classes in which the methods that call the measured method are defined in                                                                |
| CM (Changing Methods)                | The number of distinct methods that call the measured method                                                                                           |
| CINT (Coupling Intensity)            | The number of distinct operations called by the measured operation                                                                                     |
| CDISP (Coupling Dispersion)          | The number of classes in which the operations called from the measured operation are defined, divided by CINT                                          |
| BUR (Baseclass Usage Ratio)          | The number of inheritance-specific members used by the measured class, divided by the total number of inheritance-specific members from the base class |
| HIT (Height In Tree)                 | The maximum path length from a class to its deepest subclass                                                                                           |
| DIT (Depth In Tree)                  | The length from the measured class to its highest defined superclass                                                                                   |
| NOC (Number Of Children)             | The amount of all classes that extend the measured class                                                                                               |
| RFC (Response For a Class)           | The sum of all the defined methods in the measured classed and all of the called methods from the measured class                                       |

more information here: https://link.springer.com/book/10.1007/3-540-39538-5
___

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

Still in development...
