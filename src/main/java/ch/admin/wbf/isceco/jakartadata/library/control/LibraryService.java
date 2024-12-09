package ch.admin.wbf.isceco.jakartadata.library.control;

import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorFilterBean;
import ch.admin.wbf.isceco.jakartadata.library.control.filter.AuthorWithBookFilter;
import ch.admin.wbf.isceco.jakartadata.library.entity.Author;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.page.impl.PageRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LibraryService {

    @Inject
    LibraryRepository repository;

    final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Page<AuthorDto> findAll(AuthorWithBookFilter authorCriteria, PageRequest pageRequest) {

        // Use the filter in a specification
        AuthorSpecification spec = new AuthorSpecification();
        spec.addSpecification(AuthorSpecification.matchesName(authorCriteria.name()));
        spec.addSpecification(AuthorSpecification.hasBooksWithIsbns(authorCriteria.bookIsbns()));

        // Get a page given the specification and a page request.
        Page<Author> authorsPage = repository.findAll(spec, pageRequest);

        List<Author> content = authorsPage.content();

        List<AuthorDto> authorDtoList = content.stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());

        return new PageRecord<>(pageRequest, authorDtoList, authorsPage.totalElements());
    }

    @Transactional
    public Page<AuthorDto> findAllWithAuthorFilterBean(AuthorFilterBean authorCriteria, PageRequest pageRequest) {

        // Use the filter in a specification
        var spec = new SimplifiedAuthorSpecification(authorCriteria);

        // Get a page given the specification and a page request.
        Page<Author> authorsPage = repository.findAll(spec, pageRequest);

        List<Author> content = authorsPage.content();

        List<AuthorDto> authorDtoList = content.stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());

        return new PageRecord<>(pageRequest, authorDtoList, authorsPage.totalElements());
    }
    @Transactional
    public Page<AuthorDto> findAllWithAuthorFilterBeanAndCursoredPageRequest(AuthorFilterBean authorCriteria, PageRequest pageRequest) {

        // Use the filter in a specification
        var spec = new SimplifiedAuthorSpecification(authorCriteria);

        // Get a page given the specification and a page request.
        Page<Author> authorsPage = repository.findAll(spec, pageRequest);

        List<Author> content = authorsPage.content();

        List<AuthorDto> authorDtoList = content.stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());

        return new PageRecord<>(pageRequest, authorDtoList, authorsPage.totalElements());
    }


}
