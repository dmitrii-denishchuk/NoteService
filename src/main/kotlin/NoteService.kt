class NoteService : CrudService<Note> {

    var notes = mutableMapOf<Note, List<Comment>>()

    override fun add(entity: Note): Long {
        entity.id = kotlin.math.abs(entity.hashCode()).toLong()
        notes[entity] = emptyList()
        return notes.keys.last().id
    }

    override fun delete(id: Long) {
        for ((key, value) in notes)
            if (key.id == id) {
                notes.remove(key, value)
            }
        throw NotFoundException("Заметка $id не найдена")
    }

    override fun edit(entity: Note) {
        for (key in notes.keys)
            if (key.id == entity.id) {
                key.title = "ИЗМЕНЕНО"
                key.text = "ИЗМЕНЕНО"
            } else throw NotFoundException("Заметка ${entity.id} не найдена")
    }

    override fun read(): List<Note> {
        return notes.keys.toList()
    }

    override fun getById(id: Long): Note {
        for (key in notes.keys)
            return if (key.id == id) key else continue
        throw NotFoundException("Заметка $id не найдена")
    }

    override fun restore(id: Long) {
    }
}