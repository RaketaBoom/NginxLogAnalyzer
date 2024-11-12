package backend.academy.loganalyzer.mapper;

import backend.academy.loganalyzer.dto.FileInputDTO;
import backend.academy.loganalyzer.dto.UrlInputDTO;
import backend.academy.loganalyzer.models.Input;

public class InputMapper {

    public UrlInputDTO inputToUrlInputDTO(Input input) {
        return new UrlInputDTO(
            input.globOrUrl(),
            input.from(),
            input.to(),
            input.format(),
            input.filter(),
            input.filterValue()
        );
    }

    public FileInputDTO inputToFileInputDTO(Input input) {
        return new FileInputDTO(
            input.globOrUrl(),
            input.from(),
            input.to(),
            input.format(),
            input.filter(),
            input.filterValue()
        );
    }
}
