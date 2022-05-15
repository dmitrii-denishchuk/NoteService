class NoteService {
    private var comments = emptyList<Comment>()
    private var notes = emptyMap<Note, List<Comment>>().toMutableMap()

    fun addNote(note: Note): Note {
        note.id = kotlin.math.abs(note.hashCode())
        notes[note] = comments
        return note
    }

    fun getNoteById(note: Note): Note {
        for (key in notes.keys)
            return if (key.id == note.id) note else continue
        throw NotFoundException("Заметка ${note.id} не найдена")
    }

    fun getNotes(): List<Note> {
        return notes.keys.toList()
    }

    fun editNote(note: Note, title: String, text: String): Note {
        for (key in notes.keys)
            if (key.id == note.id) {
                key.title = title
                key.text = text
                println(key)
                return key
            }
        throw NotFoundException("Заметка ${note.id} не найдена")
    }

    fun deleteNote(note: Note): Boolean {
        for ((key, value) in notes)
            if (key.id == note.id) {
                notes.remove(key, value)
                return true
            }
        throw NotFoundException("Заметка ${note.id} не найдена")
    }

    fun addComment(note: Note, comment: Comment): Comment {
        for ((key, value) in notes)
            if (key.id == note.id) {
                comment.id = kotlin.math.abs(comment.hashCode())
                notes[key] = value.plus(comment)
                return comment
            }
        throw NotFoundException("Заметка ${note.id} не найдена. Комментарий не добавлен")
    }

    fun editComment(comment: Comment, text: String): Comment {
        for ((key, value) in notes)
            if (key.id == comment.noteId.id) {
                for (list in value)
                    if (list.id == comment.id) {
                        if (!list.isDelete) {
                            list.message = text
                            return comment
                        }
                        throw NotFoundException("Комментарий удален")
                    }
                throw NotFoundException("Такого комментария не существует")
            }
        throw NotFoundException("Заметка ${comment.noteId.id} не найдена")
    }

    fun getComments(note: Note): List<Comment> {
        val lists = emptyList<Comment>().toMutableList()
        if (notes.keys.contains(note)) {
            for (value in notes.values) {
                for (list in value)
                    if (!list.isDelete) {
                        lists.add(list)
                    }
            }
            return lists
        }
        throw NotFoundException("Комментарии отсутствуют т.к. заметка с id ${note.id} не найдена")
    }

    fun restoreComment(comment: Comment): Boolean {
        if (notes.keys.contains(comment.noteId)) {
            for (value in notes.values)
                for (list in value)
                    if (list.id == comment.id) {
                        if (list.isDelete) {
                            list.isDelete = false
                            return true
                        }
                        throw NotFoundException("Комментарий не удален")
                    }
            throw NotFoundException("Невозможно восстановить несуществующий комментарий")
        }
        throw NotFoundException("Невозможно восстановить комментарий, т.к. соответствующая заметка не найдена")
    }

    fun deleteComment(comment: Comment): Boolean {
        for ((key, value) in notes)
            if (key.id == comment.noteId.id) {
                for (list in value)
                    if (list.id == comment.id) {
                        if (!list.isDelete) {
                            list.isDelete = true
                            return true
                        }
                        throw NotFoundException("Комментарий уже удален")
                    }
            }
        throw NotFoundException("Комментарий не найден")
    }
}