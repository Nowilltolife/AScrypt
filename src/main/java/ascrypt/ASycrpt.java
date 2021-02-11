package ascrypt;

import ascrypt.parser.Parser;
import ascrypt.runtime.ASCRRuntime;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class ASycrpt {

    public static void main(String[] args) throws IOException {
        File file = new File("code.ascr");
        long l = System.currentTimeMillis();
        Parser parser = new Parser(new String(Files.readAllBytes(file.toPath())));
        parser.parse();
        file = new File("code.cascr");
        if(!file.exists())
            file.createNewFile();
        String compiledCode = parser.parseInstructions();
        System.out.println("Compilation of code took: " + (System.currentTimeMillis() - l) + "ms");
        Files.write(file.toPath(), compiledCode.getBytes());
        ASCRRuntime runtime = new ASCRRuntime(compiledCode);
        runtime.createVM();
        ASCRRuntime.VM vm = runtime.vm;
        int currentStep = 1;
        long l1 = 0L;
        while (true) {
            l = System.currentTimeMillis();
            boolean b = vm.tick();
            l1 += (System.currentTimeMillis() - l);
            if(!b) {
                System.out.println("Execution of code completed in: " + l1 + "ms");
                break;
            }
            currentStep++;
        }
    }

}
