package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.models.Input;
import backend.academy.loganalyzer.validators.Validator;
import com.beust.jcommander.JCommander;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InputParser {
    public static Input parse(String[] args) {
        Input input = new Input();

        JCommander.newBuilder()
            .addObject(input)
            .build()
            .parse(args);

        Validator.validatePath(input.globOrUrl());
        Validator.validateDates(input.from(), input.to());
        Validator.validateFilter(input.filter(), input.filterValue());

        return input;
    }

}
