package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class AuthorMappingNoBooksConfig {
    public static void configure(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Author, AuthorDto>() {
            @Override
            protected void configure() {
                skip(destination.getBooks());
            }
        });
    }
}
