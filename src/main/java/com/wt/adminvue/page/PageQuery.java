package com.wt.adminvue.page;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页查询
 *
 * @author tangjiawei
 * @date 2020/04/15
 */
public class PageQuery {
    /**
     * 当前页面
     */
    @ApiModelProperty("当前页面")
    public static final String CURRENT_PAGE = "page";

    /**
     * 排序条件
     */
    public static final String KEY_ORDER_BY = "orderBy";

    /**
     * 开始 index
     */
    public static final String KEY_START_INDEX = "startIndex";

    /**
     * pageSize
     */
    @ApiModelProperty("每页记录数")
    public static final String KEY_PAGE_SIZE = "pageSize";

    public static final String KEY_SORT_TYPE = "sortType";

    public static final String DIRECTION_ASC = "ASC";

    public static final String DIRECTION_DESC = "DESC";

    public static final Integer DEFAULT_PAGE_NO = 1;

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String CREATED = "created";

    public static final String STATUS = "status";

}
