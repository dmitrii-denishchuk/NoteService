import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addNote() {
        // arrange
        val service = NoteService()

        // act
        val result = service.addNote(Note()).id

        // assert
        assertEquals(kotlin.math.abs(result.hashCode()), result)
    }

    @Test
    fun getNoteById() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)

        // act
        val result = service.getNoteById(note).id

        // assert
        assertEquals(note.id, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowGetNoteById() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.addNote(note)
        service.getNoteById(note1)
    }

    @Test
    fun getNotes() {
        // arrange
        val service = NoteService()
        val note1 = Note()
        val note2 = Note()
        val note3 = Note()
        service.addNote(note1)
        service.addNote(note2)
        service.addNote(note3)

        // act
        val result = service.getNotes()

        // assert
        assertEquals(listOf(note1, note2, note3), result)
    }

    @Test
    fun editNote() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)
        val title = "random"
        val text = "random"

        // act
        val result = service.editNote(note, title, text).title

        // assert
        assertEquals(note.title, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEditNote() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.addNote(note)
        service.editNote(note1, title = "Заметка отсутствует", text = "Заметка отсутствует")
    }

    @Test
    fun deleteNote() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)

        // act
        val result = service.deleteNote(note)

        // assert
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowDeleteNote() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.addNote(note)
        service.deleteNote(note1)
    }

    @Test
    fun addComment() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)
        val comment = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        service.addComment(note, comment)

        // act
        val result = service.addComment(note, comment).id

        // assert
        assertEquals(kotlin.math.abs(result.hashCode()), result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowAddComment() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.addComment(note1, comment)
    }

    @Test
    fun editComment() {
        val service = NoteService()
        val note = Note()
        service.addNote(note)
        val comment1 = Comment(noteId = note, ownerId = note, message = "random")
        val comment2 = Comment(noteId = note, ownerId = note, message = "random")
        val comment3 = Comment(noteId = note, ownerId = note, message = "random")
        service.addComment(note, comment1)
        service.addComment(note, comment2)
        service.addComment(note, comment3)

        // act
        val result = service.editComment(comment2, "отредактирован").message

        // assert
        assertEquals("отредактирован", result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEditComment_CommentDeleted() {
        val service = NoteService()
        val note = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random", isDelete = true)
        service.addNote(note)
        service.addComment(note, comment)
        service.editComment(comment, text = "Комментарий удален")
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEditComment_CommentDoestNotExist() {
        val service = NoteService()
        val note = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random")
        val comment1 = Comment(noteId = note, ownerId = note, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.editComment(comment1, text = "Такого комментария не существует")
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEditComment_NoteNotFound() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        val comment = Comment(noteId = note1, ownerId = note1, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.editComment(comment, text = "Заметка не найдена")
    }

    @Test
    fun getComments() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)
        val comment1 = Comment(noteId = note, ownerId = note, message = "random")
        val comment2 = Comment(noteId = note, ownerId = note, message = "random")
        val comment3 = Comment(noteId = note, ownerId = note, message = "random")
        service.addComment(note, comment1)
        service.addComment(note, comment2)
        service.addComment(note, comment3)

        // act
        val result = service.getComments(note)

        // assert
        assertEquals(listOf(comment1, comment2, comment3), result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowGetComments_NoteNotFound() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        val comment = Comment(noteId = note1, ownerId = note1, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.getComments(note1)
    }

    @Test
    fun restoreComment() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)
        val comment1 = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        val comment2 = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        val comment3 = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        service.addComment(note, comment1)
        service.addComment(note, comment2)
        service.addComment(note, comment3)
        service.deleteComment(comment2)

        // act
        val result = service.restoreComment(comment2)

        // assert
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowRestoreComment_CommentDeleted() {
        val service = NoteService()
        val note = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.restoreComment(comment)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowRestoreComment_CommentDoestNotExist() {
        val service = NoteService()
        val note = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random")
        val comment1 = Comment(noteId = note, ownerId = note, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.restoreComment(comment1)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowRestoreComment_NoteNotFound() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.addNote(note)

        val comment = Comment(noteId = note1, ownerId = note1, message = "random")
        service.addComment(note, comment)

        service.restoreComment(comment)
    }

    @Test
    fun deleteComment() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.addNote(note)
        val comment1 = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        val comment2 = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        val comment3 = service.addComment(note, Comment(noteId = note, ownerId = note, message = "random"))
        service.addComment(note, comment1)
        service.addComment(note, comment2)
        service.addComment(note, comment3)

        // act
        val result = service.deleteComment(comment2)

        // assert
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowDeleteComment_CommentDeleted() {
        val service = NoteService()
        val note = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random", isDelete = true)
        service.addNote(note)
        service.addComment(note, comment)
        service.deleteComment(comment)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowDeleteComment_CommentNotFound() {
        val service = NoteService()
        val note = Note()
        val comment = Comment(noteId = note, ownerId = note, message = "random")
        val comment1 = Comment(noteId = note, ownerId = note, message = "random")
        service.addNote(note)
        service.addComment(note, comment)
        service.deleteComment(comment1)
    }
}