package ascrypt.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Block {

    String name;
    String instructions;

    public void invoke(ASCRRuntime.VM vm, String... params) {
        vm.currentClassContext = name;
        vm.currentContext = name;
        for(String instruction:instructions.split("\n"))
            vm.executeInstruction(instruction);
    }

}
