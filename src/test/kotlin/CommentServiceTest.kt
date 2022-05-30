import org.junit.Assert.*
import org.junit.Test

class CommentServiceTest {

    @Test
    fun add() {
        // arrange
        val service = CommentService()
        val note = Note()
        service.note.add(note)

        val comment = Comment(noteId = note.id, ownerId = note)

        // act
        val result = service.add(comment)

        // assert
        assertEquals(comment.id, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowAdd() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        service.add(Comment(noteId = 1, ownerId = note, message = "random"))
    }

    @Test
    fun delete() {
        // arrange
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment)
        service.delete(comment.id)

        // act
        val result = service.getById(comment.id).isDelete

        // assert
        assertEquals(true, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowDelete_Deleted() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random", isDelete = true)
        service.add(comment)
        service.delete(comment.id)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowDelete_NotFound() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment)
        service.delete(8)
    }

    @Test
    fun edit() {
        // arrange
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment)
        service.edit(comment)

        // act
        val result = service.getById(comment.id).message

        // assert
        assertEquals("ИЗМЕНЕН", result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEdit_Deleted() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random", isDelete = true)
        service.add(comment)
        service.edit(comment)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEdit_DoestNotExist() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        val comment2 = Comment(id = 999, noteId = note.id, ownerId = note, message = "random")
        service.add(comment)
        service.edit(comment2)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEdit_NotFound() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = 6, ownerId = note, message = "random")
        service.edit(comment)
    }

    @Test
    fun read() {
        // arrange
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment1 = Comment(noteId = note.id, ownerId = note, message = "random")
        val comment2 = Comment(noteId = note.id, ownerId = note, message = "random", isDelete = true)
        val comment3 = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment1)
        service.add(comment2)
        service.add(comment3)

        // act
        val result = service.read()

        // assert
        assertEquals(listOf(comment1, comment3), result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowRead_NotFound() {
        val service = CommentService()
        service.read()
    }

    @Test
    fun getById() {
        // arrange
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment)

        // act
        val result = service.getById(comment.id).id

        // assert
        assertEquals(comment.id, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowGetById() {
        val service = CommentService()
        val note = Note()
        service.getById(note.id)
    }

    @Test
    fun restore() {
        // arrange
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment)
        service.delete(comment.id)
        service.restore(comment.id)

        // act
        val result = service.getById(comment.id).isDelete

        // assert
        assertEquals(false, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowRestore_NotDeleted() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note, message = "random")
        service.add(comment)
        service.restore(comment.id)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowRestore_DoestNotExist() {
        val service = CommentService()
        val note = Note()
        service.note.add(note)
        val comment = Comment(noteId = note.id, ownerId = note)
        service.add(comment)
        service.restore(666)
    }

//    @Test(expected = NotFoundException::class)
//    fun shouldThrowRestore_NoteNotFound() {
//        val service = CommentService()
//        val note = Note()
//        val note1 = Note()
//        service.note.add(note)
//
//        val comment = Comment(noteId = note1, ownerId = note1, message = "random")
//        service.add(note, comment)
//
//        service.restore(comment)
//    }
}