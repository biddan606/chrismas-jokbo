package dev.biddan.chrismasjokbo.person.feature.createperson;


import dev.biddan.chrismasjokbo.person.feature.createperson.CreatePersonService.CreatePersonCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class CreatePersonApiController {

    private final CreatePersonService createPersonService;

    @PostMapping
    public ResponseEntity<CreatePersonResponse> create(@RequestBody CreatePersonCommand request) {
        Long newPersonId = createPersonService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreatePersonResponse(newPersonId));
    }

    public record CreatePersonResponse(
            Long newPersonId
    ) {

    }
}
