package net.itxiu.foundation.page;

public class PageQuery {

    protected Integer start;
    protected Integer pageSize;
    protected Integer page;

    public PageQuery() {
    }

    public PageQuery(Integer start, Integer pageSize, Integer page) {
        this.start = start;
        this.pageSize = pageSize;
        this.page = page;
    }

    public Limit toLimit(){
        return start !=null
                ? new Limit(start,pageSize)
                : new Limit( (page - 1) * pageSize,pageSize);
    }

    public Integer totalPageWithTotalCount(Integer totalCount){
        return totalCount % pageSize == 0
                ? totalCount / pageSize
                : totalCount / pageSize + 1;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
