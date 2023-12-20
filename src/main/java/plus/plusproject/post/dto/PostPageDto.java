package plus.plusproject.post.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class PostPageDto {

    private Integer currentPage;
    private Integer size;
    private String sortBy;

    public Pageable toPageable(){
        return PageRequest.of(currentPage-1, size, Sort.by(sortBy).descending());
    }
}
