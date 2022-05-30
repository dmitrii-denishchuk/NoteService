class CommentService : CrudService<Comment> {

    val note = NoteService()

    override fun add(entity: Comment): Long {
        for ((key, value) in note.notes)
            if (key.id == entity.noteId) {
                entity.id = kotlin.math.abs(entity.hashCode()).toLong()
                note.notes[key] = value.plus(entity)
                return entity.id
            }
        throw NotFoundException("Заметка ${entity.noteId} не найдена. Комментарий не добавлен")
    }

    override fun delete(id: Long) {
        for (lists in note.notes.values)
            for (value in lists)
                if (value.id == id) {
                    if (!value.isDelete) {
                        value.isDelete = true
                    } else throw NotFoundException("Комментарий уже удален")
                } else throw NotFoundException("Комментарий не найден")
    }

    override fun edit(entity: Comment) {
        for ((key, value) in note.notes)
            if (key.id == entity.noteId) {
                for (comment in value)
                    if (comment.id == entity.id) {
                        if (!comment.isDelete) {
                            comment.message = "ИЗМЕНЕН"
                        } else throw NotFoundException("Комментарий удален")
                    } else throw NotFoundException("Такого комментария не существует")
            } else throw NotFoundException("Заметка ${entity.noteId} не найдена")
    }

    override fun read(): List<Comment> {
        for (lists in note.notes.values) {
            return lists.filter { !it.isDelete }
        }
        throw NotFoundException("Комментарии отсутствуют т.к. заметка не найдена")
    }

    override fun getById(id: Long): Comment {
        for (lists in note.notes.values)
            for (value in lists)
                return if (value.id == id) value else continue
        throw NotFoundException("Заметка $id не найдена")
    }

    override fun restore(id: Long) {
        for (lists in note.notes.values)
            for (value in lists)
                if (value.id == id) {
                    if (value.isDelete) {
                        value.isDelete = false
                    } else throw NotFoundException("Комментарий не удален")
                } else throw NotFoundException("Невозможно восстановить несуществующий комментарий")
    }
}