package fr.leboncoin.domain.logger

object GlobalLogger {
    private var delegate: Logger? = null

    fun init(logger: Logger) {
        delegate = logger
    }

    fun v(message: String, vararg args: Any?) = delegate?.v(message, *args)
    fun v(t: Throwable, message: String, vararg args: Any?) = delegate?.v(t, message, *args)
    fun v(t: Throwable) = delegate?.v(t)

    fun d(message: String, vararg args: Any?) = delegate?.d(message, *args)
    fun d(t: Throwable, message: String, vararg args: Any?) = delegate?.d(t, message, *args)
    fun d(t: Throwable) = delegate?.d(t)

    fun i(message: String, vararg args: Any?) = delegate?.i(message, *args)
    fun i(t: Throwable, message: String, vararg args: Any?) = delegate?.i(t, message, *args)
    fun i(t: Throwable) = delegate?.i(t)

    fun w(message: String, vararg args: Any?) = delegate?.w(message, *args)
    fun w(t: Throwable, message: String, vararg args: Any?) = delegate?.w(t, message, *args)
    fun w(t: Throwable) = delegate?.w(t)

    fun e(message: String, vararg args: Any?) = delegate?.e(message, *args)
    fun e(t: Throwable, message: String, vararg args: Any?) = delegate?.e(t, message, *args)
    fun e(t: Throwable) = delegate?.e(t)

    fun wtf(message: String, vararg args: Any?) = delegate?.wtf(message, *args)
    fun wtf(t: Throwable, message: String, vararg args: Any?) = delegate?.wtf(t, message, *args)
    fun wtf(t: Throwable) = delegate?.wtf(t)
}