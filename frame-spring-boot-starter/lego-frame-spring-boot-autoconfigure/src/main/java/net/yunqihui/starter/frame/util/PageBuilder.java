package net.yunqihui.starter.frame.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description page快速构建
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/5 14:50
 **/
public class PageBuilder {

    /**
     * @desc: 根据request快速初始化page对象，默认根据id倒序查询
     * @param request -- >pageIndex 页码 pageSize每页数据量
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/11/5 14:59
     * @update:
     */
    public static Page instancePage(HttpServletRequest request) {
        return instancePageOrderBy(request,"DESC","id") ;
    }

    /**
     * @desc: 根据request快速初始化page对象，查询排序为倒序
     * @param request request -- >pageIndex 页码 pageSize每页数据量
     * @param columns 排序字段
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/11/5 18:41
     * @update:
     */
    public static Page instancePageOrderByDesc(HttpServletRequest request,String... columns) {
        return instancePageOrderBy(request,"DESC",columns) ;
    }

    /**
     * @desc: 根据request快速初始化page对象，查询排序为正序
     * @param request request -- >pageIndex 页码 pageSize每页数据量
     * @param columns 排序字段，注意：Bean内字段
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/11/5 18:41
     * @update:
     */
    public static Page instancePageOrderByAsc(HttpServletRequest request,String... columns) {
        return instancePageOrderBy(request,"ASC",columns) ;
    }


    /**
     * @desc: 根据request快速初始化page对象
     * @param request request -- >pageIndex 页码 pageSize每页数据量
     * @param sort 排序规则  DESC | ASC
     * @param columns 参与排序的字段，注意：Bean内字段
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/11/5 18:43
     * @update:
     */
    public static Page instancePageOrderBy(HttpServletRequest request,String sort,String... columns) {
        String pageIndexStr = request.getParameter("pageIndex");
        String pageSizeStr = request.getParameter("pageSize");
        int pageIndex = 1 ;
        int pageSize = 10 ;

        if (StringUtils.isNotBlank(pageIndexStr)) {
            pageIndex = Integer.valueOf(pageIndexStr);
        }

        if (StringUtils.isNotBlank(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }

        Page page = new Page(pageIndex, pageSize);
        // 构建排序规则
        if (ArrayUtils.isEmpty(columns)) {

            for (int i = 0; i < columns.length; i++) {

                if ("ASC".equals(sort.toUpperCase())) {
                    page.setOrders(OrderItem.ascs(columns));
                }else {
                    // sort为null甚至入参不符合规范时皆默认倒叙
                    page.setOrders(OrderItem.descs(columns));
                }
            }
        }
        return page ;
    }
}
