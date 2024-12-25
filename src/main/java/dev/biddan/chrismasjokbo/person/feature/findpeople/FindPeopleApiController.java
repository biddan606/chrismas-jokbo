package dev.biddan.chrismasjokbo.person.feature.findpeople;

import dev.biddan.chrismasjokbo.person.feature.findpeople.FindPeopleService.FindPeopleInfo;
import dev.biddan.chrismasjokbo.person.feature.findpeople.FindPeopleService.FindPeopleQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindPeopleApiController {

    private final FindPeopleService findPeopleService;

    @GetMapping("/people")
    public ResponseEntity<FindPeopleInfo> findPeople(@ModelAttribute FindPeopleQuery request) {
        FindPeopleInfo info = findPeopleService.findByNameAndBirthdate(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(info);
    }
}
