using System.Collections.Generic;
using Interpreter.Models;

public class ComplexCompositeTypeSpecifier: IStatement
{
    public CompositeTypeSpecifier our_Class;

    public string path;

    public IList<Name> library = new List<Name>();

}