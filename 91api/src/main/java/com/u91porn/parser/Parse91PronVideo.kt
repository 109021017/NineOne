package com.u91porn.parser

import android.text.TextUtils
import com.willme.nineone.api.BasePagedResult
import com.willme.nineone.api.Video
import com.willme.nineone.api.VideoDetail
import org.jetbrains.annotations.NotNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Created by Wen on 02/03/2018.
 */
class Parse91PronVideo{
    companion object {
        fun parseIndex(html: String): List<Video> {
            val unLimit91PornItemList = arrayListOf<Video>()
            val doc = Jsoup.parse(html)
            val body = doc.getElementById("tab-featured")
            val items = body.select("p")
            for (element in items) {
                val title = element.getElementsByClass("title").first().text()
                var imgUrl = element.select("img").first().attr("src")
                imgUrl = imgUrl.replace("1_([0-9]+\\.jpg)".toRegex(), "$1")
                val duration = element.getElementsByClass("duration").first().text()
                val contentUrl = element.select("a").first().attr("href")

                val allInfo = element.text()
                var info  = allInfo.substringAfter("添加时间: ")
                info = info.substringBefore("查看: ")
                val video = Video(
                        videoName = title,
                        thumbImgUrl = imgUrl,
                        durationStr = duration,
                        videoUrl = contentUrl,
                        info = info
                )
                unLimit91PornItemList.add(video)
            }
            return unLimit91PornItemList
        }

        @NotNull
        fun parseVideoPlayUrl(html: String): VideoDetail {
            val doc = Jsoup.parse(html)
            val videoUrl = doc.select("video").first().select("source").first().attr("src")
            val startIndex = videoUrl.lastIndexOf("/")
            val endIndex = videoUrl.indexOf(".mp4")
            val videoId = videoUrl.substring(startIndex + 1, endIndex)
            val ownnerUrl = doc.select("a[href*=UID]").first().attr("href")
            val ownnerId = ownnerUrl.substring(ownnerUrl.indexOf("=") + 1, ownnerUrl.length)
            val ownerName = doc.select("a[href*=UID]").first().text()
            val allInfo = doc.getElementById("videodetails-content").text()
            val addDate = allInfo.substring(allInfo.indexOf("添加时间"), allInfo.indexOf("作者"))
            val otherInfo = allInfo.substring(allInfo.indexOf("注册"), allInfo.indexOf("简介"))
            val thumbImg = doc.getElementById("vid").attr("poster")
            val videoName = doc.getElementById("viewvideo-title").text()

            return VideoDetail(
                    videoUrl = videoUrl,
                    videoId = videoId,
                    ownerName = ownerName,
                    thumbImgUrl = thumbImg,
                    videoName = videoName,
                    ownerId = ownnerId,
                    addDate = addDate,
                    userOtherInfo = otherInfo)
        }

        @NotNull
        fun parseHot(html: String): BasePagedResult<List<Video>> {
            var totalPage = 1
            val list = arrayListOf<Video>()
            val doc = Jsoup.parse(html)
            val body = doc.getElementById("fullside")

            val listchannel = body.getElementsByClass("listchannel")
            for (element in listchannel) {
                var contentUrl = element.select("a").first().attr("href")
                contentUrl = contentUrl.substring(0, contentUrl.indexOf("&"))
                val imgUrl = element.select("a").first().select("img").first().attr("src")
                val title = element.select("a").first().select("img").first().attr("title")
                val allInfo = element.text()
                val sindex = allInfo.indexOf("时长")
                val duration = allInfo.substring(sindex + 3, sindex + 8)
                var info  = allInfo.substringAfter("添加时间: ")
                info = info.substringBefore("查看: ")

                val video = Video(
                        videoName = title,
                        thumbImgUrl = imgUrl,
                        durationStr = duration,
                        videoUrl = contentUrl,
                        info = info)
                list.add(video)
            }
            val pagingnav = body.getElementById("paging")
            val a = pagingnav.select("a")
            if (a.size > 2) {
                val ppp = (a[a.size - 2] as Element).text()
                if (TextUtils.isDigitsOnly(ppp)) {
                    totalPage = Integer.parseInt(ppp)
                }
            }
            return BasePagedResult(list, totalPage)
        }

        @NotNull
        fun parseSearchVideos(html: String): BasePagedResult<List<Video>> {
            var totalPage = 1
            val list = arrayListOf<Video>()
            val doc = Jsoup.parse(html)
            val body = doc.getElementById("fullside")
            val listchannel = body.getElementsByClass("listchannel")
            for (element in listchannel) {
                var contentUrl = element.select("a").first().attr("href")
                contentUrl = contentUrl.substring(0, contentUrl.indexOf("&"))

                val imgUrl = element.select("a").first().select("img").first().attr("src")
                val title = element.select("span.title").text()
                val duration = element.select("span.duration").text()
                val allInfo = element.text()
                var info  = allInfo.substringAfter("添加时间: ")
                info = info.substringBefore("查看: ")

                val video = Video(
                        videoName = title,
                        thumbImgUrl = imgUrl,
                        videoUrl = contentUrl,
                        durationStr =  duration,
                        info = info)

                list.add(video)
            }
            val pagingnav = body.getElementById("paging")
            val a = pagingnav.select("a")
            if (a.size > 2) {
                val ppp = (a[a.size - 2] as Element).text()
                if (TextUtils.isDigitsOnly(ppp as CharSequence)) {
                    totalPage = Integer.parseInt(ppp)
                }
            }
            return BasePagedResult(list, totalPage)
        }
    }
}