data class Note(
    var id: Long = 0,
    val ownerId: Int = (0..99).random(),
    var title: String = listOf("Hello", "Bay", "Whats up").random(),
    var text: String = listOf("Hello", "Bay", "Whats up").random(),
    val date: Int = (0..99).random(),
    val comments: Int = (0..99).random(),
    val readComments: Int = (0..99).random(),
    val viewUrl: String = listOf("Hello", "Bay", "Whats up").random(),
    val privacyView: String = listOf("Hello", "Bay", "Whats up").random(),
    val canComment: Boolean = true,
    val textWiki: String = "https://wikipedia.org",
)