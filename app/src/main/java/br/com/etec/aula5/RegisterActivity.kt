package br.com.etec.aula5

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import br.com.etec.aula5.worker.SaveUserWorker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class RegisterActivity : AppCompatActivity() {

    lateinit var txtNome: TextInputEditText
    lateinit var txtEmail: TextInputEditText
    lateinit var txtTelefone: TextInputEditText
    lateinit var txtSenha: TextInputEditText
    lateinit var txtConfirma: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNome = findViewById(R.id.txtNome)
        txtEmail = findViewById(R.id.txtEmail)
        txtTelefone = findViewById(R.id.txtFone)
        txtSenha = findViewById(R.id.txtSenha)
        txtConfirma = findViewById(R.id.txtConfirma)
    }

    fun validate(): Boolean {

        var valido = true

        txtNome.error = null
        txtEmail.error = null
        txtTelefone.error = null
        txtSenha.error = null
        txtConfirma.error = null

        if (TextUtils.isEmpty(txtNome.text.toString())) {
            txtNome.error = resources.getString(R.string.msg_obrigatorio)
            valido = false
        }
        if (TextUtils.isEmpty(txtEmail.text.toString())) {
            txtEmail.error = resources.getString(R.string.msg_obrigatorio)
            valido = false
        }
        if (TextUtils.isEmpty(txtTelefone.text.toString())) {
            txtTelefone.error = resources.getString(R.string.msg_obrigatorio)
            valido = false
        }
        if (TextUtils.isEmpty(txtSenha.text.toString())) {
            txtSenha.error = resources.getString(R.string.msg_obrigatorio)
            valido = false
        }
        if (TextUtils.isEmpty(txtConfirma.text.toString())) {
            txtConfirma.error = resources.getString(R.string.msg_obrigatorio)
            valido = false
        }

        return valido
    }

    fun salvar(v: View) {
        if (!validate()) {
            return
        }
        val input = workDataOf(
            "nome" to txtNome.text.toString(),
            "email" to txtEmail.text.toString(),
            "telefone" to txtTelefone.text.toString(),
            "senha" to txtSenha.text.toString()
        )
        var request = OneTimeWorkRequestBuilder<SaveUserWorker>()
            .setInputData(input)
            .build()
        var resultObserver = Observer<WorkInfo> {
            if (it == null) {
                return@Observer
            }
            if (it.state == WorkInfo.State.SUCCEEDED || it.state == WorkInfo.State.FAILED) {
                Snackbar.make(
                    this.window.decorView,
                    it.outputData.getString("Result")!!,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (it.state == WorkInfo.State.SUCCEEDED) {
                finish()
            }

        }
        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(request)
        workManager.getWorkInfoByIdLiveData(request.id).observe(this, resultObserver)
    }


}




