package net.itxiu.foundation.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageQueryResult<Data> {

    private boolean hasNext;
    private Integer totalCount;
    private Integer totalPage;
    private Integer currentPage;
    private Integer offset;
    private Integer pageSize;
    private List<Data> recordList;

}
