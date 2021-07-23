package com.finest.comm_base.util

/**
 * Created by Administrator on 2018/11/14.
 */

class EventMessage<T> {

    var code: Int = 0
    var data: T? = null

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }

    override fun toString(): String {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}'.toString()
    }
}
