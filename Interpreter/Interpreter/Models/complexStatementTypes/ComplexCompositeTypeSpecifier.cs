using System.Collections.Generic;
using Interpreter.Models;

public class ComplexCompositeTypeSpecifier: IStatement
{
    public CompositeTypeSpecifier our_class;

    public string path;

    public IList<IStatement> declarations = new List<IStatement>();
    
}