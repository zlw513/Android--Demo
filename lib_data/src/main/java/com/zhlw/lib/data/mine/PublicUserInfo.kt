package com.zhlw.lib.data.mine


/**
 * example
{
"login": "zlw513",
"id": 48793154,
"node_id": "MDQ6VXNlcjQ4NzkzMTU0",
"avatar_url": "https://avatars.githubusercontent.com/u/48793154?v=4",
"gravatar_id": "",
"url": "https://api.github.com/users/zlw513",
"html_url": "https://github.com/zlw513",
"followers_url": "https://api.github.com/users/zlw513/followers",
"following_url": "https://api.github.com/users/zlw513/following{/other_user}",
"gists_url": "https://api.github.com/users/zlw513/gists{/gist_id}",
"starred_url": "https://api.github.com/users/zlw513/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/zlw513/subscriptions",
"organizations_url": "https://api.github.com/users/zlw513/orgs",
"repos_url": "https://api.github.com/users/zlw513/repos",
"events_url": "https://api.github.com/users/zlw513/events{/privacy}",
"received_events_url": "https://api.github.com/users/zlw513/received_events",
"type": "User",
"site_admin": false,
"name": null,
"company": null,
"blog": "",
"location": "shenzhen-china",
"email": null,
"hireable": null,
"bio": null,
"twitter_username": null,
"public_repos": 19,
"public_gists": 0,
"followers": 0,
"following": 1,
"created_at": "2019-03-21T11:28:21Z",
"updated_at": "2022-02-28T01:58:44Z"
}
*/

/**
 * 用户基本信息
 * @url https://api.github.com/users/{username}
 */
data class PublicUserInfo (
    val login : String,  //登录名
    val id : Long,   //用户id
    val node_id : String,
    val avatar_url : String,  //头像url
    val gravatar_id : String,
    val name : String?,   //昵称
    val company : String?,  //公司信息
    val blog : String,    //博客地址
    val location : String,   //用户所在地区
    val email : String?,     //邮箱
    val hireable : Boolean?,
    val public_repos : Int,   //公共仓库数
    val public_gists : Int,
    val followers : Int,      //粉丝数
    val following : Int       //关注数
)