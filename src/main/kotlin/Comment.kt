data class Comment(
    var id: Int = (0..99).random(),
    val noteId: Note,
    val ownerId: Note,
    var message: String,

    var isDelete: Boolean = false,
)