package com.wt.adminvue.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.adminvue.entity.Menu;
import com.wt.adminvue.entity.SysMenuModel;
import com.wt.adminvue.exception.GuliException;
import com.wt.adminvue.mapper.MenuMapper;
import com.wt.adminvue.mapper.UserMapper;
import com.wt.adminvue.service.IMenuService;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.ResultCode;
import com.wt.adminvue.util.ResultGenerator;
import com.wt.adminvue.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    @Override
    public List<SysMenuModel> getCurrentUserNav() {
        Long userId = UserUtil.getLoginUser().getUserId();
        List<Long> menuIds = userMapper.getNavMenuIds(userId);

        List<Menu> menus = baseMapper.selectBatchIds(menuIds);

        // 转树状结构
        List<Menu> menuTree = buildTreeMenu(menus);

        // 实体转DTO
        return convert(menuTree);
    }

    @Override
    public List<Menu> tree() {
        // 获取所有菜单信息
        List<Menu> sysMenus = this.list(new QueryWrapper<Menu>().orderByAsc("orderNum"));

        // 转成树状结构
        return buildTreeMenu(sysMenus);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer deleteMenu(Long id) {

        int count = baseMapper.selectCount(new QueryWrapper<Menu>().eq("parent_id", id));
        if (count > 0) {
            throw new GuliException(ResultCode.NOT_MENU_CHIN);
        }

        // 清除所有与该菜单相关的权限缓存
        userService.clearUserAuthorityInfoByMenuId(id);

        baseMapper.deleteById(id);

        // 同步删除中间关联表
        //new QueryWrapper<SysRoleMenu>().eq("menu_id", id)
       return baseMapper.removeMenuRoleById(id);
    }


    private List<SysMenuModel> convert(List<Menu> menuTree) {
        List<SysMenuModel> menuDtos = new ArrayList<>();

        menuTree.forEach(m -> {
            SysMenuModel dto = new SysMenuModel();

            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());

            if (m.getChildren().size() > 0) {

                // 子节点调用当前方法进行再次转换
                dto.setChildren(convert(m.getChildren()));
            }

            menuDtos.add(dto);
        });

        return menuDtos;
    }
    private List<Menu> buildTreeMenu(List<Menu> menus) {

        List<Menu> finalMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (Menu menu : menus) {

            for (Menu e : menus) {
                if (menu.getId().intValue() == e.getParentId().intValue()) {
                    menu.getChildren().add(e);
                }
            }

            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }

        System.out.println(JSONUtil.toJsonStr(finalMenus));
        return finalMenus;
    }
}
