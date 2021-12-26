package com.wt.adminvue.page;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date
 */
@Data
public class PageQueryDTO {
    @ApiModelProperty(value = "当前页码")
    private Integer pageNo = PageQuery.DEFAULT_PAGE_NO;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize = PageQuery.DEFAULT_PAGE_SIZE;

    public int getStartIndex() {
        int startIndex = (this.pageNo - 1) * this.pageSize;
        return startIndex;
    }
}
