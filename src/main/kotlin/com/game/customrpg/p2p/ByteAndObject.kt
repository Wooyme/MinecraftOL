package com.game.customrpg.p2p

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object ByteAndObject {
	fun ByteToObject(bytes: ByteArray?): Any? {
		var obj: Any? = null
		try {
			var bi = ByteArrayInputStream(bytes)
			var oi = ObjectInputStream(bi)
			obj = oi.readObject()
			bi.close()
			oi.close()
		} catch (e: Exception) {
			System.out.println("translation" + e.message)
			e.printStackTrace()
		}
		return obj
	}

	fun ObjectToByte(obj: kotlin.Any): ByteArray? {
		var bytes: ByteArray? = null
		try {
			var bo = ByteArrayOutputStream()
			var oo = ObjectOutputStream(bo)
			oo.writeObject(obj)
			bytes = bo.toByteArray()
			bo.close()
			oo.close()
		} catch (e: Exception) {
			System.out.println("translation" + e.message)
			e.printStackTrace()
		}
		return bytes
	}
}