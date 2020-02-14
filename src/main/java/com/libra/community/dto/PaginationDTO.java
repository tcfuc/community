package com.libra.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 * @date 2020/2/11
 * @time 11:29
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalPage, Integer currentPage) {
//        是否展示上一页按钮
        showPrevious = currentPage == 1 ? false : true;
//        是否展示下一页按钮
        showNext = currentPage == totalPage ? false : true;
//        是否展示第一页按钮
        showFirstPage = currentPage <= 4 ? false : true;
//        是否展示最后一页按钮
        showEndPage = totalPage <= 7 || totalPage - currentPage <= 3 ? false : true;
 
//        计算页码
        int firstPage = currentPage - 4 >= 0 ? currentPage - 3 : 1;
        int endPage = totalPage - currentPage >= 3 ? currentPage + 3 : totalPage;
        for (page = firstPage; page <= endPage; page++) {
            this.pages.add(page);
        }
    }

}
