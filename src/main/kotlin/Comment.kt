data class Comment(
    var id: Long = 0,
    val noteId: Long,
    val ownerId: Note,
    var message: String = "",
    var isDelete: Boolean = false,
)