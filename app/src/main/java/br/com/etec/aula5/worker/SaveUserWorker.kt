package br.com.etec.aula5.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SaverUserWorker(var ctx:Context, params:WorkerParameters):Worker(ctx,params){
    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}