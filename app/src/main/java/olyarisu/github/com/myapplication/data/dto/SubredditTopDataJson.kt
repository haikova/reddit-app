package olyarisu.github.com.myapplication.data.dto

data class SubredditTopDataJson(
    val after: String?,
    val before: String?,
    val children: List<PostJson>,
    val dist: Int,
    val modhash: String
)