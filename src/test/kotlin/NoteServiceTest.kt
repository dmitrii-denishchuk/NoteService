import org.junit.Assert.*
import org.junit.Test

class NoteServiceTest {

    @Test
    fun add() {
        // arrange
        val service = NoteService()

        // act
        val result = service.add(Note())

        // assert
        assertEquals(kotlin.math.abs(result.hashCode()).toLong(), result)
    }

    @Test(expected = NotFoundException::class)
    fun delete() {
        val service = NoteService()
        val note = Note()
        service.add(note)
        service.delete(note.id)
        service.getById(note.id)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowDelete() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.add(note)
        service.delete(note1.id)
    }

    @Test
    fun edit() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.add(note)
        service.edit(note)

        // act
        val result = service.getById(note.id).text

        // assert
        assertEquals("ИЗМЕНЕНО", result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowEdit() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.add(note)
        service.edit(note1)
    }

    @Test
    fun read() {
        // arrange
        val service = NoteService()
        val note1 = Note()
        val note2 = Note()
        val note3 = Note()
        service.add(note1)
        service.add(note2)
        service.add(note3)

        // act
        val result = service.read()

        // assert
        assertEquals(listOf(note1, note2, note3), result)
    }

    @Test
    fun getById() {
        // arrange
        val service = NoteService()
        val note = Note()
        service.add(note)

        // act
        val result = service.getById(note.id).id

        // assert
        assertEquals(note.id, result)
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrowGetById() {
        val service = NoteService()
        val note = Note()
        val note1 = Note()
        service.add(note)
        service.getById(note1.id)
    }
}