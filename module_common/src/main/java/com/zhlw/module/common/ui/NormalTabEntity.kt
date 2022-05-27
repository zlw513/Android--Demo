package com.zhlw.module.common.ui

import com.flyco.tablayout.listener.CustomTabEntity

/**
 *
 * @Desc: tablayout,底部导航栏中一般会用到的数据
 * @Author:  zlw
 * @Date:  2022/05/16
 */
class NormalTabEntity() : CustomTabEntity{

    /**
     * 标题
     */
    var title: String = ""

    /**
     * 选中时icon
     */
    var selectedIcon: Int = 0

    /**
     * 未选中时icon
     */
    var unSelectedIcon: Int = 0

    constructor(title: String, selectedIcon: Int, unSelectedIcon: Int) : this() {
        this.title = title
        this.selectedIcon = selectedIcon
        this.unSelectedIcon = unSelectedIcon
    }

    override fun getTabTitle(): String = title

    override fun getTabSelectedIcon(): Int = selectedIcon

    override fun getTabUnselectedIcon(): Int = unSelectedIcon

}