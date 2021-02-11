package ascrypt.runtime;

import ascrypt.parser.Parser;
import ascrypt.util.ArrayUtil;
import lombok.AllArgsConstructor;

import javax.lang.model.element.VariableElement;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ASCRRuntime {

    public String instructions;
    public VM vm;

    public ASCRRuntime(String instructions) {
        this.instructions = instructions;
    }

    public void createVM() {
        vm = new VM(instructions);
    }

    public static class VM {

        public final Queue<String> instructions = new ConcurrentLinkedDeque<>();
        public final List<Variable> variableSpace = new ArrayList<>();
        public final List<Function> functionSpace = new ArrayList<>();
        public final HashMap<Integer, String> stackRef = new HashMap<>();
        public HashMap<String, String> functionInstCache = new HashMap<>();
        public int currentLine;
        public String currentFunction = "";
        public String currentContext = "global";
        public String currentClassContext = "";

        public VM(String instructionSet) {
            instructions.addAll(Arrays.asList(instructionSet.split(Parser.lineSeparator)));
        }

        public void assertVariable(Variable variable) {
            variableSpace.add(variable);
        }

        public void unAllocVariable(String varName) {
            int i = 0;
            for(Variable variable:variableSpace) {
                if(variable.name.equals(varName))
                    break;
                i++;
            }
            variableSpace.remove(i);
        }

        public Variable findVariable(String name) {
            for (Variable variable : variableSpace) {
                if(variable.name.equals(name) && variable.context.equals(currentContext))
                    return variable;
            }
            return null;
        }

        public Function findFunction(String name) {
            for (Function variable : functionSpace) {
                if(variable.functionName.equals(name))
                    return variable;
            }
            return null;
        }

        public void executeInstruction(String instruction) {
            String[] split = instruction.split("\u0000");
            String instrId = split[0];
            switch (instrId) {
                case "1":
                    String varName = split[1];
                    String varType = split[2];
                    String value = ArrayUtil.arrayToString(split, " ", 3);
                    Type type = Types.getByByte(varType);
                    Object attemptedValue = type.attemptCast(value);
                    if(attemptedValue == null)
                        assertVariable(new Variable(Types.nullType, varName, null, currentContext));
                    else
                        assertVariable(new Variable(type, varName, attemptedValue, currentContext));
                    break;
                case "2":
                    String str = ArrayUtil.arrayToString(split, " ", 1);
                    System.out.println(str);
                    break;
                case "3":
                    varName = split[1];
                    Variable var = findVariable(varName);
                    if(var == null)
                        throw new RuntimeException("Variable " + varName + " not found at line " + currentLine);
                    var.value = var.type.attemptCast(ArrayUtil.arrayToString(split, " ", 2));
                    break;
                case "4":
                    String functionName = split[1];
                    functionInstCache.put(functionName, "");
                    currentFunction = functionName;
                    break;
                case "5":
                    functionName = split[1];
                    Function function = findFunction(functionName);
                    function.invoke(this);
                    break;
                default:
                    break;
            }
        }

        public boolean tick() {
            String inst = instructions.poll();
            if(inst == null)
                return false;
            currentLine++;
            if(currentFunction.isEmpty())
                executeInstruction(inst);
            else {
                String[] split = inst.split("\u0000");
                if(split[0].equals("6")) {
                    Function func = new Function(functionInstCache.get(currentFunction), currentFunction);
                    functionSpace.add(func);
                    currentFunction = "";
                }else
                functionInstCache.put(currentFunction, inst);
            }
            return true;
        }



    }


}
