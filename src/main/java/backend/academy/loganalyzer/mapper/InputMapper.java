package backend.academy.loganalyzer.mapper;

import backend.academy.loganalyzer.dto.FileInputDTO;
import backend.academy.loganalyzer.dto.UrlInputDTO;
import backend.academy.loganalyzer.models.Input;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InputMapper {
    @Mapping(target = "url", source = "globOrUrl")
    UrlInputDTO inputToUrlInputDTO(Input input);

    @Mapping(target = "glob", source = "globOrUrl")
    FileInputDTO inputToFileInputDTO(Input input);
}
