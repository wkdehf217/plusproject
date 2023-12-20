package plus.plusproject.post.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostPageRequestDto {
    private Integer currentPage;
    private Integer size;
    private String sortBy;
}
