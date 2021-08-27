package com.sumauto.download

enum class DirConflictStrategy {
    //delete old dir
    DELETE,
    //create a new dir
    CREATE,
    //abort use old dir
    USE_OLD,
    //throw exceptions
    FAIL
}