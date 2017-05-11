package net.itxiu.foundation.page;

public class Limit {

    private Integer offset;
    private Integer size;

    public Limit(Integer offset, Integer size) {
        this.offset = offset;
        this.size = size;
    }

    public boolean hasNextWithTotalCount(Integer totalCount){
        return (offset + size) < totalCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
