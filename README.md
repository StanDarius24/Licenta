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

Still in development...