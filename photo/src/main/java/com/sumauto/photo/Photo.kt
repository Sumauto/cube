package com.sumauto.photo


/*
 * Copyright:	
 * Author: 		Lincoln
 * Description:	
 * History:		2021/08/13 
 */
object Photo {

    fun <S> create(source: ImageSource<S>): Builder<S> {
        return Builder()
    }

    class Builder<S> {

    }

}