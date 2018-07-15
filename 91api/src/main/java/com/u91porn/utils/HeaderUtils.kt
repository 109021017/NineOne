package com.u91porn.utils

/**给每个请求添加对应的referer header，一定程度上能改善超时现象
 * @author flymegoc
 * @date 2018/1/2
 */

object HeaderUtils {

    /**
     * 来自主页的header
     *
     * @return header
     */
    val indexHeader: String
        get() = AddressHelper.instance?.video91PornAddress + "index.php"

    /**
     * 收藏
     * @return header
     */
    val favHeader: String
        get() = AddressHelper.instance?.video91PornAddress + "my_favour.php"

    /**
     * 来自播放列表的header
     *
     * @param viewKey 视频key
     * @return header
     */
    fun getPlayVideoReferer(viewKey: String): String {
        return AddressHelper.instance?.video91PornAddress + "view_video.php?viewkey=" + viewKey
    }

    /**
     * 获取用户header
     * @param action login or register
     * @return header
     */
    fun getUserHeader(action: String): String {
        return AddressHelper.instance?.video91PornAddress + action + ".php"
    }
}
