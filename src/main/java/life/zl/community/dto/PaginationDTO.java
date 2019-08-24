package life.zl.community.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {

    private List<QuestDTO> questDTOList;
    private boolean showPrevious;
    private boolean firstPage;
    private boolean showNext;
    private boolean endPage;
    private Integer currentPage;
    private Integer page;
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    private List<Integer> pages =  new ArrayList<>();

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "questDTOList=" + questDTOList +
                ", showPrevious=" + showPrevious +
                ", firstPage=" + firstPage +
                ", showNext=" + showNext +
                ", endPage=" + endPage +
                ", currentPage=" + currentPage +
                ", pages=" + pages +
                '}';
    }

    public List<QuestDTO> getQuestDTOList() {
        return questDTOList;
    }

    public void setQuestDTOList(List<QuestDTO> questDTOList) {
        this.questDTOList = questDTOList;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isEndPage() {
        return endPage;
    }

    public void setEndPage(boolean endPage) {
        this.endPage = endPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setPagination(Integer page, Integer totalCount, Integer size) {
        this.totalPage = totalCount%size == 0 ? totalCount/size:totalCount/size+1 ;
        this.page = page;

        pages.add(page);
        for(int i=1;i<3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        if(page==1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        if (pages.contains(1)){
            firstPage = false;
        }else {
            firstPage = true;
        }
        if (pages.contains(totalPage)){
            endPage = false;
        }else {
            endPage = true;
        }


    }
}
