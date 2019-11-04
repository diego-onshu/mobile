package br.com.etec.aula5.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.etec.aula5.model.Usuario

@Dao
interface UsuarioDao{
    @Query("select * from ${Usuario.TABLE_NAME}")
    fun select():List<Usuario>
    @Query("select * from ${Usuario.TABLE_NAME} where id = :id")
    fun select(id:Long):Usuario
    @Query("select * from ${Usuario.TABLE_NAME} where email = :email and senha = :password")
    fun select(email:String, password:String):Usuario

    @Insert(onConflict = REPLACE)
    fun insert(usuario: Usuario):Long

    @Query ("delete from ${Usuario.TABLE_NAME} where id = :id")
    fun delete(id: Long):Int

}