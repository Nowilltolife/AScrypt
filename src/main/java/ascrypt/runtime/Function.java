package ascrypt.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Function {

    String instructions;
    String functionName;
    Parameter[] parameters;

    public Function(String instructions, String functionName, Parameter... parameters) {
        this.instructions = instructions;
        this.functionName = functionName;
        this.parameters = parameters;
    }

    public void invoke(ASCRRuntime.VM vm, String... params) {
        for (int i = 0; i < parameters.length; i++) {
            String obj = params[i];
            Parameter param = parameters[i];
            Object value = param.type.attemptCast(obj);
            vm.variableSpace.add(new Variable(param.type, param.name, value, functionName));
        }
        vm.currentContext = functionName;
        for(String instruction:instructions.split("\n"))
            vm.executeInstruction(instruction);
        for (Parameter parameter : parameters) {
            vm.unAllocVariable(parameter.name);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Parameter {

        String name;
        Type type;
        Object defaultValue;

    }

}
